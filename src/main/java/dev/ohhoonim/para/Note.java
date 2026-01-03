package dev.ohhoonim.para;

import java.util.List;
import java.util.Set;
import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;

public class Note implements Entity{

    private Id noteId;
    private String title;
    private String content;
    private Set<Tag> tags;
    private List<Para> paras;

    public Note() {
    }

    public Note(Id noteId, String title, String content, Set<Tag> tags, List<Para> paras) {
        this.noteId = noteId;
        this.title = (title == null || title.isBlank()) ? "new note" : title;
        this.content = content;
        this.tags = tags;
        this.paras = paras;
    }

    public Note(Id noteId, String title, String content) {
        this(noteId, title, content, null, null);
    }

    public Note(Note note, Set<Tag> tags) {
        this(note.getNoteId(), note.getTitle(), note.getContent(), tags, null);
    }

    public Note(Note note, Set<Tag> tags, List<Para> paras) {
        this(note.getNoteId(), note.getTitle(), note.getContent(), tags, paras);
    }

    public Note(Id noteId, String title) {
        this(noteId, title, null, null, null);
    }

    public Id getNoteId() {
        return noteId;
    }

    public void setNoteId(Id noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<Para> getParas() {
        return paras;
    }

    public void setParas(List<Para> paras) {
        this.paras = paras;
    }

    @Override
    public Id getId() {
        return noteId;
    }
}