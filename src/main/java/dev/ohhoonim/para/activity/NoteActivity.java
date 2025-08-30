package dev.ohhoonim.para.activity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Para;
import dev.ohhoonim.para.Tag;

public interface NoteActivity {
    public Vo<List<Note>> findNote(String searchString, Page page);

    public Optional<Note> getNote(Id noteId);

    public Optional<Note> addNote(Note newNote);

    public Optional<Note> modifyNote(Note modifiedNote);

    public void removeNote(Id noteId);

    public Set<Tag> tags(Id noteId);

    public Set<Tag> registTag(Id noteId, Tag tag);

    public Set<Tag> removeTag(Id noteId, Tag tag);

    public Set<Para> paras(Id noteId);

    public void registPara(Id noteId, Para para);

    public void removePara(Id noteId, Para para);
}
