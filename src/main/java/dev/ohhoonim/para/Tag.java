package dev.ohhoonim.para;

import dev.ohhoonim.component.auditing.dataBy.Entity;
import dev.ohhoonim.component.auditing.dataBy.Id;

public class Tag implements Entity{
    private Id tagId;
    private String tag;

    public Tag() {
    }

    public Tag(Id tagId, String tag) {
        this.tagId = tagId;
        this.tag = tag;
    }
    public Id getTagId() {
        return tagId;
    }
    public void setTagId(Id tagId) {
        this.tagId = tagId;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public Id getId() {
        return tagId;
    }

    
}