package dev.ohhoonim.lms.domain.post.api;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.ohhoonim.lms.domain.post.Comment;
import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.post.PostUsecase;
import dev.ohhoonim.lms.domain.post.infra.PostCommandPort;
import dev.ohhoonim.lms.domain.post.infra.PostQueryPort;
import dev.ohhoonim.lms.domain.utils.Condition;

@Service
public class PostAgent implements PostUsecase {
    
    private final PostQueryPort postQueryPort;
    private final PostCommandPort postCommandPort;

    public PostAgent(PostQueryPort postQueryPort, PostCommandPort postCommandPort) {
        this.postQueryPort = postQueryPort;
        this.postCommandPort = postCommandPort;
    }
    
    @Override
    public List<Post> posts(Condition<Post, Long> postConditions) {
        return postQueryPort.posts(postConditions);
    }

    @Override
    public List<Comment> refreshComments(Long postId) {
        return postQueryPort.refreshComments(postId);
    }

    @Override
    public Post getPost(Long id) {
        var optPost = postQueryPort.getPost(id);
        return optPost.isPresent() ? optPost.get() : null;
    }

    @Override
    public void addPost(Post post) {
        postCommandPort.addPost(post);
    }

    @Override
    public void updatePost(Post post) {
        postCommandPort.updatePost(post);
    }

    @Override
    public void deletePost(Long id) {
        postCommandPort.deletePost(id);
    }
  
}
