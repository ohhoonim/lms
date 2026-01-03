package dev.ohhoonim.menu.application;

import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.model.MenuItem;

// UI-MENU-01
public interface MenuCrudActivity {

    void addMenu(MenuItem newMenuItem);

    void modifyMenu(MenuItem menuItem);

    void removeMenu(Id menuItemId);

    MenuItem menuById(Id menuItemId);
}

///////////////////////////////////////////////////////
/// 
/// 유스케이스 01: 메뉴 구조 및 속성 CRUD (관리자 기능)
/// 
/// ///////////////////////////////////////////////////////
/*
@startuml UC01_Menu_CRUD_Activity
title UC-01: 메뉴 구조/속성 CRUD 액티비티 다이어그램

start
:메뉴 관리자, 메뉴 CRUD 요청 시작;

fork
    :메뉴 엔티티(Menu_ID, Parent_ID, Target_URL 등) 생성/수정/삭제;
    :Sort_Order, Is_Active 등 기능적 속성 업데이트;
    
    if (ABAC 관련 속성 변경?) then (Yes: Menu_Code or Required_Permission_Tag)
        :ABAC 속성(Tag) 변경;
        :변경 내용 유효성 검증 (Menu_Code Unique 등);
        
        partition "Authorization BC Integration via ACL" {
            :메뉴 변경 이벤트(Menu_Updated) 생성;
            :Anti-Corruption Layer (ACL) 호출;
            :Authorization Context로 정책 변경 이벤트 전달;
            :정책 서버(PDP)에 정책 업데이트 요청 (예: Required_Permission_Tag 변경 반영);
            :정책 변경 완료 응답 수신;
        }
    else (No)
    endif
    
    :메뉴 변경 내용 DB 저장;
    
end fork

:변경 사항 Commit;


partition "Governance and Audit Log" {
    :감사 대상 이벤트 확인 (CRUD Operation, 변경 필드);
    :상관관계 ID (Correlation ID) 생성;
    :메뉴 관리 BC 감사 로그 기록 (Actor, Target, Action, Status, Correlation ID);
    :정책 서버 로그(Authorization BC)와 연결;
}

stop
@enduml
*/
