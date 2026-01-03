package dev.ohhoonim.jsonPlaceholder.application;

public interface DeletePostActivity {
    
    void deletePost(int postId);
}

/*
@startuml
title 활동 다이어그램: 게시물 삭제 (DELETE /posts/{id})

start
:클라이언트, 삭제할 게시물 ID 식별 (e.g., 1);
:클라이언트, /posts/1로 DELETE 요청 전송;
:서버, 요청 수신;
:서버, 삭제 처리 (시뮬레이션);
:서버, HTTP 200 OK 반환;
:클라이언트, 응답 수신;
stop
@enduml 
 */