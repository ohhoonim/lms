package dev.ohhoonim.component.auditing.infra;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;

import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.container.Page;
import tools.jackson.databind.ObjectMapper;

public class ChangedFieldTest {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void objectMapperTest() throws JsonProcessingException {
        var user = new UserFor("matthew", 13);
        String result = objectMapper.writeValueAsString(user);
        log.info(result);
    }

    @Test
    public void getChangedFieldsAsJsonTest() {
        var user1 = new UserFor("matthew", 16);
        var user2 = new UserFor("matthew", 14);
        var resultJson = getChangedFieldsAsJson.apply(user1, user2);

        log.info(resultJson);

        Integer oldValue = JsonPath.read(resultJson,
                "$.changed_fields.age.old_value");
        assertThat(oldValue).isEqualTo(16);

    }

    @Test
    public void compareType() {
        var user = new UserFor("matthew", 13);
        var authority = new AuthorityFor("ADMIN"); 

        assertThat(user.getClass()).isNotEqualTo(authority.getClass());
    }


    BiFunction<Object, Object, String> getChangedFieldsAsJson = (oldObject, newObject) -> {
        Map<String, Map<String, Object>> changedFields = new HashMap<>();

        Class<?> newObjectClass = newObject.getClass();
        Field[] fields = newObjectClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object newValue = field.get(newObject);
                Object oldValue = BeanUtils.getPropertyDescriptor(
                                    oldObject.getClass(),
                                    field.getName())
                                .getReadMethod()
                                .invoke(oldObject);

                if (newValue != null && !newValue.equals(oldValue)) {
                    changedFields.put(field.getName(), Map.of(
                            "old_value", oldValue,
                            "new_value", newValue));
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }

        if (changedFields.isEmpty()) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(Map.of("changed_fields", changedFields));
        } catch (Exception e) {
            // JSON 변환 오류 처리
            throw new RuntimeException("Failed to serialize changed fields to JSON", e);
        }
    };

    @Test
    public void changedFieldTest() {
        var oldId = new Id();
        var newId = new Id();

        var page1 = new Page(1000, 20, oldId);
        var page2 = new Page(1000, 20, newId);

        ChangedField changedField = new ChangedField(objectMapper);
        var resultJson = changedField.apply(page1, page2);        

        log.info("{}", resultJson);

        String newValue= JsonPath.read(resultJson,
                "$.changed_fields.lastSeenKey.new_value");

        assertThat(Id.valueOf(newValue)).isEqualTo(newId);
    }

}
// record는 동작안함
// record User(String name, int age) {}

class UserFor {
    private String name;
    private int age;

    UserFor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class AuthorityFor {
    private String role;

    public AuthorityFor(String role) {
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}