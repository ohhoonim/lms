package dev.ohhoonim.jsonPlaceholder.activity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import lombok.AccessLevel;
import lombok.With;

public class RecordWitherPatternTest {
   
    @Test
    void recordListAdd() {
        var posts = new PostTest(1, "matthew");
        var comments = List.of(
            new CommentTest("yesterday", "noting"),
            new CommentTest("today", "working")
        );
        var postContainsComments = posts.withAddComments(comments);

        assertThat(postContainsComments.comments()).hasSize(2);
    }
}

@With
record PostTest(
    int id,
    String name,
    @With(AccessLevel.PRIVATE)
    List<CommentTest> comments
) {
    public PostTest {
        comments = List.copyOf(
            Objects.requireNonNullElse(comments, List.of())
        );
    }
    public PostTest(int id, String name) {
        this(id, name, List.of());
    }

    public PostTest withAddComments(List<CommentTest> newComments) {
        List<CommentTest> commentsToAdd = 
                Objects.requireNonNullElse(newComments, List.of());
        if (commentsToAdd.isEmpty()) {
            return this;
        }
        var combindComments = new ArrayList<>(this.comments);
        combindComments.addAll(commentsToAdd);

        var newPost = withComments(combindComments);
        return newPost;
    }
}

record CommentTest(
    String title,
    String contents 
) {

}