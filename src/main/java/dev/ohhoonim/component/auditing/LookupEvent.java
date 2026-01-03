package dev.ohhoonim.component.auditing;

import dev.ohhoonim.component.auditing.model.Created;
import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;

public final class LookupEvent<T extends Entity> implements ChangedEvent<T> {

    private Id id;
    private String entityType;
    private String entityId;
    private String eventType;
    private Created creator;
    private String jsonData;

    public LookupEvent() {
    }

    public LookupEvent(Id entityId, Class<T> entityType){
        this.entityId = entityId.toString();
        this.entityType = Id.entityType(entityType);
    }

    public LookupEvent(Id id, String entityType, String entityId, String eventType, Created creator, String jsonData) {
        this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.eventType = eventType;
        this.creator = creator;
        this.jsonData = jsonData;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public String getEntityType() {
        return entityType;
    }

    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public Created getCreator() {
        return creator;
    }

    @Override
    public String getJsonData() {
        return jsonData;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setCreator(Created creator) {
        this.creator = creator;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

}
