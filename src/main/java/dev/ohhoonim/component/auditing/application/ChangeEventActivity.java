package dev.ohhoonim.component.auditing.application;

import dev.ohhoonim.component.auditing.ChangedEvent;
import dev.ohhoonim.component.auditing.SigninEvent;
import dev.ohhoonim.component.auditing.model.Entity;

public interface ChangeEventActivity {
    public void changedEvent(ChangedEvent<? extends Entity> event);

    public void signinEvent(SigninEvent signUser);
}
