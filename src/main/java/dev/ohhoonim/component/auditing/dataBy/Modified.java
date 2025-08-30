package dev.ohhoonim.component.auditing.dataBy;

import java.time.Instant;

public final class Modified implements DataBy {

    private Id id;
    private String modifier;
    private Instant modified;

    public Modified() {
        this.modified = Instant.now();
    }

    public Modified(String modifier) {
        this.modifier = modifier;
        this.modified = Instant.now();
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Id getId() {
        return this.id;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }
}
