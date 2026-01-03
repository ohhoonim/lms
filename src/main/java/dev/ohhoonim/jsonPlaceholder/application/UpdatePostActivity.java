package dev.ohhoonim.jsonPlaceholder.application;

import dev.ohhoonim.jsonPlaceholder.model.Post;

public interface UpdatePostActivity {

  Post updatePost(Post post);
    
}
/*
@startuml
title 활동 다이어그램: 게시물 수정 (PUT/PATCH /posts/{id})

start
:클라이언트, 업데이트할 데이터 준비;
:클라이언트, /posts/1로 PUT 또는 PATCH 요청 전송;
:서버, 요청 수신;
:서버, ID 1인 게시물 검색;

if (게시물을 찾았는가?) then (예)
  :서버, 업데이트 데이터 적용 (시뮬레이션);
  :서버, HTTP 200 OK 및 업데이트된 게시물 객체 반환;
else (아니오)
  :서버, HTTP 404 Not Found 반환;
endif

:클라이언트, 응답 수신;
stop
@enduml
 */