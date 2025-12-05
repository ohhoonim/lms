package dev.ohhoonim.component.sign.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.component.sign.Authority;
import dev.ohhoonim.component.sign.SignedToken;
import dev.ohhoonim.component.sign.activity.SignActivity;
import dev.ohhoonim.component.sign.activity.port.AuthorityPort;
import dev.ohhoonim.component.sign.activity.service.BearerTokenService;
import dev.ohhoonim.component.sign.api.SignController.LoginReq;
import dev.ohhoonim.component.sign.api.filter.BearerAuthenticationFilter;
import dev.ohhoonim.component.sign.api.filter.BearerAuthenticationProvider;
import dev.ohhoonim.component.sign.api.filter.BearerAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(SignController.class)
@Import({BearerTokenService.class, BearerAuthenticationFilter.class,
                BearerAuthenticationProvider.class, SecurityConfig.class})
class SignControllerTest {

        @InjectMocks
        SignController signController;

        @MockitoBean
        SignActivity signService;

        @Autowired
        BearerTokenService bearerTokenService;

        @Autowired
        BearerAuthenticationFilter bearerFilter;

        @Autowired
        BearerAuthenticationProvider bearerAuthenticationProvider;

        @MockitoBean
        AuthorityPort authorityPort;

        @Autowired
        MockMvcTester mockMvcTester;

        @Autowired
        ObjectMapper objectMapper;

        @Test
        void signInTest() throws JacksonException {
                var access = bearerTokenService.generateAccessToken("matthew", List.of());
                var refresh = bearerTokenService.generateRefreshToken("matthew", List.of());

                var search = new Search<>(new LoginReq("matthew", "abc123"), null);

                SignedToken signedToken = new SignedToken(access, refresh);
                when(signService.signIn(any())).thenReturn(new Vo<SignedToken>(signedToken));

                mockMvcTester.post().with(csrf()).uri("/sign/in")
                                .content(objectMapper.writeValueAsString(search))
                                .contentType(MediaType.APPLICATION_JSON).assertThat().apply(print())
                                .bodyJson().extractingPath("$.data.record")
                                .hasFieldOrPropertyWithValue("access", access)
                                .hasFieldOrPropertyWithValue("refresh", refresh);
        }

        @Test
        void timezone() {
                ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
                ZonedDateTime expiryZdt = now.plus(7, ChronoUnit.HOURS);
                Date expiryDate = Date.from(expiryZdt.toInstant());
                assertThat(Date.from(now.toInstant())).isBefore(expiryDate);
        }

        @Test
        @DisplayName("filter : BearerAuthenticationToken으로 context에 저장되는지 확인")
        void bearerFilterSuccessTest() throws ServletException, IOException {
                // doFilter 테스트를 위한 mock 설정
                HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
                HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
                FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
                // 선행조건: 로그인 후 access token을 발급받는다 
                var accessToken = bearerTokenService.generateAccessToken("matthew", List.of());

                // access token은 http header의 "Authorization" 키에
                // 'Bearer ' prefix가 붙은 형태로 들어와야 한다. 
                when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + accessToken);
                when(authorityPort.authoritiesByUsername(any())).thenReturn(
                                List.of(new Authority("ROLE_ADMIN"), new Authority("ROLE_USER")));

                bearerFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

                // SecurityContext에 토큰이 저장되었는지 확인
                var authentication = (BearerAuthenticationToken) SecurityContextHolder.getContext()
                                .getAuthentication();
                assertThat(authentication.getName()).isEqualTo("matthew");
                assertThat(authentication.getAuthorities()).hasSize(2);
                assertThat(authentication.isAuthenticated()).isTrue();
        }

        @Test
        @DisplayName("유효하지 않은(또는 만료된) 토큰시 에러처리")
        public void bearerFilterInFail() throws ServletException, IOException {
                // doFilter 테스트를 위한 mock 설정
                HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
                HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
                FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
                // 선행조건: 로그인 후 access token을 발급받는다 
                // 여기서는 테스트를 위해 만료된 토큰 사용
                var accessToken = bearerTokenService.generateDenyToken("matthew", List.of());
                when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + accessToken);

                assertThatThrownBy(() -> bearerFilter.doFilter(mockRequest, mockResponse,
                                mockFilterChain)).hasMessage("유효하지 않은 토큰입니다");

        }

        @Test
        public void refreshTokenTest() {
                var refresh = bearerTokenService.generateRefreshToken("matthew", List.of());
                var newAccess = bearerTokenService.generateAccessToken("matthew", List.of());

                when(signService.refresh(any()))
                                .thenReturn(new Vo(new SignedToken(newAccess, "refresh...")));

                mockMvcTester.get().uri("/sign/refresh")
                                .header("Authorization", "Bearer " + refresh)
                                .accept(MediaType.APPLICATION_JSON).assertThat().hasStatusOk()
                                .bodyJson().extractingPath("$.data.record.access")
                                .isEqualTo(newAccess);
        }

}
