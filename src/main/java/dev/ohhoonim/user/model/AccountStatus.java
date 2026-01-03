package dev.ohhoonim.user.model;

import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatus implements Entity {

    private User signUserId;
    private Boolean enabled;
    private Boolean locked;
    private Integer failedAttemptCount;

    @Override
    public Id getId() {
        return this.signUserId.getId();
    }
}
