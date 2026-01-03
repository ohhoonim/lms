package dev.ohhoonim.user.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.github.f4b6a3.ulid.UlidCreator;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.user.application.UserEnableStatus;
import dev.ohhoonim.user.application.UserLockStatus;
import dev.ohhoonim.user.application.UserReq;
import dev.ohhoonim.user.model.User;
import dev.ohhoonim.user.model.UserAttribute;
import dev.ohhoonim.user.model.UserService;
import dev.ohhoonim.user.port.UserPort;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserPort userPort;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 인가를 확인하고, 사용자 정보를 가져온다, activate/unlock")
    void isValidUser() {
        when(userPort.verifyLoginUser(any(), any())).thenReturn(Optional.of(new User()));
        when(passwordEncoder.encode(any())).thenReturn("test");

        var findedUser = userService.isValidUser("matthew", "test");
        assertThat(findedUser).isInstanceOf(User.class);

        when(userPort.verifyLoginUser(any(), any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.isValidUser("matthew", "test"))
                .hasMessageContaining("회원을 찾을 수 없습니다");
    }

    @Test
    @DisplayName("계정 활성/비활성")
    void modifyActivate() {
        var userEnableStatus = userService.modifyActivate(new User("matthew"), true);

        assertThat(userEnableStatus).isEqualTo(UserEnableStatus.Activate);
        verify(userPort, times(1)).changeActivate(any(), anyBoolean());
        assertThatThrownBy(() -> userService.modifyActivate(new User(), true))
                .hasMessageContaining("사용자 계정 아이디를 확인해주세요");
    }

    // 상태 조합을 통한 계정 관리 시나리오

    /*
     *  인가 상태 : unlock : 로그인 가능, lock : 로그인 불가능
     *  인증 상태 : activate : 시스템 이용가능, deactivate : 시스템 이용불가능(프로파일 만 관리 가능) 
     * 
     * activate	unlock	정상 계정: 
     *  - 로그인 및 모든 서비스 이용이 가능한 상태입니다.	
     *  - 대부분의 일반 사용자 계정
     * 
     * activate	lock	일시 정지 계정: 
     *  - 계정은 활성화되어 있지만, 현재 로그인이 불가능한 상태입니다.	
     *  - 비밀번호 연속 실패
     *  - 관리자가 보안상 잠금 처리
     * 
     * deactivate	unlock	비활성 계정: 
     *  - 계정 자체가 사용 중단, 로그인이 가능합니다.	
     *  - 휴면 계정으로 전환된 경우
     * 
     * deactivate	lock	비활성 계정: 
     *  - 로그인 불가능 
     *  - 사용자가 탈퇴를 요청한 경우
     * 
     */

    @Test
    @DisplayName("회원 탈퇴 - deactivate and lock 처리")
    void withdrawUser() {
        var userId = new Id();
        var user = new User(userId);
        user.setUsername("matthew");
        assertThat(user).extracting(u -> user.getId()).isEqualTo(userId)
                .extracting(u -> user.getUsername()).isEqualTo("matthew");

        userService.withdrawUser(user);

        verify(userPort, times(1)).changeLock(eq("matthew"), eq(true), any());
        verify(userPort, times(1)).changeActivate(eq("matthew"), eq(false));
    }

    @Test
    void modifyLock() {
        var userId = new Id();
        var user = new User(userId);
        user.setUsername("matthew");

        var result = userService.modifyLock(user, true, null);
        assertThat(result).isEqualTo(UserLockStatus.Lock);

        verify(userPort, times(1)).changeLock(eq("matthew"), eq(true), any());
    }

    @Test
    void verifyPassword() {
        var id = Id.valueOf(UlidCreator.getMonotonicUlid().toString());
        var userId = new User(id);
        userId.setUsername("matthew"); // 필수. 백오피스에 따라 사번이 될 수도 있다

        when(userPort.verifyLoginUser(any(), any())).thenReturn(Optional.empty());
        var verified = userService.verifyPassword(userId, "viwosdh");
        assertThat(verified).isFalse();

        var nullUsername = new User(id);
        assertThatThrownBy(() -> userService.verifyPassword(nullUsername, "sdfsdaf"))
                .hasMessage("사용자 계정 아이디를 확인해주세요");
    }

    @Test
    @DisplayName("패스워드를 변경하고 Lock을 풀어준다, 최초 로그인 처리를 위해 activate도 같이 처리해준다")
    void resetPassword() {
        var userId = new Id();
        var user = new User(userId);
        user.setUsername("matthew");
        when(passwordEncoder.encode(any())).thenReturn("encoded_test");

        userService.resetPassword(user, "abcd", "abcd");

        verify(userPort, times(1)).resetPassword(eq("matthew"), eq("encoded_test"));
        verify(userPort, times(1)).changeLock(eq("matthew"), eq(false), any());
        verify(userPort, times(1)).changeActivate(eq("matthew"), eq(true));
    }

    @Test
    @DisplayName("사용자를 추가한다. deactivate/unlock, 최초 로그인 시 패스워드 변경 후 activate로 변경")
    void register() {
        var user = new User();
        // required
        user.setUsername("200209021"); // 사번
        // options
        user.setName("박 전무");
        user.setEmail("");
        user.setContact("");
        // options - attribute
        user.setAttributes(List.of());

        // id, 초기패스워드, accountStatus 는 register에서 처리
        // 초기 패스워드는 username과 동일하다
        userService.register(user);

        verify(userPort, times(1)).registUser(any(User.class));
        verify(userPort, never()).registUserAttribute(any());
    }

    @Test
    void modifyInfo() {
        var userId = new Id();
        var user = new User(userId);
        user.setUsername("matthew");

        user.setAttributes(List.of(new UserAttribute("job", "support")));

        userService.modifyInfo(user);

        verify(userPort, times(1)).modifyInfo(any(User.class));
        verify(userPort, times(1)).modifyAttribute(any());
    }

    @Test
    void dormantUser() {
        var userId = new Id();
        var user = new User(userId);
        user.setUsername("matthew");

        userService.dormantUser(user);

        verify(userPort).changeActivate(any(), eq(false));
        verify(userPort).changeLock(any(), eq(false), any());
    }

    @Test
    void increaseFailedAttemptCount() {
        var username = new User();
        username.setUsername("matthew");

        when(userPort.increaseFailedAttemptCount(any(), eq(false))).thenReturn(2);

        var result = userService.increaseFailedAttemptCount(username, false);
        assertThat(result).isEqualTo(2);

        // 초기화를 실행하면 카운트가 0이 된다
        when(userPort.increaseFailedAttemptCount(any(), eq(true))).thenReturn(0);

        var initResult = userService.increaseFailedAttemptCount(username, true);
        assertThat(initResult).isEqualTo(0);
    }

    @Test
    void lastLogin() {
        var username = new User();
        username.setUsername("matthew");

        var now = LocalDateTime.now();

        when(userPort.lastLogin(any())).thenReturn(now);
        var result = userService.lastLogin(username);

        assertThat(result).isEqualTo(now);
    }

    @Test
    void batchDormantUser() {
        var user1 = new User();
        user1.setUsername("matthew");
        var user2 = new User();
        var user3 = new User();
        user3.setUsername("ohhoonim");

        var users = List.of(user1, user2, user3);

        userService.batchDormantUser(users);

        verify(userPort, times(2)).changeActivate(any(), eq(false));
        verify(userPort, times(2)).changeLock(any(), eq(false), any());
    }

    @Test
    void users() {
        Search<UserReq> condition = new Search<>();
        when(userPort.findUsers(any())).thenReturn(new Vo(List.of()));
        var users = userService.users(condition);
        assertThat(users).isInstanceOf(Vo.class);
    }

    @Test
    void userInfo() {
        var username = new User("matthew");

        when(userPort.findByUsername(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.userInfo(username))
                .hasMessageContaining("사용자를 찾을 수 없습니다");


    }



}
