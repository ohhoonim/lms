package dev.ohhoonim.user.model;

import dev.ohhoonim.component.auditing.model.DataBy;
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
public class ChangeDetail implements Entity {

    private Id changeDetailId;
    private PendingChange pendingChange;
    private String attributeName;
    private String oldValue;
    private String newValue;
    private DataBy creator;
    private DataBy modifier;

    @Override
    public Id getId() {
        return changeDetailId;
    }
}
