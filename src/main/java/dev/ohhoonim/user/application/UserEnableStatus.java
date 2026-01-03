package dev.ohhoonim.user.application;

import dev.ohhoonim.component.auditing.model.MasterCode;

public enum UserEnableStatus implements MasterCode {
    Activate("Y", "user.status_activate"), 
    Deactiviate("N", "user.status_deactivate");
    
    private String masterCode;
    private String langCode;

    private UserEnableStatus(String masterCode, String langCode) {
        this.masterCode = masterCode;
        this.langCode = langCode;
    }

    @Override
    public String groupCode() {
        return "USER_ENABLE_STATUS";
    }

    @Override
    public String masterCode() {
        return masterCode;
    }

    @Override
    public String langCode() {
        return langCode;
    }

    public static UserEnableStatus getStatus(boolean isEnable) {
        return isEnable ? Activate : Deactiviate;
    }

}
