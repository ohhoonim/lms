package dev.ohhoonim.para.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Tag;

@Testcontainers
@JdbcTest
@Import(NoteRepository.class)
public class NoteRepositoryTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:18.1-alpine"));

    @Autowired
    NoteRepository noteRepository;

    @Test
    public void addNoteTest() {
        var newNote = new Note(null, "yesterday did", "nothging");
        var newNoteId = new Id();
        noteRepository.addNote(newNote, newNoteId);

        var savedNote = noteRepository.getNote(newNoteId);
        assertThat(savedNote.isPresent()).isTrue();
        assertThat(savedNote.get().getNoteId()).isEqualTo(newNoteId);
    }

    @Test
    public void modifyNoteTest() {

        Id noteId = new Id();
        var originNote = new Note(null, "origin titile", "contennts");
        noteRepository.addNote(originNote, noteId);

        var modifiedNote = new Note(noteId, "modified title", "contrents");
        noteRepository.modifyNote(modifiedNote);

        var currentNote = noteRepository.getNote(noteId);
        assertThat(currentNote.get().getTitle()).isEqualTo("modified title");
    }

    @Test
    public void removeNoteTest() {
        var noteId = new Id();

        noteRepository.addNote(new Note(null, "titile", "contents"), noteId);
        noteRepository.removeNote(noteId);
        var note = noteRepository.getNote(noteId);

        assertThat(note.isEmpty()).isTrue();
    }

    @Test
    public void tagTest() {
        var tagJava = new Tag(new Id(), "java");
        noteRepository.addTag(tagJava);

        var tagJavascript = new Tag(new Id(), "javascript");
        noteRepository.addTag(tagJavascript);

        var tag3 = new Id();
        var tagSpring = new Tag(tag3, "spring");
        var addedTagSpring = noteRepository.addTag(tagSpring);

        assertThat(addedTagSpring.getTag()).isEqualTo("spring");
        assertThat(addedTagSpring.getTagId()).isEqualTo(tag3);

    }

    @Test
    public void addTagInNoteTest() {
        // 노트가 존재하지 않거나 등록된 태그가 아니면 에러
        assertThrows(Exception.class, () -> {
            noteRepository.addTagInNote(new Id(), new Tag(new Id(), "spring"));
        });
    }

    @Test
    public void addTagInNoteTest2() {
        // 노트는 존재하나 등록되지 않은 태그는 태그를 등록 후 노트에 태그 추가
        var noteId = new Id();
        noteRepository.addNote(new Note(null, "react basic", "nono"), noteId);
        var newedNote = noteRepository.getNote(noteId);
        assertThat(newedNote.get().getNoteId()).isEqualTo(noteId);

        noteRepository.addTagInNote(noteId, new Tag(null, "react"));

        var tagsInNote = noteRepository.tagsInNote(noteId);
        assertThat(tagsInNote.size()).isEqualTo(1);
        assertThat(tagsInNote.stream().findFirst().get().getTag()).isEqualTo("react");
    }

}
