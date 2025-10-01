package dev.ohhoonim.user;

import java.time.LocalDateTime;
import java.util.List;

import dev.ohhoonim.component.auditing.dataBy.DataBy;
import dev.ohhoonim.component.auditing.dataBy.Entity;
import dev.ohhoonim.component.auditing.dataBy.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingChange implements Entity {

    private Id pendingChangeId;
    private User user;
    private String changeType;
    private LocalDateTime effectiveDate;
    private String status;
    private DataBy creator;
    private DataBy modifier;

    private List<ChangeDetail> changeDetails;

    @Override
    public Id getId() {
        return this.pendingChangeId;
    }
}
