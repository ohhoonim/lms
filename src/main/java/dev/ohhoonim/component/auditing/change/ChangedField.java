package dev.ohhoonim.component.auditing.change;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ChangedField implements BiFunction<Object, Object, String> {

    Logger log = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper;

    public ChangedField(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String apply(Object oldObject, Object newObject) {
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
            throw new RuntimeException("Failed to serialize changed fields to JSON", e);
        }
    }
}
