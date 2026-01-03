package dev.ohhoonim.menu.application;

import dev.ohhoonim.menu.model.MenuTree;

// UC-MENU-03
public interface MenuVisiblilityActivity {

    MenuTree createTree();
}

///////////////////////////////////////////////////////
/// 
/// 03: 사용자 메뉴 가시성 결정 및 렌더링 (일반 사용자 기능)
/// 
/// ///////////////////////////////////////////////////////
/*
@startuml UC03_Menu_Visibility_Activity
title UC-03: 사용자 메뉴 가시성 결정 및 렌더링 액티비티 다이어그램

start
:사용자 백오피스 접속 또는 세션 시작;
:사용자 ID 및 세션 정보 획득;

if (메뉴 가시성 캐시 존재 & 유효?) then (Yes)
    :캐시된 Menu Tree DTO 사용;
    :메뉴 렌더링;
    stop
else (No or Invalid)
    :Menu Management Context (MMC)에서 전체 메뉴 구조 (Menu Entities) 조회;
    
    partition "Policy Decision Point (PDP) Call via ACL" {
        :ACL, Authorization Context에 PDP 호출 요청 (Anti-Corruption Layer);
        :사용자 속성 (Identity BC 참조: Subject Attributes) 및 환경 속성 수집 (PIP 역할);
        :각 메뉴 엔티티의 속성 (Resource Attributes)을 PDP 입력으로 준비;
        
        :PDP, ABAC 정책 평가 수행;
        :Policy Set (MenuVisibilityPolicies) 및 Rule 평가 (예: Tag INTERSECT 검증);
        :각 메뉴 항목별 접근 결정 (Permit / Deny);
        
        :ACL, PDP 응답을 Menu Management BC 모델로 변환 (List<AuthorizedMenuId>);
    }
    
    :MMC, 인가 허용 정보를 사용하여 Menu Tree DTO 구성 (Composition);
    :접근 허용된 메뉴만 계층 구조에 따라 필터링/정렬;
    :Menu Tree DTO 캐싱 (성능 최적화);
    :메뉴 렌더링;
    stop
endif
@enduml
*/
