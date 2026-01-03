package dev.ohhoonim.para.infra;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Para.Shelf.Area;
import dev.ohhoonim.para.Para.Shelf.Resource;;

@Testcontainers
@JdbcTest
@Import({ ShelfRepository.class, NoteRepository.class })
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ShelfRepositoryTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgres = new PostgreSQLContainer(
            DockerImageName.parse("postgres:18.1-alpine"));

    @Autowired
    ShelfRepository shelfRepository;

    @Test
    @DisplayName("shelf 관리 - area, resource, archive")
    public void basicShelfTest() {
        var area = new Area(null, "java", "grammar java");
        var paraId = new Id();
        shelfRepository.addShelf(area, paraId);
        var shelf = shelfRepository.getShelf(paraId);
        assertThat(shelf.isPresent()).isTrue();
        assertThat(shelf.get()).isInstanceOf(Area.class);

        var modifiedShelf = new Area(shelf.get().getParaId(), "java 2", "design pattern");
        shelfRepository.modifyShelf(modifiedShelf);

        var modifiedResult = shelfRepository.getShelf(paraId);
        assertThat(modifiedResult.get().getTitle()).isEqualTo("java 2");
        // shelf 이동 
        shelfRepository.moveToPara(modifiedResult.get(), Resource.class);
        var movedResult = shelfRepository.getShelf(paraId);
        assertThat(movedResult.get()).isInstanceOf(Resource.class);

        // shelf 삭제
        shelfRepository.removeShelf((Resource) movedResult.get());
        var deletedResult = shelfRepository.getShelf(paraId);
        assertThat(deletedResult.isPresent()).isFalse();
    }

    @Autowired
    NoteRepository noteRepository;

    @Test
    public void findShelfInNote() {
        var noteId = new Id();
        var newNote = new Note(null, "variable in java", "constants");
        noteRepository.addNote(newNote, noteId);

        var area = new Area(null, "java", "grammar java");
        var areaId = new Id();
        shelfRepository.addShelf(area, areaId);

        var resource = new Resource(null, "javascript", "grammar javascript");
        var resourceId = new Id();
        shelfRepository.addShelf(resource, resourceId);

        shelfRepository.registNote(noteId, new Area(areaId));
        shelfRepository.registNote(noteId, new Resource(resourceId));
        // note내 shelf 목록 조회
        var results = shelfRepository.findShelfInNote(noteId);

        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("shelf내 노트 관리")
    public void notesInShelfTest() {

        var area = new Area(null, "java", "grammar java");
        var areaId = new Id();
        shelfRepository.addShelf(area, areaId);

        var noteId = new Id();
        var newNote = new Note(null, "variable in java", "constants");
        noteRepository.addNote(newNote, noteId);

        var noteId2 = new Id();
        var newNote2 = new Note(null, "method in java", "methods");
        noteRepository.addNote(newNote2, noteId2);

        shelfRepository.registNote(noteId, new Area(areaId));
        shelfRepository.registNote(noteId2, new Area(areaId));

        var notes = shelfRepository.notes(new Area(areaId));
        assertThat(notes.size()).isEqualTo(2);
        assertThat(notes.getFirst().getTitle()).contains("java");

        shelfRepository.removeNote(noteId2, new Area(areaId));

        var removedNotes = shelfRepository.notes(new Area(areaId));
        assertThat(removedNotes.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("shelf 검색")
    public void searchShelfTest() {
        var area = new Area(null, "java", "grammar java");
        var areaId = new Id();
        shelfRepository.addShelf(area, areaId);

        var resource = new Resource(null, "javascript", "grammar javascript");
        var resourceId = new Id();
        shelfRepository.addShelf(resource, resourceId);

        var shelves = shelfRepository.findShelves("javascr", new Page());
        assertThat(shelves.size()).isEqualTo(1);
    }

    
}
