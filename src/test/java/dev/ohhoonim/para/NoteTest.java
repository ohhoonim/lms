package dev.ohhoonim.para;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Para.Project;
import dev.ohhoonim.para.Para.Shelf;
import dev.ohhoonim.para.Para.Shelf.Area;
import dev.ohhoonim.para.Para.Shelf.Resource;
import dev.ohhoonim.para.activity.port.NotePort;
import dev.ohhoonim.para.activity.port.ProjectPort;
import dev.ohhoonim.para.activity.port.ShelfPort;
import dev.ohhoonim.para.activity.port.TagPort;
import dev.ohhoonim.para.activity.service.NoteService;
import dev.ohhoonim.para.activity.service.ParaService;
import dev.ohhoonim.para.activity.service.TagService;

@ExtendWith(MockitoExtension.class)
public class NoteTest {

    @InjectMocks
    NoteService noteService;

    @Mock
    NotePort notePort;
    @Mock
    ProjectPort projectPort;
    @Mock
    ShelfPort shelfPort;
    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @Test
    @DisplayName("노트 만들고 수정하기")
    public void makeNoteTest() {
        var title = "first note";
        var contents = "new contents";
        var notSaveNote = new Note(null, title, contents);

        // 새로운 노트 작성
        var noteId = new Id();
        var newNote = new Note(noteId, title, contents);
        when(notePort.getNote(any())).thenReturn(Optional.of(newNote));

        var savedNote = noteService.addNote(notSaveNote);
        assertThat(savedNote.get().getNoteId()).isEqualTo(noteId);
        verify(notePort, times(1)).addNote(any(), any());

        // 노트를 열어서 제목, 내용 수정
        when(notePort.getNote(any())).thenReturn(savedNote);

        var old = noteService.getNote(noteId);
        var newTitle = "new " + title;
        var newContents = "new " + contents;
        var modifiedNote = new Note(old.get().getNoteId(), newTitle, newContents);

        when(notePort.getNote(any())).thenReturn(Optional.of(modifiedNote));

        var newModifiedNote = noteService.modifyNote(modifiedNote);
        assertThat(newModifiedNote.get().getTitle()).isEqualTo("new first note");
        verify(notePort, times(1)).modifyNote(any());
    }

    @InjectMocks
    TagService tagService;

    @Mock
    TagPort tagPort;

    @Test
    @DisplayName("노트에 tag 추가하기")
    public void addTagTest() {
        // search tag --> tagService
        Set<Tag> searchedTags = Set.of(
                new Tag(new Id(), "java"),
                new Tag(new Id(), "javascript"));
        when(tagPort.findTags(any(), any())).thenReturn(searchedTags);

        Set<Tag> tagList = tagService.findTagsLimit20PerPage("java", null);

        assertThat(tagList.size()).isEqualTo(2);
        verify(tagPort, times(1)).findTags(any(), any());

        // add tag int note
        var searchedTag = new Tag(new Id(), "java");

        var noteId = new Id();

        when(tagPort.tagsInNote(any())).thenReturn(Set.of(new Tag(new Id(), "java")));

        Set<Tag> tags = noteService.registTag(noteId, searchedTag);

        assertThat(tags.size()).isEqualTo(1);

        verify(tagPort, times(1)).addTagInNote(any(), any());
        verify(tagPort, times(1)).tagsInNote(any());
    }

    @InjectMocks
    ParaService paraService;

    @Test
    @DisplayName("노트 para 등록하기 - project 추가")
    public void addProjectTest() {
        // search project --> projecService
        Page page = new Page(null, 10, null);
        String searchString = "";

        List<Project> results = List.of(
                new Project(null, "monthly", "", null, null, null),
                new Project(null, "youtube", "", null, null, null));
        when(projectPort.findProjects(any(), any())).thenReturn(results);

        List<Project> projectList = paraService.findProjects(searchString, page);
        assertThat(projectList.size()).isEqualTo(2);
        verify(projectPort, times(1)).findProjects(any(), any());

        // add project
        var noteId = new Id();
        var project = new Project(new Id(), null, null, null, null, null); // required projectId

        noteService.registPara(noteId, project);
        verify(projectPort, times(1)).registNote(any(), any());

        // 추가하기가 끝나면 목록 재조회
    }

    @Test
    @DisplayName("노트 para 등록하기 - shelf ")
    public void classifyNoteTest() {
        // search shelves
        List<Shelf> mockShelves = List.of(
                new Area(new Id(), "youtube", ""),
                new Resource(new Id(), "java", ""));
        when(shelfPort.findShelves(any(), any())).thenReturn(mockShelves);

        List<Shelf> shelves = paraService.findShelves(null, null);
        assertThat(shelves.size()).isEqualTo(2);
        verify(shelfPort, times(1)).findShelves(any(), any());

        // add shelf
        Resource mockResource = new Resource(new Id(), "java", null);
        noteService.registPara(new Id(), mockResource);

        verify(shelfPort, times(1)).registNote(any(), any());
    }

    @Test
    @DisplayName("노트와 연결된 파라 목록 가져오기 ")
    public void noteInParasTest() {
        var noteId = new Id();

        // when(projectPort.findProjectInNote(any()))
        // .thenReturn(Set.of(new Project(UUID.randomUUID())));
        when(projectPort.findProjectInNote(any())).thenReturn(null);
        when(shelfPort.findShelfInNote(any()))
                .thenReturn(Set.of(new Area(new Id())));

        Set<Para> parasInNote = noteService.paras(noteId);
        assertThat(parasInNote.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("노트와 연결된 파라 제거하기")
    public void noteInParaRemoveTest() {
        var area = new Area(new Id());

        noteService.removePara(new Id(), area);
        verify(shelfPort, times(1))
                .removeNote(any(Id.class), any(Shelf.class));
    }

    @Test
    @DisplayName("노트 검색하기 ")
    public void findNoteTest() {
        var findedNotes = List.of(
                new Note(new Id(), "searchword is goog", null),
                new Note(new Id(), "new note", "this content searchword"));
        when(notePort.findNote(any(), any())).thenReturn(findedNotes);

        var results = noteService.findNote(
                "searchword",
                new Page());
        assertThat(results.record().size()).isEqualTo(2);
    }
}
