package dev.ohhoonim.lms.domain.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ohhoonim.lms.domain.post.api.PostAgent;
import dev.ohhoonim.lms.domain.post.infra.PostQueryPort;

@ExtendWith(MockitoExtension.class)
public class PostUsecaseTest {

    @InjectMocks
    PostAgent postAgent;

    @Mock
    PostQueryPort postQuery;

    @Test
    void testPostList() {
        when(postQuery.postList()).thenReturn(
                List.of(
                        new Post(null, "title1", "content1", null, null),
                        new Post(null, "title1", "content1", null, null)));
        assertEquals(2, postAgent.postList().size());
    }

    
}


