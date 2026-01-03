package dev.ohhoonim.policy;

/////////////////////////////////////////////////////////////////
/// 
/// 인가/정책 시스템 (Context Authorization & Policy) 
/// 유스케이스 다이어그램
/// 
/////////////////////////////////////////////////////////////////

/*
@startuml Authorization_Policy_UC
left to right direction
title 인가/정책 Context (Authorization & Policy) 유스케이스 다이어그램

actor "정책 관리자" as PolicyAdmin
actor "메뉴 관리 Context" as Menu_BC
actor "Identity Context" as Identity_BC
actor "환경 속성 제공 시스템" as Env_Provider

actor "감사 로그 및 추적 관리" as Audit_log 

rectangle "인가/정책 시스템 (Authorization & Policy Context)" {
    usecase "ABAC 정책 및 규칙 관리 (UC-A1)" as UC_Policy_CRUD
    usecase "ABAC 정책 평가 요청 처리 (UC-A2)" as UC_PDP_Eval
    usecase "정책 정보 제공 (PIP) 및 수집" as UC_PIP
}

' 정책 관리자의 역할: 정책 설정 및 관리
PolicyAdmin -up- UC_Policy_CRUD

' 핵심 기능: 정책 평가
Menu_BC --> UC_PDP_Eval : <<request>> 인가 요청
UC_PDP_Eval .> UC_Policy_CRUD : <<uses>> 정의된 정책 참조
UC_PDP_Eval .> UC_PIP : <<uses>> 평가를 위한 속성 요청

' PIP의 역할: 속성 수집 및 제공
UC_PIP .down.> Identity_BC : <<uses>> 사용자 속성 질의
UC_PIP .> Menu_BC : <<uses>> 리소스 속성 질의 
UC_PIP .down.> Env_Provider : <<uses>> 환경 속성 질의 

' 거버넌스 및 감사
UC_PDP_Eval .up.> Audit_log: <<logs>> 인가 결정 기록
UC_Policy_CRUD ..> Audit_log: <<logs>> 정책 변경 기록

@enduml
 */
