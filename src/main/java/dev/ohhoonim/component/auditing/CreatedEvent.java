package dev.ohhoonim.component.auditing;

import dev.ohhoonim.component.auditing.model.Created;
import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

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
        } catch (JacksonException e) {
            throw new RuntimeException("json 변환에 실패하였습니다.");
        }
    }
}
