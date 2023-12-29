package dev.ohhoonim.lms.domain.post;

import java.time.LocalDateTime;
import java.util.List;

public record Post (
    Long id,
    String author,
    String title,
    String contents,
    LocalDateTime createdDateTime,
    List<Comment> comments
){
    public Post(Long id, String author, String title, String contents, LocalDateTime createdDateTime) {
        this(id, author, title, contents, createdDateTime, List.of());
    }
    public Post retrieveComment(List<Comment> comments) {
        return new Post(id, author, title, contents, createdDateTime, comments);
    }
}
