package dev.ohhoonim.component.auditing.change;

import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.auditing.dataBy.Id;

public sealed interface ChangedEvent <T> 
        permits CreatedEvent, ModifiedEvent, LookupEvent,
                SigninEvent {
    
    public Id getId();
    public String getEntityType(); // Id.entityType(Class) static 메소드를 사용하면 된다 
    public String getEntityId();
    public String getEventType() ; 
    public Created getCreator();
    public String getJsonData();
}
