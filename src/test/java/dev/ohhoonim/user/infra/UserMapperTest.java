package dev.ohhoonim.user.infra;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import dev.ohhoonim.component.auditing.model.Created;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.auditing.model.Modified;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.component.sign.api.PasswordConfig;
import dev.ohhoonim.user.application.UserReq;
import dev.ohhoonim.user.model.AccountStatus;
import dev.ohhoonim.user.model.ChangeDetail;
import dev.ohhoonim.user.model.PendingChange;
import dev.ohhoonim.user.model.User;
import dev.ohhoonim.user.model.UserAttribute;

@Import(PasswordConfig.class)
@Testcontainers
@MybatisTest
public class UserMapperTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:18.1-alpine"));

    @Autowired
    UserMapper userMapper;

    @Autowired
    PendingMapper pendingMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void findByUsernamePassword() {
        var name = "matthew";
        var password = passwordEncoder.encode("abcd1234");
        var username = userMapper.findByUsernamePassword(name, password);
        assertThat(username).isNull();
    }

    @Test
    @WithMockUser
    void verifyLoginUser() {
        userMapper.saveUser(makeUser());
        var user = userMapper.verifyLoginUser(null, null);
        assertThat(user).isNull();
    }

    @Test
    void changeActivate() {
        userMapper.changeActivate(null, true);
    }

    @Test
    void getUserId() {
        var userId = userMapper.getUserId("matthew");
        assertThat(userId).isNull();
    }

    @Test
    @WithMockUser(username = "matthew")
    void changeLock() {
        var user = makeUser();
        userMapper.saveUser(user);

        var pending = new PendingChange();
        pending.setPendingChangeId(new Id());
        pending.setUser(user);
        pending.setChangeType(PendingChangeType.Lock.masterCode());
        pending.setEffectiveDate(LocalDateTime.now());
        pending.setStatus(PendingChangeStatus.Pending.masterCode());
        pending.setCreator(new Created("matthew"));
        pending.setModifier(new Modified("matthew"));

        pendingMapper.addPending(pending);
    }

    @Test
    void resetPassword() {
        userMapper.resetPassword("mathew", "encoded_password");
    }

    @Test
    @WithMockUser
    void saveUser() {
        userMapper.saveUser(makeUser());
    }

    private User makeUser() {
        var user = new User(new Id());
        user.setUsername("matthew");
        user.setPassword("encoded_password");
        user.setEmail("abc@def.com");
        user.setContact(null);

        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setSignUserId(user);
        accountStatus.setEnabled(false); // default
        accountStatus.setLocked(false); // default
        accountStatus.setFailedAttemptCount(0); // default
        user.setAccountStatus(accountStatus);

        var creator = new Created("matthew");
        var modifier = new Modified("matthew");
        user.setCreator(creator);
        user.setModifier(modifier);

        return user;
    }

    @Test
    @WithMockUser
    void saveUserAttribute() {
        var userId = makeUser();
        userMapper.saveUser(userId);

        var userAttribute = new UserAttribute("job", "counter");
        userAttribute.setAttributeId(new Id());
        userAttribute.setUser(userId);
        userAttribute.setCreator(new Created("matthew"));
        userAttribute.setModifier(new Modified("matthew"));

        userMapper.saveUserAttribute(userAttribute);
    }

    @Test
    @WithMockUser
    void modifyInfo() {
        var userInfo = makeUser();
        userMapper.modifiyInfo(userInfo);
    }

    @Test
    @WithMockUser(username = "matthew")
    void findAttributeBy() {
        var userInfo = makeUser();
        var value = userMapper.findAttributeBy(userInfo, "job");
        assertThat(value).isNull();
    }

    @Test
    @WithMockUser
    void modifiyAttribute() {
        var userAttribute = new UserAttribute("job", "counter");
        userAttribute.setUser(makeUser());
        userAttribute.setModifier(new Modified("matthew"));

        userMapper.modifyAttribute(userAttribute);
    }

    @Test
    @WithMockUser
    void failedCount() {
        Integer count = userMapper.failedCount("matthew");
        assertThat(count).isNull();
    }

    @Test
    void increaseFailedAttemptCount() {
        userMapper.increaseFailedAttemptCount("matthew", 0);
    }

    @Test
    @WithMockUser
    void findByUsername() {
        userMapper.saveUser(makeUser());
        var user = userMapper.findByUsername("matthew");
        assertThat(user.getUsername()).isEqualTo("matthew");
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    @WithMockUser
    void findUsers() {
        userMapper.saveUser(makeUser());

        var userReq = new UserReq();
        var users = userMapper.findUsers(userReq, new Page());

        Id lastSeenKey = users.stream().reduce((f, s) -> s)
                .map(s -> s.getUserId())
                .orElseGet(() -> null);
        assertThat(lastSeenKey).isInstanceOf(Id.class)
                .isNotNull();

        log.info("lastSeenKey : {}", lastSeenKey.toString());
    }

    @Test
    @WithMockUser
    void findUsersTotal() {
        userMapper.saveUser(makeUser());
        var userReq = new UserReq();
        int count = userMapper.findUsersTotal(userReq);
        assertThat(count).isEqualTo(1);
    }

    // addPending은 changeLock 참고

    @Test
    @WithMockUser(username="Admin")
    void addChangeDetail() {
        var pendingId = new Id();
        
        var pending = new PendingChange();
        pending.setPendingChangeId(pendingId);
        pending.setUser(new User("maathew")); // 신규유저는 id가 없다
        pending.setCreator(new Created("matthew"));
        pendingMapper.addPending(pending);

        var changeDetail = new ChangeDetail();
        changeDetail.setChangeDetailId(new Id());
        changeDetail.setPendingChange(pending);
        changeDetail.setAttributeName("job");
        changeDetail.setNewValue("intern");
        changeDetail.setCreator(new Created("matthew"));
        pendingMapper.addChangeDetail(changeDetail);
    }

    @Test
    void pendings() {
        var pendings = pendingMapper.pendings(LocalDateTime.now());
        assertThat(pendings).hasSize(0);
    }
}
