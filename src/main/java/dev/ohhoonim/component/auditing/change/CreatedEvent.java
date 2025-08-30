package dev.ohhoonim.component.auditing.change;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.auditing.dataBy.Entity;
import dev.ohhoonim.component.auditing.dataBy.Id;

public final class CreatedEvent<T extends Entity> implements ChangedEvent<T> {

    private T entityInstance;
    private Created creator;

    public CreatedEvent(T entity, Created creator) {
        this.entityInstance = entity;
        this.creator = creator;
    }

    public Id getId() {
        return new Id();
    }

    public String getEntityType() {
        return Id.entityType(entityInstance.getClass());
    }

    public String getEntityId() {
        return entityInstance.getId() == null ? null : entityInstance.getId().toString();
    }

    public String getEventType() {
        return EventType.CREATED.toString();
    }

    public Created getCreator() {
        return creator;
    }

    public String getJsonData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(entityInstance);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json 변환에 실패하였습니다.");
        }
    }
}
