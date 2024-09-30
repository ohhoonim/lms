# 학습 관리 시스템(Learning Management System, LMS)

## 명명규칙
- 스프링 Modulith를 이용하므로 Bounded Context 단위로 패키지 생성
    - 복수형으로 표기 예) course (x), courses (o)
- 다음은 고정 패키지 이다
    - *.config : 애플리케이션 config 파일
    - *.repository : 데이터 접근 레이어
- Bounded Context별 서브 패키지 구조
    - *.message : 메시지 config
    - *.model : 도메인 모델. 서브 패키지로 port 를 포함한다
        - entity, Service, *.port 를 포함한다
        - entity는 suffix를 붙이지 않는다
        - port는 Command, Query suffix를 붙인다
        - service는 Service suffix를 붙인다
    - *.service
        - factory : port 구현체
    - 패키지별 루트에는 Event 클래스 생성

## rest api 테스트 방법
- REST Client 확장 설치 : VSCode용
- /test/resources 폴더에 유스케이스별 테스트 케이스 생성

## VSCode 확장
- Extension Pack for Java(required)
- Spring Boot Extension Pack(required)
- REST Client(required)
- Docker
- GitLens
- Lombok(required)
- SQLTOOLs + PostgreSQL/Cockroch Driver
- PlantUML
- Remote Development
