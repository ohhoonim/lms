package dev.ohhoonim.jsonPlaceholder.application;

import java.util.List;

import dev.ohhoonim.jsonPlaceholder.model.Comment;

public interface ViewPostComments {

  List<Comment> postComments(int postId); 
    
}

/*
@startuml
title 활동 다이어그램: 게시물 댓글 조회 (GET /posts/{id}/comments)

start
:클라이언트, 게시물 ID 식별 (e.g., 1);
:클라이언트, /posts/1/comments로 GET 요청 전송;
:서버, 요청 수신;
:서버, 게시물 ID 1에 해당하는 댓글 검색;

if (댓글이 존재하는가?) then (예)
  :서버, 해당 댓글 목록 검색 및 준비;
  :서버, HTTP 200 OK 및 댓글 목록 반환;
else (아니오)
  :서버, HTTP 200 OK 및 빈 목록 또는 404 반환 (API 정책에 따름);
endif

:클라이언트, 응답 수신;
stop
@enduml
 */