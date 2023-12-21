package dev.ohhoonim.lms.domain.post.infra;

import java.util.List;

import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.utils.Condition;

public interface PostQueryPort {

    List<Post> posts(Condition<Post, Long> condition);

    Post getPost(Long id);
    
}
