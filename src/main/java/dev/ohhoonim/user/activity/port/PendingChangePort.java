package dev.ohhoonim.user.activity.port;

import java.time.LocalDateTime;
import java.util.List;

import dev.ohhoonim.user.ChangeDetail;
import dev.ohhoonim.user.PendingChange;

public interface PendingChangePort {

    List<PendingChange> pendings(LocalDateTime effectiveDate);

    void addPending(PendingChange pendingChange);

    void addChangeDetail(ChangeDetail changeDetail);

}
