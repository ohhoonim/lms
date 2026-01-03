package dev.ohhoonim.jsonPlaceholder.application;

import java.util.List;

import dev.ohhoonim.jsonPlaceholder.model.Post;

public interface SearchFilterPostsActivity {
    
  List<Post> searchFilterPosts(SearchReq searchReq);
}

/*
@startuml
title 활동 다이어그램: 게시물 검색/필터링 (GET /posts?query)

start
:클라이언트, 검색 조건 (예: userId=1) 준비;
:클라이언트, /posts에 쿼리 파라미터를 포함하여 GET 요청 전송;
:서버, 요청 수신;
:서버, 요청된 쿼리 파라미터 확인;
:서버, 게시물 데이터베이스에서 필터링 조건에 맞는 게시물 검색;

if (검색 결과가 존재하는가?) then (예)
  :서버, HTTP 200 OK 및 필터링된 게시물 목록 반환;
else (아니오)
  :서버, HTTP 200 OK 및 빈 목록 반환;
endif

:클라이언트, 응답 수신 및 필터링된 목록 표시;
stop
@enduml
 */
