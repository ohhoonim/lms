package dev.ohhoonim.lms.domain.post.api;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.post.PostUsecase;
import dev.ohhoonim.lms.domain.post.infra.PostQueryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostAgent implements PostUsecase {
    
    private final PostQueryPort postQuery;
    
    @Override
    public List<Post> postList() {
        return postQuery.postList();
    }
    
}
