package dev.ohhoonim.component.container;

/**
 * 
## Layered Architecture를 위한 레이어간 container 정의

### Entity
- 이것은 컨테이너가 아님
- 도메인 레이어에서만 사용

### Dto
- 외부 시스템에 데이터를 전송하거나 받을때 사용하는 컨테이너
- 프리젠테이션 레이어에서 사용된다
- OUT : **Response** 로 표기한다. json으로 변환된다 
- IN : **`Search<T>`** 로 표기한다. Page외 기타 필요한 정보를 추가 담고 있다.
    - `<T>`에 대하여 
        - CUD 액션은 컨테이너 없이 Entity로 바로 받을 수도 있다
        - req객체가 필요한 경우 [주요엔티티명]+Req로 구성

### Vo
- 도메인, 서비스 레이어 간 사용하는 컨테이너 
- **`Vo<T>`** 로 표기한다
- Vo에는 Page 등 부가 정보를 포함할 수 있다 
 */
public sealed interface Container
        permits Response, Search, Vo {

}
