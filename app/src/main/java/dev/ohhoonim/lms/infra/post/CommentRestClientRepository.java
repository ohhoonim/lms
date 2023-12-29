package dev.ohhoonim.lms.infra.post;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import dev.ohhoonim.lms.domain.post.Comment;

@HttpExchange
public interface CommentRestClientRepository {

    @GetExchange("/posts/{postId}/comments/")
    public List<Comment> retrieve(@PathVariable Long postId);

}
