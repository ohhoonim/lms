package dev.ohhoonim.user.infra;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.auditing.dataBy.Modified;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.user.application.UserReq;
import dev.ohhoonim.user.model.ChangeDetail;
import dev.ohhoonim.user.model.PendingChange;
import dev.ohhoonim.user.model.User;
import dev.ohhoonim.user.model.UserAttribute;
import dev.ohhoonim.user.port.PendingChangePort;
import dev.ohhoonim.user.port.UserPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAdaptor implements UserPort, PendingChangePort {

    private final UserMapper userMapper;
    private final PendingMapper pendingMapper;

    @Override
    public Optional<User> verifyLoginUser(String username, String encodedPassword) {
        User user = userMapper.verifyLoginUser(username, encodedPassword);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public void changeActivate(String username, boolean isEnable) {
        userMapper.changeActivate(username, isEnable);
    }

    /**
     * effectiveDate 값이 없으면 바로 변경하고 
     * 그 외에는 pending 테이블 이용
     */
    @Override
    public void changeLock(String username, boolean isLock, LocalDateTime effectiveDate) {
        if (effectiveDate != null) {
            var pending = new PendingChange();
            var pendingChangeId = new Id();
            pending.setPendingChangeId(pendingChangeId);
            User userId = userMapper.getUserId(username);
            pending.setUser(userId);
            pending.setChangeType(PendingChangeType.Lock.masterCode());
            pending.setStatus(PendingChangeStatus.Pending.masterCode());
            pending.setCreator(new Created());
            pending.setModifier(new Modified());
            pendingMapper.addPending(pending);
        } else {
            userMapper.changeLock(username, isLock);
        }
    }

    @Override
    public void resetPassword(String username, String newEncodedPassword) {
        userMapper.resetPassword(username, newEncodedPassword);
    }

    @Override
    public void registUser(User user) {
        userMapper.saveUser(user);
    }

    @Override
    public void registUserAttribute(List<UserAttribute> userAttributes) {
        for (var attribute : userAttributes) {
            attribute.setAttributeId(new Id());
            attribute.setCreator(new Created());
            attribute.setCreator(new Modified());
            userMapper.saveUserAttribute(attribute);
        }
    }

    @Override
    public void modifyInfo(User userInfo) {
        // email, contact만 수정 가능. 추가 프로퍼티 필요시 쿼리 수정필요
        userMapper.modifiyInfo(userInfo);
    }

    @Override
    public void modifyAttribute(List<UserAttribute> attributes) {
        for (var attribute : attributes) {
            String currentValue = userMapper.findAttributeBy(
                    attribute.getUser(), attribute.getName());
            if (currentValue == null) {
                userMapper.saveUserAttribute(attribute);
            } else if (!currentValue.equals(attribute.getValue())) {
                userMapper.modifyAttribute(attribute);
            }
        }
    }

    @Override
    public int increaseFailedAttemptCount(String username, boolean isInit) {
        Integer count = userMapper.failedCount(username);
        if (count == null) {
            throw new RuntimeException("사용자가 없습니다");
        }
        var newCount = isInit ? 0 : count + 1;
        userMapper.increaseFailedAttemptCount(username, newCount);
        return newCount;
    }

    @Override
    public LocalDateTime lastLogin(String username) {
        // TODO 로그인 할 때 이벤트 처리 작업 후 구현 
        throw new UnsupportedOperationException("Unimplemented method 'lastLogin'");
    }

    @Override
    public Vo<List<User>> findUsers(Search<UserReq> condition) {
        Page reqPage = condition.getPage() == null ? new Page() : condition.getPage();
        Integer totalCount = userMapper.findUsersTotal(condition.getReq());
        if (totalCount > 0) {
            List<User> users = userMapper.findUsers(condition.getReq(), reqPage);
            Id lastSeenKey = users.stream().reduce((f, s) -> s).map(s -> s.getUserId())
                    .orElseGet(() -> null);
            return new Vo(users, new Page(totalCount, reqPage.limit(), lastSeenKey));
        }
        return new Vo(List.of(), new Page());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public List<PendingChange> pendings(LocalDateTime effectiveDate) {
        return pendingMapper.pendings(effectiveDate);
    }

    @Override
    public void addPending(PendingChange pendingChange) {
        pendingMapper.addPending(pendingChange);
    }

    @Override
    public void addChangeDetail(ChangeDetail changeDetail) {
        pendingMapper.addChangeDetail(changeDetail);
    }
}
