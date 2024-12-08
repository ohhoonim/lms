package dev.ohhoonim.component.masterCode;

public enum CompletedStatus implements MasterCode {
    Working("CPL001", "master-code.completed-status.working"),
    Completed("CPL002", "master-code.completed-status.completed");

    private String masterCode;
    private String langCode;

    private CompletedStatus(String masterCode, String langcode) {
        this.masterCode = masterCode;
        this.langCode = langcode;
    }

    public String groupCode() {
        return "COMPLETED_STATUS";
    }

    public String langCode() {
        return this.langCode;
    }

    public String masterCode() {
        return this.masterCode;
    }

}
