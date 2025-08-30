package dev.ohhoonim.component.signJwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import dev.ohhoonim.component.signJwt.Authority;
import dev.ohhoonim.component.signJwt.AuthorityPort;
import dev.ohhoonim.component.signJwt.BearerAuthenticationFilter;
import dev.ohhoonim.component.signJwt.BearerAuthenticationToken;
import dev.ohhoonim.component.signJwt.BearerTokenService;
import dev.ohhoonim.component.signJwt.SignController;
import dev.ohhoonim.component.signJwt.SignUsecase;
import dev.ohhoonim.component.signJwt.SignVo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebMvcTest(SignController.class)
@Import({ BearerTokenService.class, BearerAuthenticationFilter.class })
public class SignJwtTest {

    @InjectMocks
    SignController signController;

    @MockitoBean
    SignUsecase signService;

    @Autowired
    BearerTokenService bearerTokenService;

    @Autowired
    BearerAuthenticationFilter bearerFilter;

    @MockitoBean
    AuthorityPort authorityPort;

    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    public void contextLoad() {

    }

    @Test
    @DisplayName("login 페이지 접근은 permitAll")
    public void loginTest() {
        var access = bearerTokenService.generateAccessToken("matthew", List.of());
        var refresh = bearerTokenService.generateRefreshToken("matthew", List.of());

        SignVo signVo = new SignVo(access, refresh);
        when(signService.signIn(any())).thenReturn(signVo);

        mockMvcTester.post().uri("/signJwt/login")
                .param("username", "matthew")
                .param("password", "12345")
                .assertThat()
                .apply(print())
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data")
                .hasFieldOrPropertyWithValue("access", access)
                .hasFieldOrPropertyWithValue("refresh", refresh);
    }

    @Test
    @DisplayName("filter : BearerAuthenticationToken으로 context에 저장되는지 확인")
    public void bearerFilterSuccessTest() throws ServletException, IOException {
        // doFilter 테스트를 위한 mock 설정
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        // 선행조건: 로그인 후 access token을 발급받는다 
        var accessToken = bearerTokenService
                .generateAccessToken("matthew", List.of());

        // access token은 http header의 "Authorization" 키에
        // 'Bearer ' prefix가 붙은 형태로 들어와야 한다. 
        when(mockRequest.getHeader("Authorization"))
                .thenReturn("Bearer " + accessToken);
        when(authorityPort.authoritiesByUsername(any()))
                .thenReturn(List.of(
                        new Authority(1L, "ROLE_ADMIN"),
                        new Authority(2L, "ROLE_USER")));

        bearerFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        // SecurityContext에 토큰이 저장되었는지 확인
        var authentication = (BearerAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
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
        var accessToken = bearerTokenService
                .generateDenyToken("matthew", List.of());
        when(mockRequest.getHeader("Authorization"))
                .thenReturn("Bearer " + accessToken);

        assertThatThrownBy(() -> bearerFilter
                .doFilter(mockRequest, mockResponse, mockFilterChain))
                .hasMessage("유효하지 않은 토큰입니다");

    }

    @Test
    public void refreshTokenTest() {
        var refresh = bearerTokenService.generateRefreshToken(
                "matthew",
                List.of());
        var newAccess = bearerTokenService.generateAccessToken("matthew",
                List.of());

        when(signService.refresh(any()))
                .thenReturn(new SignVo(newAccess,
                        "refresh..."));

        mockMvcTester.get().uri("/signJwt/refresh")
                .header("Authorization", "Bearer " + refresh)
                .accept(MediaType.APPLICATION_JSON)
                .assertThat()
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.data.access")
                .isEqualTo(newAccess);
    }

    @Test
    public void requestSomePageTest() {
        var access = bearerTokenService.generateAccessToken("matthew",
                List.of());
        mockMvcTester.get().uri("/signJwt/forFilterTest")
                .header("Authorization", "Bearer " + access)
                .accept(MediaType.TEXT_PLAIN)
                .assertThat().hasStatusOk().bodyJson()
                .extractingPath("$.data")
                .isEqualTo("success");
    }

    @Test
    public void requestSomePageFailTest() {
        mockMvcTester.get().uri("/signJwt/forFilterTest")
                .header("Authorization", "Bearer " + "hacked token")
                .accept(MediaType.TEXT_PLAIN)
                .assertThat().hasFailed()
                .failure().hasMessage("유효하지 않은 토큰입니다");
        // 여기서는 exception을 그대로 리턴해주고 있으나,
        // 응답모델을 사용하면 다르게 적용할 수 있음
    }

    @Test
    public void logoutTest() {
        // 로그아웃 전 인증이 필요한 테스트에 접속 
        var access = bearerTokenService.generateAccessToken("matthew",
                List.of());
        mockMvcTester.get().uri("/signJwt/forFilterTest")
                .header("Authorization", "Bearer " + access)
                .accept(MediaType.TEXT_PLAIN)
                .assertThat().hasStatusOk().bodyJson()
                .extractingPath("$.data")
                .isEqualTo("success");

        // 로그아웃 
        mockMvcTester.get().uri("/signJwt/logout")
                .assertThat().bodyText()
                .isEqualTo("");
        // 로그아웃 후 authentication 은 null
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull();
    }

    @TestConfiguration
    @EnableWebSecurity(debug = true)
    public static class SecurityConfig {

        @Bean
        SecurityFilterChain securityFitlerChain(HttpSecurity http,
                BearerAuthenticationFilter bearerFilter) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> {
                        auth
                                .requestMatchers("/signJwt/login",
                                        "/signJwt/logout",
                                        "/signJwt/main",
                                        "/signJwt/refresh")
                                .permitAll()
                                .anyRequest().authenticated();
                    })
                    .logout(logout -> logout
                            .logoutUrl("/signJwt/logout")
                    // .logoutSuccessHandler(LogoutSuccessHandler)
                    // TODO logoutSuccessUrl설정만으로는 리다이렉트 안되는 듯
                    // .logoutSuccessUrl("/signJwt/main")
                    )
                    .sessionManagement(s -> s
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterAfter(bearerFilter, LogoutFilter.class);

            return http.build();
        }
    }
}
