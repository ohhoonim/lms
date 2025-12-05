package dev.ohhoonim.component.auditing.change;

import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.auditing.dataBy.Entity;
import dev.ohhoonim.component.auditing.dataBy.Id;
import tools.jackson.databind.ObjectMapper;

public final class ModifiedEvent<T extends Entity> implements ChangedEvent<T> {

    private T oldRecord;
    private T newRecord;
    private Created creator;

    public ModifiedEvent(T oldRecord, T newRecord, Created creator) {
        this.oldRecord = oldRecord;
        this.newRecord = newRecord;
        this.creator = creator;
    }

    @Override
    public Id getId() {
        return newRecord.getId();
    }

    @Override
    public String getEntityType() {
        return Id.entityType(newRecord.getClass());
    }

    @Override
    public String getEntityId() {
        return newRecord.getId().toString();
    }

    @Override
    public String getEventType() {
        return EventType.MODIFIED.toString();
    }

    @Override
    public Created getCreator() {
        return creator;
    }

    @Override
    public String getJsonData() {
        ObjectMapper objectMapper = new ObjectMapper();
        var changedField = new ChangedField(objectMapper);
        return changedField.apply(oldRecord, newRecord);
    }

}
