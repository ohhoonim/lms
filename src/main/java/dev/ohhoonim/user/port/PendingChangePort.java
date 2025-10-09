package dev.ohhoonim.user.port;

import java.time.LocalDateTime;
import java.util.List;

import dev.ohhoonim.user.model.ChangeDetail;
import dev.ohhoonim.user.model.PendingChange;

public interface PendingChangePort {

    List<PendingChange> pendings(LocalDateTime effectiveDate);

    void addPending(PendingChange pendingChange);

    void addChangeDetail(ChangeDetail changeDetail);

}
