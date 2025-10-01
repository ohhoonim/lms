package dev.ohhoonim.user.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.user.ChangeDetail;
import dev.ohhoonim.user.PendingChange;
import dev.ohhoonim.user.User;
import dev.ohhoonim.user.UserAttribute;
import dev.ohhoonim.user.activity.port.HrClient;
import dev.ohhoonim.user.activity.port.PendingChangePort;
import dev.ohhoonim.user.activity.port.UserPort;
import dev.ohhoonim.user.activity.service.UserBatchService;
import dev.ohhoonim.user.activity.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserBatchServiceTest {

    @InjectMocks
    UserBatchService userBatchService;

    @Mock
    PendingChangePort pendingChangePort;

    @Mock
    HrClient hrClient;

    @InjectMocks
    UserService userService;

    @Mock
    UserPort userPort;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        this.userBatchService = new UserBatchService(userService,
                pendingChangePort, hrClient);
    }

    @Test
    void batchUpdate() {
        var users = List.of(
                new User("matthew"),
                new User(),
                new User("ohhoonim"),
                new User("glam"));
        var addedCount = userBatchService.batchUpdate(users);

        assertThat(addedCount).isEqualTo(3);
    }

    @Test
    @DisplayName("배치 스케줄러로 데이터를 가져오면 우선 pending change에 저장함")
    void fetchHrSystemToPendingChange() {
        int count = userBatchService.fetchHrSystemToPendingChange();
        assertThat(count).isEqualTo(0); // 외부시스템 미비로 미구현 
    }

    @Test
    void applyPendingChangesToUser() {

        var pending1 = new PendingChange();
        pending1.setUser(new User("matthew"));
        var change1 = new ChangeDetail();
        change1.setAttributeName("job");
        change1.setNewValue("engineer");
        pending1.setChangeDetails(List.of(change1));

        var pending2 = new PendingChange();
        pending2.setUser(new User("ohhoonim"));
        var change2 = new ChangeDetail();
        change2.setAttributeName("job");
        change2.setNewValue("engineer");
        pending2.setChangeDetails(List.of(change2));

        var pending3 = new PendingChange();
        pending3.setUser(new User("alison"));
        var change3 = new ChangeDetail();
        change3.setAttributeName("job");
        change3.setNewValue("engineer");
        pending3.setChangeDetails(List.of(change3));

        var pendings = List.of(pending1, pending2, pending3);
        when(pendingChangePort.pendings(any())).thenReturn(pendings);

        int resultCount = userBatchService.applyPendingChangesToUser(LocalDateTime.now());

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    void batchRegister() {
        var users = List.of(
            new User("matthew"),
            new User("ohhoonim"),
            new User("alision")
            );

        int resultCount = userBatchService.batchRegister(users);
        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    void translateCsvToUsers() {
        // test code 생략
    }

    @Test
    void translateExcelToUsers() {
       // test code 생략 
    }
}
