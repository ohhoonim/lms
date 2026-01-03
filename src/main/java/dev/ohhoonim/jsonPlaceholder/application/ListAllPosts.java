package dev.ohhoonim.jsonPlaceholder.application;

import java.util.List;

import dev.ohhoonim.jsonPlaceholder.model.Post;

public interface ListAllPosts {

    List<Post> allPosts();
}
/*

@startuml
title 활동 다이어그램: 모든 게시물 목록 조회 (GET /posts)

start
:클라이언트, 모든 게시물 조회를 위해 준비;
:클라이언트, /posts로 GET 요청 전송;
:서버, 요청 수신;
:서버, 게시물 데이터베이스에서 모든 게시물 목록 검색;
:서버, HTTP 200 OK 및 게시물 목록(100개) 반환;
:클라이언트, 응답 수신 및 목록 표시;
stop
@enduml 

*/
