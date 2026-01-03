package dev.ohhoonim.component.auditing.port;

import java.util.List;

import dev.ohhoonim.component.auditing.ChangedEvent;
import dev.ohhoonim.component.auditing.LookupEvent;
import dev.ohhoonim.component.auditing.SigninEvent;
import dev.ohhoonim.component.auditing.model.Entity;

public interface ChangedEventPort <T extends Entity> {

    public void recordingChangedData(ChangedEvent<T> event);

    public List<LookupEvent<T>> lookupEvent(LookupEvent<T> lookup);

    public void recordingSignin(SigninEvent signUser);
}
