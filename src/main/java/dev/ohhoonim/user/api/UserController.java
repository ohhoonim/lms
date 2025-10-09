package dev.ohhoonim.user.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.user.application.UserEnableStatus;
import dev.ohhoonim.user.application.UserLockStatus;
import dev.ohhoonim.user.application.UserReq;
import dev.ohhoonim.user.internal.UserService;
import dev.ohhoonim.user.model.User;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/withdrawUser")
    public void withdrawUser(@RequestBody @NonNull User user) {
        userService.withdrawUser(user);
    }

    @GetMapping("/users")
    public Vo<List<User>> users(@RequestBody @NonNull Search<UserReq> condition) {
        return userService.users(condition);
    }

    @GetMapping("/userInfo/{username}")
    public User userInfo(@PathVariable("username") String username) {
        return userService.userInfo(new User(username));
    }

    @PostMapping("/verifyPassword")
    public boolean verifyPassword(@RequestBody @NonNull UserReq user) {
        if (!StringUtils.hasText(user.getPassword())) {
            throw new RuntimeException("패스워드를 입력해주세요");
        }
        return userService.verifyPassword(
                new User(user.getUsername()), user.getPassword());
    }

    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody @NonNull UserReq user) {
        if (!StringUtils.hasText(user.getPassword()) ||
                !StringUtils.hasText(user.getNewPassword())) {
            throw new RuntimeException("패스워드를 입력해주세요");
        }
        userService.resetPassword(
                new User(user.getUsername()),
                user.getPassword(),
                user.getNewPassword());
    }

    @PostMapping("/register")
    public void register(@RequestBody @NonNull User user) {
        userService.register(user);
    }

    @PostMapping("/modifyInfo")
    public void modifyInfo(@RequestBody @NonNull User userInfo) {
        userService.modifyInfo(userInfo);
    }

    @PostMapping("/isValidUser")
    public User isValidUser(@RequestBody UserReq userReq) {
        if (!StringUtils.hasText(userReq.getPassword())) {
            throw new RuntimeException("패스워드를 입력해주세요");
        }
        return userService.isValidUser(userReq.getUsername(),
                userReq.getPassword());
    }

    @PostMapping("/modifyLock")
    public UserLockStatus modifyLock(@RequestBody @NonNull UserReq userReq ) {
        return userService.modifyLock(
                new User(userReq.getUsername()), 
                userReq.getLocked(), 
                userReq.getEffectiveDate());
    }

    @PostMapping("/dormantUser")
    public void dormantUser(@RequestBody @NonNull UserReq userReq) {
        userService.dormantUser(new User(userReq.getUsername()));
    }

    @PostMapping("/increaseFailedAttemptCount")
    public int increaseFailedAttemptCount(@RequestBody @NonNull UserReq userReq) {
        if (userReq.getIsInit() == null) {
            throw new RuntimeException("초기화 여부를 지정해주세요");
        }
        return userService.increaseFailedAttemptCount(
                new User(userReq.getUsername()), userReq.getIsInit());
    }

    @PostMapping("/lastLogin")
    public LocalDateTime lastLogin(@RequestBody @NonNull UserReq userReq) {
        return userService.lastLogin(new User(userReq.getUsername()));
    }

    @PostMapping("/batchDormantUser")
    public void batchDormantUser(@RequestBody @NonNull List<User> users) {
        userService.batchDormantUser(users);
    }

    @PostMapping("/modifyActivate")
    public UserEnableStatus modifyActivate(@RequestBody UserReq userReq) {
        return userService.modifyActivate(
                new User(userReq.getUsername()), 
                userReq.getEnabled());
    }
}
