package dev.ohhoonim.lms.domain.post;

public record Comment (
    Long postId,
    Long id,
    String name,
    String email,
    String body
) {
    public Comment {
        if (postId == null) {
            throw new IllegalArgumentException("postId must not be null");
        }
    }
}
