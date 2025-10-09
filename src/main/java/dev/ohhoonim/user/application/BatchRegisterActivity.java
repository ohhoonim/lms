package dev.ohhoonim.user.application;

import java.io.InputStream;
import java.util.List;

import dev.ohhoonim.user.model.User;

public interface BatchRegisterActivity {
    
    int batchRegister(List<User> users);

    List<User> translateCsvToUsers(InputStream csv);

    List<User> translateExcelToUsers(InputStream excel);

    int fetchHrSystemToPendingChange();

}
/*

```plantuml
@startuml

title 파일 업로드 일괄 등록

start
:로그인 성공;
:내비게이션 바에서 '사용자 관리' 메뉴 선택;
:사용자 목록 페이지로 이동;
:계정 일괄 등록 버튼 클릭;
:파일 첨부 팝업창 표시록
:CSV 또는 엑셀 파일 첨부;
:업로드 버튼 클릭;
:파일 유효성 검사 및 데이터 파싱;
:파싱된 계정 정보 데이터베이스에 저장;
:계정정보 조회 목록에 업로드된 데이터 표시(ViewInfoActivity);
stop
@enduml
```

```plantuml
@startuml

title 배치 일괄 등록

start
:관리자, 인사 시스템 연동 시각 및 주기 설정;
:설정된 시각에 스케줄러 실행;
:인사 시스템에서 최신 계정 정보 조회;
:사용자 관리 시스템의 계정 정보와 비교;
if (새로 추가된 계정이 있는가?) then (예)
  :추가된 계정 정보를 사용자 관리 시스템에 등록;
  :등록 완료 로그 기록;
  stop
else (아니오)
  :특이사항 없음 로그 기록;
  stop
endif
@enduml
```

 */