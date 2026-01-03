package dev.ohhoonim.user.application;

import dev.ohhoonim.component.auditing.model.MasterCode;

public enum UserLockStatus implements MasterCode {
    Lock("Y", "user.lock"),
    Unlock("N", "user.unlock");

    private final String masterCode;
    private final String langCode;

    private UserLockStatus(String masterCode, String langCode) {
        this.masterCode = masterCode;
        this.langCode = langCode;
    }

    @Override
    public String groupCode() {
        return "USER_LOCK_STATUS";
    }

    @Override
    public String masterCode() {
        return masterCode;
    }

    @Override
    public String langCode() {
        return langCode;
    }

    public static UserLockStatus get(boolean status) {
        return status ? Lock : Unlock;
    }
}
