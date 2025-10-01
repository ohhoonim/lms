package dev.ohhoonim.user.activity.port;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.user.User;
import dev.ohhoonim.user.UserAttribute;
import dev.ohhoonim.user.activity.UserReq;

public interface UserPort {

    Optional<User> verifyLoginUser(String username, String encodedPassword);

    void changeActivate(String username, boolean isEnable);

    void changeLock(String username, boolean isLock, LocalDateTime effectiveDate);

    void resetPassword(String username, String newEncodedPassword);

    void registUser(User user);

    void registUserAttribute(List<UserAttribute> userAttributes);

    void modifyInfo(User userInfo);

    void modifyAttribute(List<UserAttribute> attributes);

    int increaseFailedAttemptCount(String username, boolean isInit);

    LocalDateTime lastLogin(String username);

    Vo<List<User>> findUsers(Search<UserReq> condition);

    Optional<User> findByUsername(String username);
    
}
