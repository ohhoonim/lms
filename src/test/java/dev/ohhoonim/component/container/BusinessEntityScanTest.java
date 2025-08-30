package dev.ohhoonim.component.container;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import dev.ohhoonim.component.auditing.dataBy.Entity;
import dev.ohhoonim.para.Note;

@SpringBootTest
public class BusinessEntityScanTest {
    @Autowired
    ApplicationContext context;

/*
# business entity에 대한 정보가 필요 할 때 사용

- business entity는 component.auditing.dataBy.Entity 클래스를 구현해야한다

## 사용법

```java
@SpringBootApplication
@BusinessEntityScan(basePackages = {"dev.ohhoonim.para", "dev.ohhoonim.lms"})
public class LmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
	}
}
 */

    @Test
    void entityBeanScanTest() {
        var beans = List.of(context.getBeanDefinitionNames());
        assertThat(beans).contains("businessEntityMap");

        var map = (Map<String, Class<? extends Entity>>)context.getBean("businessEntityMap");
        assertThat(map.get("note")).isEqualTo(Note.class);
    }

    @Autowired
    @Qualifier("businessEntityMap")
    Map<String, Class<Entity>> businessEntityMap;

    @Test
    void diBeanTest() {
        var note = businessEntityMap.get("note");
        assertThat(note).isEqualTo(Note.class);
    }
}
