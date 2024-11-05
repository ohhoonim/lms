package dev.ohhoonim.lms.component.user;

public sealed interface User {
    public final class Manager implements User {}
    public final class Professor implements User {}
    public final class Assistant implements User {}
}
