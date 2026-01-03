package dev.ohhoonim.jsonPlaceholder.application;

import dev.ohhoonim.jsonPlaceholder.model.Post;

public interface ViewPostDetailsActivity {

  Post postDetails(int postId);

}

/*
@startuml
title 활동 다이어그램: 게시물 상세 조회 (GET /posts/{id})

start
:클라이언트, 조회할 게시물 ID 식별 (e.g., 1);
:클라이언트, /posts/1로 GET 요청 전송;
:서버, 요청 수신;
:서버, ID 1인 게시물 검색;

if (게시물을 찾았는가?) then (예)
  :서버, 게시물 데이터 검색;
  :서버, HTTP 200 OK 및 게시물 데이터 반환;
else (아니오)
  :서버, HTTP 404 Not Found 반환;
endif

:클라이언트, 응답 수신;
stop
@enduml
 */