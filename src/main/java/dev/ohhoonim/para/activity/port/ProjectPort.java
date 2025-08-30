package dev.ohhoonim.para.activity.port;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Para;
import dev.ohhoonim.para.Para.Project;

public interface ProjectPort {

    List<Project> findProjects(String searchString, Page page);

    void registNote(Id noteId, Project project);

    void addProject(Project project, Id newParaId);

    Optional<Para> getProject(Id paraId);

    List<Note> notes(Project para);

    void removeNote(Id noteId, Project p);

    void removeProject(Project p);

    void modifyProject(Project p);

    Set<Para> findProjectInNote(Id noteId);
    
}
