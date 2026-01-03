package dev.ohhoonim.jsonPlaceholder;
/*

@startuml
left to right direction
actor User

rectangle "Post Management System" {
  usecase "게시물 생성" as UC1
  usecase "게시물 상세 조회" as UC2
  usecase "모든 게시물 목록 조회" as UC3
  usecase "게시물 수정" as UC4
  usecase "게시물 삭제" as UC5
  usecase "게시물 검색/필터링" as UC6
  usecase "게시물 댓글 조회" as UC7

  User --> UC1
  User --> UC2
  User --> UC3
  User --> UC4
  User --> UC5
  User --> UC6
  User --> UC7
}
@enduml

 */


/*
@startuml
skinparam ClassAttributeIconSize 0
skinparam monochrome reverse

' ----------------------------------------------------
' Aggregate Root 정의 및 애그리거트 경계 표시
' ----------------------------------------------------

package "Post Aggregate" #DDDDDD {
 class Post <<Aggregate Root>> {
   + id : int
   + userId : int <<ID Reference>>
   --
   + title : string
   + body : string
   --
   + addComment(comment: Comment)
 }

 class Comment <<Entity>> {
   + id : int
   + postId : int <<FK>>
   --
   + name : string
   + email : string
   + body : string
 }
 ' Post와 Comment는 합성(Composition, 소유) 관계
 Post "1" *-- "0..*" Comment : Comments
}

' ----------------------------------------------------
' 외부 Aggregate Root 및 엔티티 정의
' ----------------------------------------------------

class User <<Aggregate Root>> {
 + id : int
 --
 + name : string
 + username : string
 + email : string
}

package "Album Aggregate" #DDDDDD {
 class Album <<Aggregate Root>> {
   + id : int
   + userId : int <<ID Reference>>
   --
   + title : string
 }

 class Photo <<Entity>> {
   + id : int
   + albumId : int <<FK>>
   --
   + title : string
   + url : string
 }
 Album "1" *-- "0..*" Photo : Photos
}

class Todo <<Aggregate Root>> {
 + id : int
 + userId : int <<ID Reference>>
 --
 + title : string
 + completed : boolean
}


' ----------------------------------------------------
' 애그리거트 간의 ID 참조 관계 (느슨한 결합)
' ----------------------------------------------------

' Post는 User의 ID를 참조함
Post .right.> User : userId 참조 (N:1)

' Album은 User의 ID를 참조함
Album .right.> User : userId 참조 (N:1)

' Todo는 User의 ID를 참조함
Todo .up.> User : userId 참조 (N:1)

@enduml
 */
