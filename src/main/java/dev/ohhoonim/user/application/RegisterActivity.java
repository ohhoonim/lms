package dev.ohhoonim.user.application;

import dev.ohhoonim.user.model.User;

public interface RegisterActivity {

    void register(User user);
    
}
/*

```plantuml
@startuml

title 회원가입

start
:회원가입 링크 클릭;
:회원가입 페이지로 이동;
:아이디와 패스워드, 기타 정보 입력;

repeat
    :두 패스워드 입력;
    if (패스워드 일치?) then (아니오)
        :경고 메시지 표시: '패스워드가 일치하지 않습니다';
    else (예)
        break
    endif
repeat while (true)

:회원가입 버튼 클릭;
:회원가입 정보 저장 및 관리자 승인 요청;
:메시지 표시: '관리자에게 승인을 요청하였습니다';
detach
:관리자, "계정 활성화/비활성화" 유스케이스\n'계정 활성화' 액티비티 실행;
if (계정 활성화 승인?) then (예)
    :회원가입 요청자에게 이메일로 승인 결과 발송;
    :사용자, "로그인" 액티비티를 통해 로그인 가능;
    stop
else (아니오)
    :계정 활성화 거부 및 알림;
    stop
endif
@enduml
```
 */