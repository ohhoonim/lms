package dev.ohhoonim.component.auditing.model;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import dev.ohhoonim.component.auditing.ChangedEvent;
import dev.ohhoonim.component.auditing.CreatedEvent;
import dev.ohhoonim.component.auditing.LookupEvent;
import dev.ohhoonim.component.auditing.ModifiedEvent;
import dev.ohhoonim.component.auditing.SigninEvent;
import dev.ohhoonim.component.auditing.application.ChangeEventActivity;
import dev.ohhoonim.component.auditing.port.ChangedEventPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChangedEventListener implements ChangeEventActivity {

    private final ChangedEventPort<?> repository;

    @Async
    @EventListener
    @Override
    public void changedEvent(ChangedEvent<? extends Entity> event) {
        switch (event) {
            case CreatedEvent c -> repository.recordingChangedData(c);
            case ModifiedEvent m -> repository.recordingChangedData(m);
            case LookupEvent _ -> new RuntimeException("Not supported event");
            case SigninEvent _ -> new RuntimeException("Not supported event");
        }
    }

    @Async
    @EventListener
    @Override
    public void signinEvent(SigninEvent signUser) {
        repository.recordingSignin(signUser);
    }
}
