package dev.ohhoonim.lms.infra.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.ohhoonim.lms.domain.post.Post;

@SpringBootTest
public class PostAdaptorTest {

    ///// [ Jdbc ]///////////////////////////////////////////////////
    @Autowired
    private PostJdbcRepository postJdbcRepository;

    @Test
    void postJdbcTest() {
        var posts = postJdbcRepository.posts(null);
        assertEquals(13, posts.size());
    }

    ///// [ Jdbc Template ]///////////////////////////////////////////////////
    @Autowired
    private PostJdbcTemplateRepository postJdbcTemplateRepository;
    
    @Test
    void postJdbcTemplateTest() {
        var posts = postJdbcTemplateRepository.posts(null);
        assertEquals(13, posts.size());
    }
    
    ///// [ Jdbc Client ]///////////////////////////////////////////////////
    @Autowired
    private PostJdbcClientRepository postJdbcClientRepository;
    
    @Test
    void postJdbcClientTest() {
        var posts = postJdbcClientRepository.posts(null);
        assertEquals(13, posts.size());
    }

    @Test
    void postJdbcClientGetPostTest() {
        var post = postJdbcClientRepository.getPost(100l);
        assertTrue(post.isEmpty());
        // assertEquals(1l, post.get().id());
    }
    
    ///// [ Jdbc Client 활용 예 : Port-Adaptor 패턴]/////////////////////////
    
    @Autowired
    private PostAdaptor postAdaptor;

    @Test
    void postsTest() {
        var posts = postAdaptor.posts(null);
        assertEquals(13, posts.size());
    }

    @Test
    void getPostTest() {
        assertTrue(postAdaptor.getPost(1000L).isEmpty());
        assertEquals(1L, postAdaptor.getPost(1L).get().id());
    }

    @Test
    void retrieveCommentsTest() {
        var post = postAdaptor.getPost(1L).get();
        assertEquals(5, post.comments().size());
    }

    // @Test
    // void addPostTest() {
    //     var post = new Post(null,
    //              "matthew",
    //              "new year", 
    //              "thanks", 
    //              null);
    //     postAdaptor.addPost(post);
    //     verify(postJdbcRepository, times(1)).addPost(post);
    // }

    // @Test
    // void updatePostTest() {
    //     var post = new Post(1L,
    //              "matthew",
    //              "new year",
    //              "thanks",
    //              null);
    //     postAdaptor.updatePost(post);
    //     verify(postJdbcRepository, times(1)).updatePost(post);
    // }

    // @Test
    // void deletePostTest() {
    //     var postId = 1L;
    //     postAdaptor.deletePost(postId);
    //     verify(postJdbcRepository, times(1)).deletePost(postId);
    // }
}
