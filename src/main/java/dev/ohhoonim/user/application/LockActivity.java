package dev.ohhoonim.user.application;

import java.time.LocalDateTime;

import dev.ohhoonim.user.model.User;

public interface LockActivity {
    
    UserLockStatus modifyLock(User username, boolean isLock, LocalDateTime effectiveDate);
}

/*
```plantuml
@startuml

title 계정 잠금 / 잠금 해제
'인사조치 등의 사유가 발생했을때 사용

start
:로그인 성공;
:내비게이션 바에서 '사용자 관리' 메뉴 선택;
:사용자 목록 페이지로 이동;
:잠금/잠금 해제할 계정 선택;

if (계정이 현재 잠금 상태?) then (아니오)
  :계정 잠금 버튼 클릭;
  :기간 설정 팝업 표시;
  :잠금 기간 입력;
  :확인 버튼 클릭;
  :계정 상태를 '잠금'으로 변경;
  :계정 잠금 기간 설정;
  :확인 메시지 표시: "계정이 잠금 처리되었습니다.";
  stop
else (예)
  :계정 잠금 해제 버튼 클릭;
  :계정 상태를 '잠금 해제'로 변경;
  :확인 메시지 표시: "계정 잠금이 해제되었습니다.";
  stop
endif
@enduml
```
 */