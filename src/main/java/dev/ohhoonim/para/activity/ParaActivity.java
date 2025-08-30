package dev.ohhoonim.para.activity;

import java.util.List;
import java.util.Optional;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Para;
import dev.ohhoonim.para.Para.Project;
import dev.ohhoonim.para.Para.Shelf;

public interface ParaActivity {

    public List<Note> notes(Para paraId);

    public List<Note> registNote(Para paraId, Id noteId);

    public List<Note> removeNote(Para paraId, Id noteId);

    public Optional<Para> moveToPara(Para origin, Class<? extends Shelf> targetPara);

    public Optional<Para> getPara(Para para);

    public Optional<Para> addPara(Para para);

    public Optional<Para> modifyPara(Para para);

    public void removePara(Para para);

    public List<Shelf> findShelves(String searchString, Page page);

    public List<Project> findProjects(String searchString, Page page);
}
