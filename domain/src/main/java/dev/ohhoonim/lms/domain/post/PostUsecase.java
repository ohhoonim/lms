package dev.ohhoonim.lms.domain.post;

import java.util.List;

import dev.ohhoonim.lms.domain.utils.Condition;

public interface PostUsecase {

    public List<Post> posts(Condition<Post, Long> condition);

    public Post getPost(Long id);

    public List<Comment> refreshComments(Long postId);
    
    public void addPost(Post post);

    public void updatePost(Post post);

    public void deletePost(Long id);


}
