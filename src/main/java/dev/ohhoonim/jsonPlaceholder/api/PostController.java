package dev.ohhoonim.jsonPlaceholder.api;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;
import dev.ohhoonim.jsonPlaceholder.application.SearchReq;
import dev.ohhoonim.jsonPlaceholder.model.Comment;
import dev.ohhoonim.jsonPlaceholder.model.Post;
import dev.ohhoonim.jsonPlaceholder.model.PostService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public Post postDetails(@PathVariable("id") int postId) {
        return postService.postDetails(postId);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> postComments(@PathVariable("id") int postId) {
        return postService.postComments(postId);
    }

    @PutMapping("")
    public Post updatePost(@RequestBody Post post) {
        return postService.updatePost(post);
    }

    @GetMapping("")
    public List<Post> searchFilterPosts(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "body", required = false) String body) {
        var searchReq = new SearchReq(title, body);
        return postService.searchFilterPosts(searchReq);
    }

    @GetMapping("/all")
    public List<Post> allPosts() {
        return postService.allPosts();
    }

    @DeleteExchange("/{id}")
    public void deletePost(@PathVariable("id") int postId) {
        postService.deletePost(postId);
    }

    @PostMapping("")
    public Post createPost(@RequestBody Post newPost) {
        return postService.createPost(newPost);
    }

}
