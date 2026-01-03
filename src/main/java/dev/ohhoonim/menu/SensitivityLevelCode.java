package dev.ohhoonim.menu;

import dev.ohhoonim.component.auditing.model.MasterCode;

public enum SensitivityLevelCode implements MasterCode {

    P1("P1", "sensitivityLevelCode.general"), // public
    P2("P2", "sensitivityLevelCode.internal"), // restricted
    P3("P3", "sensitivityLevelCode.critical"), // high confidential
    P4("P4", "sensitivityLevelCode.executive") // core secret
    ;

    private final String masterCode;
    private final String langCode;

    private SensitivityLevelCode(String masterCode, String langCode) {
        this.masterCode = masterCode;
        this.langCode = langCode;
    }

    @Override
    public String groupCode() {
        return "SENSITIVITY_LEVEL_CODE";
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
