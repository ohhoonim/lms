package dev.ohhoonim.lms.infra.post;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import dev.ohhoonim.lms.domain.post.Comment;
import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.post.infra.PostCommandPort;
import dev.ohhoonim.lms.domain.post.infra.PostQueryPort;
import dev.ohhoonim.lms.domain.utils.Condition;
import dev.ohhoonim.lms.infra.post.PostJdbcClientRepository.PostPartial;

@Component
public class PostAdaptor implements PostQueryPort, PostCommandPort {

    private final PostJdbcClientRepository postRepository;
    private final CommentRestClientRepository commentRepository;

    public PostAdaptor(PostJdbcClientRepository postRepository,
            CommentRestClientRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Post> posts(Condition<Post, Long> condition) {
        List<PostPartial> postPartials = postRepository.posts(condition);
        return postPartials.stream().map(PostPartial::toPost).toList(); 
    }

    @Override
    public Optional<Post> getPost(Long id) {
        Post post;
        try {
            var content = postRepository.getPost(id);
            var comments = commentRepository.retrieve(id);
            post = content.retrieveComment(comments);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(post);
    }

    @Override
    public List<Comment> refreshComments(Long postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refreshComments'");
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
