package dev.ohhoonim.user.application;

import java.time.LocalDateTime;
import java.util.List;

import dev.ohhoonim.user.model.PendingChange;
import dev.ohhoonim.user.model.User;

public interface BatchUpdateActivity {

    int batchUpdate(List<User> users);

    int applyPendingChangesToUser(LocalDateTime effectiveDate);

}
/*

```plantuml
@startuml

title 계정 일괄 수정(관리자 - 수동방식)

start
:로그인 성공;
:내비게이션 바에서 '사용자 관리' 메뉴 선택;
:사용자 목록 페이지로 이동(ViewInfoController);
:일괄 수정할 계정들 선택;
:일괄 변경 팝업 표시;
:활성화 여부 및 잠금 여부 설정(ActivateActivity, LockActivity);
if (잠금 여부 설정?) then (예)
    :적용 시작일 설정 팝업 표시;
    :적용 시작일 입력;
    :확인 버튼 클릭;
    :계정 잠금 상태 및 시작일 일괄 적용;
    stop
else (아니오)
    :활성화 여부 일괄 적용;
    stop
endif
@enduml
```

### 계정 일괄 수정 (인사 시스템 연동 - 자동 방식)
```plantuml
@startuml

title 계정 일괄 수정(인사 시스템 연동 - 자동 방식)

start
:관리자, 인사시스템 연동 시각 및 주기 설정;
:설정된 시각에 스케줄러 실행;
:인사 시스템에서 최신 계정 정보 조회;
:사용자 관리 시스템의 계정 정보와 비교;
if (변경된 속성(활성화/잠금)이 있는가?) then (예)
    :변경된 속성 값 일괄 변경(ViewInfoActivityS);
    if (특정 적용 시작일이 있는가?) then (예)
        :적용 시작일 설정;
    else (아니오)
        :즉시 변경;
    endif
    :변경 완료 로그 기록;
    stop
else (아니오)
    :특이사항 없음 로그 기록;
    stop
endif
@enduml
```

 */