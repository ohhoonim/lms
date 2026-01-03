@org.jspecify.annotations.NullMarked
package dev.ohhoonim.menu;

//////////////////////////////////////////////////////////
/// 
/// 메뉴관리 시스템 MenuManagement_UseCases 유스케이스 다이어그램


/*
@startuml 
left to right direction

actor "메뉴 관리자" as Admin
actor "일반 사용자" as User
actor "인가/정책 Context" as Authorization_BC
actor "사용자/인증 Context" as Identity_BC
    
rectangle "메뉴 관리 시스템 " {
    usecase "메뉴 구조/속성 CRUD (UC-MENU-01)" as UC_CRUD
    usecase "메뉴 표시 순서/계층 관리 (UC-MENU-04)" as UC_Order
    usecase "메뉴 ABAC 태그 설정 (UC-MENU-05)" as UC_ABAC_Tag
    usecase "메뉴 다국어 설정 (UC-MENU-02)" as UC_I18N

    usecase "사용자 메뉴 가시성 결정 (UC-MENU-03)" as UC_Visibility
    usecase "인가 결정 결과 캐싱 (UC-MENU-06)" as UC_Cache
}

Admin -- UC_CRUD
Admin -- UC_Order
Admin -- UC_ABAC_Tag
Admin -- UC_I18N

User --> UC_Visibility
UC_Visibility .> UC_Cache : <<uses>>
UC_Visibility .down.> Authorization_BC : <<includes>> 정책 질의
UC_Visibility .down.> Identity_BC : <<includes>> 사용자 속성 참조
UC_CRUD .> Authorization_BC : <<extends>> 정책 변경 이벤트 발생
UC_ABAC_Tag .> Authorization_BC : <<includes>> 정책 속성 정의

UC_CRUD .down.> Audit_Log : <<logs>> 정책속성 변경 기록 

@enduml
*/


