package dev.ohhoonim.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import dev.ohhoonim.component.masterCode.LectureMethod;
import dev.ohhoonim.component.masterCode.MasterCode;

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
