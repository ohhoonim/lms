package dev.ohhoonim.user.infra;

import dev.ohhoonim.component.auditing.dataBy.MasterCode;

public enum PendingChangeStatus implements MasterCode {
    Pending("pending", "pendingChangeStatus.pending"),
    Completed("completed", "pendingChangeStatus.completed");

    private final String masterCode;
    private final String langCode;

    private PendingChangeStatus(String masterCode, String langCode) {
        this.masterCode = masterCode;
        this.langCode = langCode;
    }

    @Override
    public String groupCode() {
        return "PENDING_CHANGE_STATUS";
    }

    @Override
    public String masterCode() {
        return masterCode;
    }

    @Override
    public String langCode() {
        return langCode;
    }

}
