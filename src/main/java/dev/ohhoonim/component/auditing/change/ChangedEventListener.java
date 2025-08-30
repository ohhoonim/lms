package dev.ohhoonim.component.auditing.change;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dev.ohhoonim.component.auditing.dataBy.Entity;

@Component
public class ChangedEventListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ChangedEventRepository<?> repository;

    public ChangedEventListener(ChangedEventRepository<?> changedEventRepository) {
        this.repository= changedEventRepository;
    }
    
    @EventListener
    public void changedEvent(ChangedEvent<? extends Entity> event) {
        switch (event) {
            case CreatedEvent c -> repository.recordingChangedData(c);
            case ModifiedEvent m -> repository.recordingChangedData(m);
            case LookupEvent l -> new RuntimeException("Not supported event");
        }
    }
}
