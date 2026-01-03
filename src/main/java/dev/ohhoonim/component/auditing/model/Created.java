package dev.ohhoonim.component.auditing.model;

import java.time.Instant;

public final class Created implements DataBy {

    private Id id;
    private String creator;
    private Instant created;

    public Created() {
        this.created = Instant.now();
    }

    public Created(String creator) {
        this.creator = creator;
        this.created = Instant.now();
    }

    public Created(String creator, Instant created) {
        this.creator = creator;
        this.created = created;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Id getId() {
        return this.id;
    }
    
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
