package dev.ohhoonim.component.auditing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class EntityTypeInferTest {
    
    @Test
    public void classForNameTest() throws ClassNotFoundException {
        Class clazz = Class.forName("dev.ohhoonim.para.Note");
        
        assertThat(clazz.getSimpleName()).isEqualTo("Note");
    }

    @Test
    public void lookupEntityTypeTest() {
        
    }
}
