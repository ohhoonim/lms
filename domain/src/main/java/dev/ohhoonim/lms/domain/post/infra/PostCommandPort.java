package dev.ohhoonim.lms.domain.post.infra;

import dev.ohhoonim.lms.domain.post.Post;

public interface PostCommandPort {

    void addPost(Post post);

    void updatePost(Post post);

    void deletePost(Long id);
    
}
