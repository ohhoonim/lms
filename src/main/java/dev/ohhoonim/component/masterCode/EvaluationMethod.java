package dev.ohhoonim.component.masterCode;

public enum EvaluationMethod implements MasterCode {
    MID_TERM("EVM001", "master-code.evaluation-method.mid-term"),
    FINAL("EVM002", "master-code.evaluation-method.final");

    private String masterCode;
    private String langCode;

    private EvaluationMethod(String masterCode, String langcode) {
        this.masterCode = masterCode;
        this.langCode = langcode;
    }

    public String groupCode() {
        return "EVALUATION_METHOD";
    }

    public String langCode() {
        return this.langCode;
    }

    public String masterCode() {
        return this.masterCode;
    }

}
