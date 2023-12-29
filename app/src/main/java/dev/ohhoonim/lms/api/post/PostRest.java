package dev.ohhoonim.lms.api.post;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.post.PostUsecase;
import dev.ohhoonim.lms.domain.utils.Condition;


@RestController
@RequestMapping("/api/posts")
public class PostRest {
    private final PostUsecase postAgent;

    public PostRest(PostUsecase postUsecase) {
        this.postAgent = postUsecase;
    }
    
    @GetMapping("")
    public ResponseEntity<List<Post>> posts(@RequestBody(required = false) Condition<Post, Long> condition) throws InterruptedException {
        return ResponseEntity.ok().body(postAgent.posts(condition));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok().body(postAgent.getPost(id));
    }

    @PostMapping("")
    public void addPost(@RequestBody Post post) {
        postAgent.addPost(post);
    }

    @PutMapping("/{id}")
    public void updatePost(@RequestBody Post post) {    
        postAgent.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postAgent.deletePost(id);
    }
}
