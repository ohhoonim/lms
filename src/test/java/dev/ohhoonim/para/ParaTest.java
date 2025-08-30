package dev.ohhoonim.para;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.para.Para.Project;
import dev.ohhoonim.para.Para.Shelf.Archive;
import dev.ohhoonim.para.Para.Shelf.Area;
import dev.ohhoonim.para.Para.Shelf.Resource;
import dev.ohhoonim.para.activity.port.ProjectPort;
import dev.ohhoonim.para.activity.port.ShelfPort;
import dev.ohhoonim.para.activity.service.ParaService;

@ExtendWith(MockitoExtension.class)
public class ParaTest {

    @InjectMocks
    ParaService paraService;

    @Mock
    ProjectPort projectPort;

    @Mock
    ShelfPort shelfPort;

    @Test
    public void projectParaTest() {
        // project 등록
        var projectId = new Id();
        var project = new Project(
                projectId,
                "Youtube 영상제작",
                "no coontents",
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                ProjectStatus.Backlog);

        when(projectPort.getProject(any())).thenReturn(Optional.of(project));

        var newProject = new Project(
                null,
                "Youtube 영상제작",
                "no coontents",
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                ProjectStatus.Backlog);
        Optional<Para> resultPara = paraService.addPara(newProject);

        assertThat(resultPara.get().getParaId()).isEqualTo(projectId);
        assertThat(resultPara.get()).isInstanceOf(Project.class);
        assertThat(((Project) resultPara.get()).getStatus()).isEqualTo(ProjectStatus.Backlog);
        verify(projectPort, times(1)).addProject(any(), any());

        // note 추가 
        List<Note> notesInProject = List.of(
                new Note(new Id(), null, null),
                new Note(new Id(), null, null),
                new Note(new Id(), null, null));
        when(projectPort.notes(any())).thenReturn(notesInProject);

        var currentPara = Para.of(projectId, Project.class);
        var targetNoteId = new Id();
        var notes = paraService.registNote(currentPara, targetNoteId);

        assertThat(notes.size()).isEqualTo(3);
        verify(projectPort, times(1)).registNote(any(), any());

    }

    @Test
    public void paraOfTest() {
        var paraId = new Id();
        var project = Para.of(paraId, Project.class);

        assertThat(project.getParaId()).isEqualTo(paraId);
        assertThat(project).isInstanceOf(Project.class);

        var area = Para.of(paraId, Area.class);
        assertThat(area).isInstanceOf(Area.class);

        var resource = Para.of(paraId, Resource.class);
        assertThat(resource).isInstanceOf(Resource.class);

        var archive = Para.of(paraId, Archive.class);
        assertThat(archive).isInstanceOf(Archive.class);
    }

    @Test
    public void shelfAreaParaTest() {
        // area 등록
        when(shelfPort.getShelf(any())).thenReturn(Optional.of(Para.of(new Id(), Area.class)));

        var area = new Area(null, "youtube 제작", "pass");
        var para = paraService.addPara(area);

        assertThat(para.get()).isInstanceOf(Area.class);
        verify(shelfPort, times(1)).addShelf(any(), any());

        // note 추가
        List<Note> areaNotes = List.of(
                new Note(new Id(), "1장", null),
                new Note(new Id(), "2장", null));
        when(shelfPort.notes(any())).thenReturn(areaNotes);

        Area areaNote = Para.of(new Id(), Area.class);
        var results = paraService.registNote(areaNote, new Id());
        assertThat(results.size()).isEqualTo(2);
        verify(shelfPort, times(1)).registNote(any(), any());
    }

    @Test
    public void shelfResourceParaTest() {
        // reource 등록, 테스트 간편성을 고려하여 세부 항목은 생략(Para.of)
        when(shelfPort.getShelf(any()))
                .thenReturn(Optional.of(Para.of(new Id(), Resource.class)));

        var resource = new Resource(null, "Javascript", "pass");
        var para = paraService.addPara(resource);

        assertThat(para.get()).isInstanceOf(Resource.class);
        assertThat(para.get().getParaId()).isNotNull();
        verify(shelfPort, times(1)).addShelf(any(), any());

        // note추가 생략
    }

    @Test
    @DisplayName("Area에서 Resource로 이동, Archiving에서도 활용 가능")
    public void moveShelfTest() {
        // shelf 이동
        var resource = Para.of(new Id(), Resource.class);
        when(shelfPort.getShelf(any())).thenReturn(Optional.of(resource));

        var area = Para.of(new Id(), Area.class);
        var result = paraService.moveToPara(area, Resource.class);

        verify(shelfPort, times(1)).moveToPara(any(), any());
        assertThat(result.get()).isInstanceOf(Resource.class);

    }

    @Test
    public void archivingParaTest() {
        // archive 등록 
        var archiveResult = Para.of(new Id(), Archive.class);
        when(shelfPort.getShelf(any())).thenReturn(Optional.of(archiveResult));

        var archive = new Archive(null, "youtube 제작", "pass");
        Optional<Para> para = paraService.addPara(archive);

        assertThat(para.get()).isInstanceOf(Archive.class);
        verify(shelfPort, times(1)).addShelf(any(), any());

        // archiving : moveToShelfTest 참고   
    }
}
