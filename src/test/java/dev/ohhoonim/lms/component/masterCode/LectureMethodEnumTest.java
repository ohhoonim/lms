package dev.ohhoonim.lms.component.masterCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class LectureMethodEnumTest {

    @Test
    public void getLangCodeTest() {
        assertEquals("master-code.lecture-method.both", LectureMethod.BOTH.langCode());
    }

    @Test
    public void getLectureMethodEnumTest() {
        LectureMethod enumCode = 
            MasterCode.enumCode(LectureMethod.class, "LME001");
        assertEquals(LectureMethod.ON_LINE, enumCode);
    }
}
