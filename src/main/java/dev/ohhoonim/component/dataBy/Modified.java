package dev.ohhoonim.component.dataBy;

import java.time.LocalDateTime;

import dev.ohhoonim.component.id.Id;

public final class Modified implements DataBy {
    private Id id;
    private String modifier;
    private LocalDateTime modified;

    public Modified() {
        this.modified = LocalDateTime.now();
    }

    public Modified(String modifier) {
        this.modifier = modifier;
        this.modified = LocalDateTime.now();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public Id getId() {
        return this.id;
    }

    public void setId(Id id) {
        this.id = id;
    }

}
