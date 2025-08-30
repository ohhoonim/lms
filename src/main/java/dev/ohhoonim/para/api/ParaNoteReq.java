package dev.ohhoonim.para.api;

public class ParaNoteReq {

    private String category;
    private String noteId;
    private String targetCategory;

    public ParaNoteReq() {

    }

    public ParaNoteReq(String category, String noteId) {
        this(category, noteId, null);
    }

    public ParaNoteReq(String category, String noteId, String targetCategory) {
        this.category = category;
        this.noteId = noteId;
        this.targetCategory = targetCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

}
