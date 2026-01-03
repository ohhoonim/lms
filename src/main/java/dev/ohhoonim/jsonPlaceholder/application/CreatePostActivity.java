package dev.ohhoonim.jsonPlaceholder.application;

import dev.ohhoonim.jsonPlaceholder.model.Post;

public interface CreatePostActivity {
    
    Post createPost(Post newPost);
}

/*
@startuml
title 활동 다이어그램: 게시물 생성 (POST /posts)

start
:클라이언트, 게시물 데이터 준비;
:클라이언트, /posts로 POST 요청 전송;
:서버, 요청 수신 및 데이터 처리 (유효성 검사 등);
:서버, 새로운 게시물 ID 생성 (e.g., 101);
:서버, HTTP 201 Created 및 새 게시물 객체 반환;
:클라이언트, 응답 수신;
stop
@enduml
 */