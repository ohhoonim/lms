package dev.ohhoonim.user.activity.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.auditing.dataBy.Modified;
import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.user.AccountStatus;
import dev.ohhoonim.user.User;
import dev.ohhoonim.user.activity.ActivateActivity;
import dev.ohhoonim.user.activity.DormantActivity;
import dev.ohhoonim.user.activity.LockActivity;
import dev.ohhoonim.user.activity.LoginActivity;
import dev.ohhoonim.user.activity.ModifyInfo;
import dev.ohhoonim.user.activity.RegisterActivity;
import dev.ohhoonim.user.activity.ResetPasswordActivity;
import dev.ohhoonim.user.activity.UserEnableStatus;
import dev.ohhoonim.user.activity.UserLockStatus;
import dev.ohhoonim.user.activity.UserReq;
import dev.ohhoonim.user.activity.ViewInfoActivity;
import dev.ohhoonim.user.activity.WithdrawActivity;
import dev.ohhoonim.user.activity.port.UserPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements ActivateActivity, DormantActivity,
        LockActivity, LoginActivity, ModifyInfo, RegisterActivity, ResetPasswordActivity,
        ViewInfoActivity, WithdrawActivity {

    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User isValidUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return userPort.verifyLoginUser(username, encodedPassword)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다"));
    }

    @Override
    public UserEnableStatus modifyActivate(User username, boolean isEnable) {
        checkUsername(username);
        userPort.changeActivate(username.getUsername(), isEnable);
        return UserEnableStatus.getStatus(isEnable);
    }

    @Override
    public void withdrawUser(User user) {
        modifyLock(user, true, null);
        modifyActivate(user, false);
    }

    @Override
    public boolean verifyPassword(User username, String inputPassword) {
        checkUsername(username);
        return !userPort.verifyLoginUser(username.getUsername(), 
                    passwordEncoder.encode(inputPassword))
                .isEmpty();
    }

    @Override
    public void resetPassword(User username, String oldPassword, String newPassword) {
        checkUsername(username);
        if (!oldPassword.equals(newPassword)){
            throw new RuntimeException("패스워드가 일치하지 않습니다.");
        }

        userPort.resetPassword(username.getUsername(), passwordEncoder.encode(newPassword));
        modifyLock(username, false, null);
        modifyActivate(username, true);
    }

    @Override
    public void register(User user) {
        checkUsername(user);
        var userId = new Id();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getUsername()));

        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setSignUserId(user);
        accountStatus.setEnabled(false); 
        accountStatus.setLocked(false);
        accountStatus.setFailedAttemptCount(0);
        user.setAccountStatus(accountStatus);

        user.setCreator(new Created());
        user.setCreator(new Modified());

        userPort.registUser(user);

        if (!ObjectUtils.isEmpty(user.getAttributes())) {
            userPort.registUserAttribute(user.getAttributes());
        }
    }

    @Override
    public void modifyInfo(User userInfo) {
        checkUsername(userInfo);
        userPort.modifyInfo(userInfo);

        if (!ObjectUtils.isEmpty(userInfo.getAttributes())) {
            userPort.modifyAttribute(userInfo.getAttributes());
        }
    }

    @Override
    public UserLockStatus modifyLock(User username, boolean isLock, LocalDateTime effectiveDate) {
        checkUsername(username);
        userPort.changeLock(username.getUsername(), isLock, effectiveDate);
        return UserLockStatus.get(isLock);
    }

    @Override
    public void dormantUser(User username) {
        modifyActivate(username, false);
        modifyLock(username, false, null);
    }

    @Override
    public int increaseFailedAttemptCount(User username, boolean isInit) {
        checkUsername(username);
        return userPort.increaseFailedAttemptCount(username.getUsername(), isInit);
    }

    @Override
    public LocalDateTime lastLogin(User username) {
        checkUsername(username);
        return userPort.lastLogin(username.getUsername());
    }

    @Override
    public void batchDormantUser(List<User> users) {
        for(var user: users) {
            try {
                dormantUser(user);
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Override
    public Vo<List<User>> users(Search<UserReq> condition) {
        return userPort.findUsers(condition);
    }

    @Override
    public User userInfo(User username) {
        checkUsername(username);
        return userPort.findByUsername(username.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
    }

    private void checkUsername(User username) {
        if (username == null || username.getUsername() == null) {
            throw new RuntimeException("사용자 계정 아이디를 확인해주세요");
        }
    }
}
