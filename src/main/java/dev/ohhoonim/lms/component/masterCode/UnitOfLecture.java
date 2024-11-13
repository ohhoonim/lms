package dev.ohhoonim.lms.component.masterCode;

public enum UnitOfLecture implements MasterCode {
    HOUR("EVM001", "master-code.unit-of-lecture.hour"),
    MINUTE("EVM002", "master-code.unit-of-lecture.minute");

    private String masterCode;
    private String langCode;

    private UnitOfLecture(String masterCode, String langcode) {
        this.masterCode = masterCode;
        this.langCode = langcode;
    }

    public String groupCode() {
        return "UNIT_OF_LECTURE";
    }

    public String langCode() {
        return this.langCode;
    }

    public String masterCode() {
        return this.masterCode;
    }

}
