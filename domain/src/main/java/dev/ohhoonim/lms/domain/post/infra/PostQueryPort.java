package dev.ohhoonim.lms.domain.post.infra;

import java.util.List;

import dev.ohhoonim.lms.domain.post.Post;

public interface PostQueryPort {

    List<Post> postList();
    
}
