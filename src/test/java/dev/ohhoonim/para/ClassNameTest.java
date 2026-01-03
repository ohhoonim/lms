package dev.ohhoonim.para;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.para.api.NoteController;

public class ClassNameTest {

    @Test
    public void classNameTest() {
        var note = new Note();
        assertThat(note.getClass().getName()).isEqualTo("dev.ohhoonim.para.Note");
    }

    @Test
    public void simpleNameTest() {
        var note = new Note();
        assertThat(note.getClass().getSimpleName().toLowerCase()).isEqualTo("note");
    }

    @Test
    public void translateCaseTest() {
        var entityType = Id.entityType(NoteController.class);
        assertThat(entityType).isEqualTo("note_controller");
    }
}
