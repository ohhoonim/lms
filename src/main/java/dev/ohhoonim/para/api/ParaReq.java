package dev.ohhoonim.para.api;

public class ParaReq {
    
    private String noteId;
    private String category;
    private String title;
    private String content;

    public ParaReq() {
    }

    public ParaReq(String noteId, String category) {
        this.noteId = noteId;
        this.category = category;
    }

    public ParaReq(String noteId, String category, String title, String content) {
        this.noteId = noteId;
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public String getNoteId() {
        return noteId;
    }
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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
    
}
