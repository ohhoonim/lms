package dev.ohhoonim.user.activity;

import dev.ohhoonim.user.User;

public interface WithdrawActivity {
    
    void withdrawUser(User user);
}
/*
```plantuml
@startuml

title 회원 탈퇴

start
:인사 시스템으로부터 퇴사자 정보 수신 (배치)(BatchRegisterActivity);
:해당 퇴사자의 계정 정보 조회;
:계정 상태를 '비활성화'로 변경(LockActivity);
:계정 활성화/비활성화 로그 기록;
stop
@enduml
```
 */