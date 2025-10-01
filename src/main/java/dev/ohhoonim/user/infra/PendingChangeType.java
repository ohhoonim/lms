package dev.ohhoonim.user.infra;

import dev.ohhoonim.component.auditing.dataBy.MasterCode;

public enum PendingChangeType implements MasterCode {
    Lock("lock", "pendingChangeType.lock"),
    Activate("activate", "pendingChangeType.activate"),
    Batch("batch", "pendingChangeType.batch");

    private final String masterCode;
    private final String langCode;

    private PendingChangeType(String masterCode, String langcode) {
        this.masterCode = masterCode;
        this.langCode = langcode;
    }

    @Override
    public String groupCode() {
        return "PENDING_CHANGE_TYPE";
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
