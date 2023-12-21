package dev.ohhoonim.lms.infra.post;

import java.util.List;

import org.springframework.stereotype.Component;

import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.post.infra.PostCommandPort;
import dev.ohhoonim.lms.domain.post.infra.PostQueryPort;
import dev.ohhoonim.lms.domain.utils.Condition;

@Component
public class PostAdaptor implements PostQueryPort, PostCommandPort {

    private final PostJdbcRepository postJdbcRepository;

    public PostAdaptor(PostJdbcRepository postJdbcRepository) {
        this.postJdbcRepository = postJdbcRepository;
    }

    @Override
    public List<Post> posts(Condition<Post, Long> condition) {
        return postJdbcRepository.posts(condition);
    }

    @Override
    public Post getPost(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPost'");
    }

    @Override
    public void addPost(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPost'");
    }

    @Override
    public void updatePost(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePost'");
    }

    @Override
    public void deletePost(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePost'");
    }

}
