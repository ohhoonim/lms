package dev.ohhoonim.component.id;

import java.util.UUID;

public class Id {

    private UUID id;

    public Id() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
