package dev.ohhoonim.component.auditing.change;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import dev.ohhoonim.component.auditing.dataBy.Entity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChangedEventListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ChangedEventRepository<?> repository;

    @Async
    @EventListener
    public void changedEvent(ChangedEvent<? extends Entity> event) {
        switch (event) {
            case CreatedEvent c -> repository.recordingChangedData(c);
            case ModifiedEvent m -> repository.recordingChangedData(m);
            case LookupEvent l -> new RuntimeException("Not supported event");
        }
    }

    @Async
    @EventListener
    public void signinEvent(SigninEvent signUser) {
        repository.recordingSignin(signUser);
    }
}
