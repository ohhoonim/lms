package dev.ohhoonim.para.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Para;
import dev.ohhoonim.para.Para.Project;
import dev.ohhoonim.para.ProjectStatus;

@Import({ ProjectRepository.class, NoteRepository.class })
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class ProjectRepositoryTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgres = new PostgreSQLContainer(
            DockerImageName.parse("postgres:17.2-alpine"));

    @Autowired
    ProjectRepository projectRepository;

    @Test
    @DisplayName("프로젝트 기본 관리")
    public void basicProjectTest() {
        var newParaId = new Id();
        var newProject = new Para.Project(null,
                "spring for begining",
                "spring course",
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                ProjectStatus.Backlog);

        projectRepository.addProject(newProject, newParaId);

        var addedProject = projectRepository.getProject(newParaId);
        assertThat(addedProject.isPresent()).isTrue();

        var modifiedProject = new Para.Project(newParaId,
                "spring for junior",
                newProject.getContent(),
                newProject.getStartDate(),
                newProject.getEndDate(),
                ProjectStatus.Ready);
        projectRepository.modifyProject(modifiedProject);
        var modifiedResult = projectRepository.getProject(newParaId);
        assertThat(modifiedResult.isPresent()).isTrue();
        assertThat(modifiedResult.get()).isInstanceOf(Project.class);
        assertThat(((Project) modifiedResult.get()).getStatus()).isEqualTo(ProjectStatus.Ready);

        projectRepository.removeProject(new Project(newParaId));
        var removedProject = projectRepository.getProject(newParaId);
        assertThat(removedProject.isEmpty()).isTrue();
    }

    @Autowired
    NoteRepository noteRepository;

    @Test
    public void registNoteTest() {
        assertThrows(Exception.class, () -> {
            projectRepository.registNote(new Id(), new Project(new Id()));
        });
    }

    @Test
    @DisplayName("프로젝트에 속한 노트 관리")
    public void noteInProjectTest() {
        var noteId = new Id();
        var note = new Note(null, "spring data core", "cores");
        noteRepository.addNote(note, noteId);

        var newParaId = new Id();
        var project = new Project(null, "Spring Data", null, null, null, null);
        projectRepository.addProject(project, newParaId);

        projectRepository.registNote(noteId, new Project(newParaId));
        var notesInProject = projectRepository.notes(new Project(newParaId));

        assertThat(notesInProject.size()).isEqualTo(1);

        // note 기준으로 project 목록 가져오기
        var projects = projectRepository.findProjectInNote(noteId);
        assertThat(projects.size()).isEqualTo(1);

        projectRepository.removeNote(noteId, new Project(newParaId));
        var emptyNotes = projectRepository.notes(new Project(newParaId));

        assertThat(emptyNotes.size()).isEqualTo(0);
    }

    @Test
    public void searchProjectsTest() {
        var paraId1 = new Id();
        var paraId2 = new Id();

        var project = new Project(null, "Spring Data", null, null, null, null);
        projectRepository.addProject(project, paraId1);
        var project2 = new Project(null, "Spring Data", null, null, null, null);
        projectRepository.addProject(project2, paraId2);

        var results = projectRepository.findProjects("Dat", new Page());
        assertThat(results.size()).isEqualTo(2);

    }
}
