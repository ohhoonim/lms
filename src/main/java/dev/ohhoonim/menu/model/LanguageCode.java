package dev.ohhoonim.menu.model;

import dev.ohhoonim.component.auditing.model.MasterCode;

public enum LanguageCode implements MasterCode {
    KO("ko-KR", "languageCode.ko-KR"),
    EN("en-US", "languageCode.en-US")
    ;

    private final String masterCode;
    private final String langCode;

    private LanguageCode(String masterCode, String langCode) {
        this.masterCode = masterCode;
        this.langCode = langCode;
    }

    @Override
    public String groupCode() {
        return "LANGUAGE_CODE";
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
