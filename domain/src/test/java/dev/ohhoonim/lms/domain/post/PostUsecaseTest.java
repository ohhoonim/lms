package dev.ohhoonim.lms.domain.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.ohhoonim.lms.domain.post.api.PostAgent;
import dev.ohhoonim.lms.domain.post.infra.PostCommandPort;
import dev.ohhoonim.lms.domain.post.infra.PostQueryPort;
import dev.ohhoonim.lms.domain.utils.Condition;

@ExtendWith(MockitoExtension.class)
public class PostUsecaseTest {

    @InjectMocks
    PostAgent postAgent;

    @Mock
    PostQueryPort postQuerPort;

    @Mock
    PostCommandPort postCommandPort;

    static Stream<Arguments> providePost() {
        List<Post> posts = Stream.generate(() -> new Post(
                1L, "matthew", "daily", "blar blar", LocalDateTime.now()))
                .limit(137).toList();
        return Stream.of(Arguments.of(posts));  
    }

    @ParameterizedTest
    @MethodSource("providePost")
    void postListTest(List<Post> posts) {
        var condition = new Condition<Post, Long>(null, null);
        when(postQuerPort.posts(null))
            .thenReturn(posts.subList(condition.page().offset(), condition.page().size()));
        assertEquals(10, postAgent.posts(null).size());
    }

    @Test
    void defaultConditionTest() {
        Condition<Post, Long> condition = new Condition<>(null, null);
        assertEquals(1, condition.page().page());
        assertEquals(10, condition.page().size());
        assertEquals(0, condition.page().offset());
    }

    @ParameterizedTest
    @MethodSource("providePost")
    void getPostTest(List<Post> posts) {
        Long id = 1L;
        when(postQuerPort.getPost(id)).thenReturn(posts.stream().filter(post -> id.equals(post.id())).findFirst().get());
        assertEquals("matthew", postAgent.getPost(id).author());
    }

    @Test
    void addPostTest() {
        postAgent.addPost(new Post(1L, "matthew", "daily", "blar blar", LocalDateTime.now()));
        verify(postCommandPort, times(1)).addPost(any());
    }

    @Test
    void updatePostTest() {
        postAgent.updatePost(new Post(1L, "matthew", "daily", "blar blar", LocalDateTime.now()));
        verify(postCommandPort, times(1)).updatePost(any());
    }

    @Test
    void deletePostTest() {
        postAgent.deletePost(1L);
        verify(postCommandPort, times(1)).deletePost(1L);
    }

}
