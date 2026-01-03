package dev.ohhoonim.component.auditing.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EntitySetBeanRegistrar.class)
public @interface BusinessEntityScan {
	String[] basePackages();
}
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
