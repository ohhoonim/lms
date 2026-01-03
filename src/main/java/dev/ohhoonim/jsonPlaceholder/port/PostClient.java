package dev.ohhoonim.jsonPlaceholder.port;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import dev.ohhoonim.jsonPlaceholder.model.Comment;
import dev.ohhoonim.jsonPlaceholder.model.Post;

@HttpExchange
public interface PostClient {

    @GetExchange("/posts/{id}")
    Optional<Post> postDetails(@PathVariable("id") int postId);

    @GetExchange("/posts/{id}/comments")
    List<Comment> postComments(@PathVariable("id") int postId);

    @PutExchange("/posts/{id}")
    Post updatePost(@PathVariable("id") int id, @RequestBody Post post);

    @GetExchange("/posts")
    List<Post> searchFilterPosts(@RequestParam Map<String, Object> searchReq);

    @DeleteExchange("/posts/{id}")
    void deletePost(@PathVariable("id") int postId);

    @PostExchange("/posts")
    Post createPost(@RequestBody Post newPost);
    


}
