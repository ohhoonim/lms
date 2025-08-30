package dev.ohhoonim.component.container;

public sealed interface Response extends Container {

    public record Success (
        ResponseCode code,
        Object data
    ) implements Response { }

    public record Fail (
        ResponseCode code,
        String message,
        Object data
    ) implements Response { }
}
