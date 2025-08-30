package dev.ohhoonim.component.auditing.dataBy.entityScan;
/**

## business entity를 스캔하는 법 
- business 엔티티를 작성할 때 Entity 인터페이스를 implements해준다. 
- 아래와 같이 @BusinessEntityScan를 붙여준다 
- "businessEntityMap" 으로 빈 주입을 받으면 된다.

```java

@SpringBootApplication
@BusinessEntityScan(basePackages = {"com.ohhoonim.demo_auditing.para"})
public class DemoAuditingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAuditingApplication.class, args);
	}
}

```
 */