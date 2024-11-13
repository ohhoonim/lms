package dev.ohhoonim.lms.component.user;

import java.util.UUID;

public sealed interface User permits Manager, Professor, Assistant {
    public UUID id();

    public String userName();

    public String password();
}
