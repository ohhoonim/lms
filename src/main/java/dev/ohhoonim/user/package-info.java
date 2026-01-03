package dev.ohhoonim.user;
/*


## usecase

```plantuml
@startuml
left to right direction
actor "일반 사용자" as User
actor "관리자" as Admin

rectangle "사용자 계정 관리 시스템" {
  usecase "로그인" as Login
  usecase "회원 가입" as Register
  usecase "계정 정보 조회" as ViewInfo
  usecase "계정 정보 수정" as ModifyInfo
  usecase "비밀번호 재설정" as ResetPassword
  usecase "회원 탈퇴" as Withdraw
  usecase "계정 활성화/비활성화" as Activate
  usecase "계정 잠금/잠금 해제" as Lock
  usecase "계정 일괄 등록" as BatchRegister
  usecase "계정 일괄 수정" as BatchUpdate
  usecase "휴면 계정 관리" as Dormant
}

User -- Login
User -- Register
User -- ViewInfo
User -- ModifyInfo
User -- ResetPassword
User -- Withdraw

Admin -- Login
Admin -- ViewInfo
Admin -- ModifyInfo
Admin -- Activate
Admin -- Lock
Admin -- BatchRegister
Admin -- BatchUpdate
Admin -- Dormant

Login ..> (사용자 정보 확인) : include
ModifyInfo ..> (인증) : include
ResetPassword ..> (본인 확인) : include

@enduml
```
 */