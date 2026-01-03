package dev.ohhoonim.jsonPlaceholder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.With;

@With
public record Post(
    int userId,
    int id,
    String title,
    String body,
    @With(AccessLevel.PRIVATE)
    List<Comment> comments
) {
    
    public Post {
        comments = List.copyOf(
                Objects.requireNonNullElse(comments, List.of()));
    }

    public Post(int userId, int id, String title, String body) {
        this(userId, id, title, body, List.of());
    }

    public Post withAddComments(List<Comment> newComments) {
        List<Comment> commentsToAdd = Objects.requireNonNullElse(
            comments, List.of()
        ); 
        if (commentsToAdd.isEmpty()) {
            return this;
        }
        var combindComments = new ArrayList<>(this.comments);
        combindComments.addAll(commentsToAdd);

        return withComments(combindComments);
    }

}
