package dev.ohhoonim.lms.component.masterCode;

public enum LectureMethod implements MasterCode {
    ON_LINE("LME001", "master-code.lecture-method.online"),
    OFF_LINE("LME002", "master-code.lecture-method.offline"),
    BOTH("LME003", "master-code.lecture-method.both");

    private String masterCode;
    private String langCode;

    private LectureMethod(String masterCode, String langcode) {
        this.masterCode = masterCode;
        this.langCode = langcode;
    }

    public String groupCode() {
        return "LECTURE_METHOD";
    }

    public String langCode() {
        return this.langCode;
    }

    public String masterCode() {
        return this.masterCode;
    }

}
