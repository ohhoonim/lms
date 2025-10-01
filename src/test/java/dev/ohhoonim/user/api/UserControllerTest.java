package dev.ohhoonim.user.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.user.User;
import dev.ohhoonim.user.activity.UserEnableStatus;
import dev.ohhoonim.user.activity.UserLockStatus;
import dev.ohhoonim.user.activity.UserReq;
import dev.ohhoonim.user.activity.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvcTester mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @InjectMocks
    UserController userController;

    @MockitoBean
    UserService userService;

    @Test
    @WithMockUser
    void withdrawUser() throws Exception {
        var user = new User("maathew");

        mockMvc.post().with(csrf())
                .uri("/user/withdrawUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .assertThat().apply(print())
                .hasStatusOk();

        verify(userService, times(1))
                .withdrawUser(any(User.class));
    }

    @Test
    @WithMockUser
    void users() throws Exception {
        var userReq = new UserReq();
        userReq.setUsername("maathew");
        var condition = new Search<UserReq>(userReq, null);

        when(userService.users(any())).thenReturn(new Vo(List.of()));

        mockMvc.get()
                .uri("/user/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(condition))
                .assertThat().apply(print())
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void userInfo() throws Exception {
        when(userService.userInfo(any())).thenReturn(new User("matthew"));
        mockMvc.get()
                .uri("/user/userInfo/matthew")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.data.username")
                .isEqualTo("matthew");
    }

    @Test
    @WithMockUser
    void verifyPassword() throws Exception {
        var userReq = new UserReq();
        userReq.setUsername("maathew");
        userReq.setPassword("abc123");
        when(userService.verifyPassword(any(), any()))
                .thenReturn(true);
        mockMvc.post().with(csrf())
                .uri("/user/verifyPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq))
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$.data")
                .isEqualTo(true);
    }

    @Test
    @WithMockUser
    void resetPassword() throws Exception {
        var userReq = new UserReq();
        userReq.setUsername("matthew");
        userReq.setPassword("abc123");
        userReq.setNewPassword("abc123");

        mockMvc.post().with(csrf())
                .uri("/user/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq))
                .assertThat().apply(print())
                .hasStatusOk();

        verify(userService, times(1))
                .resetPassword(any(User.class),
                        eq("abc123"), eq("abc123"));
    }

    @Test
    @WithMockUser
    void register() throws Exception {
        var newUser = new User("matthew");
        mockMvc.post().with(csrf())
                .uri("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .assertThat().apply(print())
                .hasStatusOk();

        verify(userService, times(1))
                .register(any(User.class));
    }

    @Test
    @WithMockUser
    void modifiyInfo() throws Exception {
        var newUser = new User("matthew");
        mockMvc.post().with(csrf())
                .uri("/user/modifyInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
                .assertThat().apply(print())
                .hasStatusOk();
        verify(userService, times(1))
                .modifyInfo(any(User.class));
    }

    @Test
    @WithMockUser
    void isValidUser() throws Exception {
        var userReq = new UserReq();
        userReq.setUsername("matthew");
        userReq.setPassword("abc123");

        when(userService.isValidUser(eq("matthew"), eq("abc123")))
                .thenReturn(new User("matthew"));

        mockMvc.post().with(csrf())
                .uri("/user/isValidUser").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq))
                .assertThat().apply(print())
                .bodyJson().extractingPath("$.data.username")
                .isEqualTo("matthew");
    }

    @Test
    @WithMockUser
    void modifyLock() throws Exception {
        var userReq = new UserReq();
        userReq.setUsername("matthew");
        userReq.setLocked(true);
        userReq.setEffectiveDate(null); // optional

        when(userService.modifyLock(any(), eq(true), any()))
                .thenReturn(UserLockStatus.Lock);

        mockMvc.post().with(csrf())
                .uri("/user/modifyLock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq))
                .assertThat().bodyJson().extractingPath("$.data")
                .isEqualTo(UserLockStatus.Lock.toString());
    }

    @Test
    @WithMockUser
    void dormantUser() throws Exception {
        var userReq = new UserReq();
        userReq.setUsername("matthew");

        mockMvc.post().with(csrf())
                .uri("/user/dormantUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq))
                .assertThat()
                .hasStatusOk();

        verify(userService, times(1))
                .dormantUser(any());

    }

    @Test
    @WithMockUser
    void increaseFailedAttemptCount() throws Exception {

        var userReq = new UserReq();
        userReq.setUsername("matthew");
        userReq.setIsInit(true);

        when(userService.increaseFailedAttemptCount(any(), eq(true)))
                .thenReturn(0);

        mockMvc.post().with(csrf())
                .uri("/user/increaseFailedAttemptCount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq))
                .assertThat()
                .hasStatusOk().bodyJson().extractingPath("$.data")
                .isEqualTo(0);
    }

    @Test
    @WithMockUser
    void lastLogin() throws Exception {
        var userReq = new UserReq();
        userReq.setUsername("matthew");

        var lastLogined = "2023-10-27T10:30:00";

        when(userService.lastLogin(any()))
                .thenReturn(LocalDateTime.parse(lastLogined));

        mockMvc.post().with(csrf())
                .uri("/user/lastLogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq))
                .assertThat()
                .hasStatusOk().bodyJson().extractingPath("$.data")
                .isEqualTo(lastLogined);
    }

    @Test
    @WithMockUser
    void batchDormantUser() throws Exception {
        var users = List.of(
                new User("matthew"),
                new User("ohhoonim"));

        mockMvc.post().with(csrf())
                .uri("/user/batchDormantUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users))
                .assertThat()
                .hasStatusOk();

        verify(userService, times(1))
                .batchDormantUser(any());
    }

    @Test
    @WithMockUser
    void modifyActivate() throws Exception {

        var userReq = new UserReq();
        userReq.setUsername("matthew");
        userReq.setEnabled(false);

        when(userService.modifyActivate(any(), eq(false)))
                .thenReturn(UserEnableStatus.Deactiviate);

        mockMvc.post().with(csrf())
                .uri("/user/modifyActivate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq))
                .assertThat()
                .hasStatusOk().bodyJson().extractingPath("$.data")
                .isEqualTo(UserEnableStatus.Deactiviate.toString());
    }
}
