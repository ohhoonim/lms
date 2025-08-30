package dev.ohhoonim.component.auditing;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.ohhoonim.component.auditing.dataBy.MasterCode;

public class MasterCodeTest {
    /**
     * master code용 enum 만드는 법은 하단 참고
     *  */ 
    @Test
    @DisplayName("enum 기본 테스트")
    public void getEnumCodeTest() {
        var senior = GradeCode.SENIOR;
        assertThat(senior).isEqualTo(GradeCode.SENIOR);
    }

    @Test
    @DisplayName("DB에 저장된 코드를 enum으로 변환")
    public void getEnumCharactors() {
        var enumCode = MasterCode.enumCode(GradeCode.class, "S");
        assertThat(enumCode).isEqualTo(GradeCode.SENIOR);
    }

    @Test
    @DisplayName("i18n 지원을 위한")
    public void getLangCode() {
        assertThat(GradeCode.JUNIOR.langCode())
                .isEqualTo("code.grade.junior");
    }

}

enum GradeCode implements MasterCode {
    SENIOR("S", "code.grade.senior"),
    JUNIOR("J", "code.grade.junior");

    private String masterCode;
    private String langCode;

    private GradeCode(String masterCode, String langCode){
        this.masterCode = masterCode;
        this.langCode = langCode;
    }

    /**
     * 왠만하면 enum 명과 맞춰주자
     * 'Code' 빼고 
     */
    @Override
    public String groupCode() {
        return "GRADE";
    }

    @Override
    public String masterCode() {
        return this.masterCode;
    }

    @Override
    public String langCode() {
        return this.langCode;
    }

}