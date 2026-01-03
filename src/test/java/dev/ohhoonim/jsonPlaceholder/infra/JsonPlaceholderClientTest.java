package dev.ohhoonim.jsonPlaceholder.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import dev.ohhoonim.jsonPlaceholder.application.SearchReq;
import dev.ohhoonim.jsonPlaceholder.model.Comment;
import dev.ohhoonim.jsonPlaceholder.model.Post;
import dev.ohhoonim.jsonPlaceholder.port.PostClient;

public class JsonPlaceholderClientTest {

    private MockRestServiceServer mockServer;
    private PostClient postClient;

    @BeforeEach
    void setUp() {
        RestClient.Builder sharedBuilder = RestClient.builder();

        this.mockServer = MockRestServiceServer.bindTo(sharedBuilder).build();

        RestClientAdapter adapter = RestClientAdapter.create(sharedBuilder.build());
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        this.postClient = factory.createClient(PostClient.class);
    }

    @Test
    void postDetails() {
        setMockServer("/posts/1", expectedPost);

        Optional<Post> post = postClient.postDetails(1);

        mockServer.verify();
        assertThat(post.get().id()).isEqualTo(1);
        assertThat(post.get().body()).contains("et s");
    }

    @Test
    void postComments() {
        setMockServer("/posts/1/comments", expectedComments);

        List<Comment> post = postClient.postComments(1);

        mockServer.verify();
        assertThat(post).hasSize(2);

    }

    @Test
    void updatePost() {
        setMockServer("/posts/1", expectedPost);
        Post newPost = new Post(1,
                1,
                "sunt aut face",
                "quia et su");
        Post post = postClient.updatePost(newPost.id(), newPost);

        mockServer.verify();
        assertThat(post.id()).isEqualTo(1);
    }

    @Test
    void searchFilterPosts() {
        setMockServer("/posts?title=aut%20face", expectedPosts);
        var searchReq = new SearchReq("aut face", null);
        var posts = postClient.searchFilterPosts(searchReq.toMap());
        mockServer.verify();
        assertThat(posts).hasSize(2);
    }

    @Test
    void deletePost() {
        setMockServer("/posts/1", expectedPost);
        postClient.deletePost(1);
        mockServer.verify();
    }

    @Test
    void createPost() {
        setMockServer("/posts", expectedPost);
        var newPost = new Post(1, 0, "yesterday", "completed");
        var post = postClient.createPost(newPost);

        mockServer.verify();
        assertThat(post).isNotNull();

    }

    private void setMockServer(String url, String expected) {
        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(expected, MediaType.APPLICATION_JSON));
    }

    String expectedPost = """
            {
               "userId": 1,
               "id": 1,
               "title": "sunt aut face",
               "body": "quia et su"
             }
             """;

    String expectedComments = """
            [
                {
                    "postId": 1,
                    "id": 1,
                    "name": "id labore ex et quam laborum",
                    "email": "Eliseo@gardner.biz",
                    "body": "laudantium enim quasi"
                },
                {
                    "postId": 1,
                    "id": 2,
                    "name": "quo vero reiciendis velit similique earum",
                    "email": "Jayne_Kuhic@sydney.com",
                    "body": "est natus enim nihil"
                }
            ]
                            """;

    String expectedPosts = """
            [
                {
                    "userId": 1,
                    "id": 1,
                    "title": "sunt aut facere repell",
                    "body": "quia et sus"
                },
                {
                    "userId": 1,
                    "id": 2,
                    "title": "qui est esse",
                    "body": "est rerum t"
                }
            ]
            """;
}
