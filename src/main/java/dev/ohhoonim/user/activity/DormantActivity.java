package dev.ohhoonim.user.activity;

import java.time.LocalDateTime;
import java.util.List;

import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.user.User;

public interface DormantActivity {
    
    void dormantUser(User username);

    int increaseFailedAttemptCount(User username, boolean isInit);

    LocalDateTime lastLogin(User username);

    void batchDormantUser(List<User> users);
}
/*

```plantuml
@startuml

title 휴면 계정 관리 (시스템 자동화)

start
:정기적인 스케줄러 실행 (매일/매주);
:모든 사용자 계정 정보 조회(ViewInfoActivity);
:각 계정의 최종 로그인 기록 확인;
if (최종 로그인 기록이 3개월을 초과했는가?) then (예)
  :계정 상태를 '잠금'으로 변경(LockActivity);
  :휴면 계정으로 처리되었음을 사용자에게 이메일로 발송;
  :로그 기록: '휴면 계정 잠금 처리됨';
  stop
else (아니오)
  :다음 계정으로 이동;
endif
@enduml
```

### 휴면 계정 잠금 해제 (사용자 개입)

```plantuml
@startuml

title 휴면 계정 잠금 해제 (사용자 개입)

start
:잠금 처리된 계정으로 로그인 시도;
:로그인 실패 메시지 표시: '휴면 계정으로 잠금 처리되었습니다. 비밀번호를 변경하여 잠금을 해제하세요.';
:비밀번호 변경 유스케이스 실행;
:사용자 본인 인증;
:새로운 비밀번호 입력;
:비밀번호 변경 성공;
:계정 상태를 '잠금 해제'로 변경;
:성공적으로 잠금 해제되었음을 알림;
:메인 페이지로 이동;
stop
@enduml
```
 */