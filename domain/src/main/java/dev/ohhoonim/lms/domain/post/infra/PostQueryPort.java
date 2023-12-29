package dev.ohhoonim.lms.domain.post.infra;

import java.util.List;
import java.util.Optional;

import dev.ohhoonim.lms.domain.post.Comment;
import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.utils.Condition;

public interface PostQueryPort {

    List<Post> posts(Condition<Post, Long> condition);

    Optional<Post> getPost(Long id);

    List<Comment> refreshComments(Long postId);
    
}
