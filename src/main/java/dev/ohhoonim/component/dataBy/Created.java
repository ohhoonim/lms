package dev.ohhoonim.component.dataBy;

import java.time.LocalDateTime;

import dev.ohhoonim.component.id.Id;

public final class Created implements DataBy {

    private Id id;
    private String creator;
    private LocalDateTime created;

    public Created() {
        this.created = LocalDateTime.now();
    }

    public Created(String creator) {
        this.creator = creator;
        this.created = LocalDateTime.now();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Id getId() {
        return this.id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}
