package dev.ohhoonim.jsonPlaceholder.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import dev.ohhoonim.jsonPlaceholder.model.Comment;
import dev.ohhoonim.jsonPlaceholder.model.Post;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    private RestTestClient restTestClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        this.restTestClient = RestTestClient.bindToServer()
                .baseUrl("http://localhost:" + port).build();
    }

    @Test
    void postDetails() {
        restTestClient.get().uri("/posts/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectBody(Post.class)
                .value(post -> {
                    assertThat(post.id()).isEqualTo(1);
                });
    }

    // expectBody()를 통해 List나 Map, Page 등 제네릭 타입의 응답을 받을 때는 
    // ParameterizedTypeReference를 반드시 사용해야 합니다.
    @Test
    void postComments() {
        restTestClient.get().uri("/posts/1/comments")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Comment>>() {
                })
                .value(comments -> {
                    assertThat(comments).hasSize(5);
                });
    }

    @Test
    void updatePost() {
        ObjectMapper objectMapper = new ObjectMapper();
        Post reqPost = new Post(1, 1, "test", null);

        restTestClient.put().uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(reqPost))
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody(Post.class)
                .value(post -> {
                    assertThat(post.id()).isEqualTo(1);
                });
    }

    @Test
    void searchFilterPosts() {
        restTestClient.get().uri(uriBuilder -> uriBuilder.path("/posts")
                .queryParam("title", "qui est esse")
                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Post>>() {
                })
                .value(posts -> {
                    assertThat(posts).hasSize(1);
                });
    }

    @Test
    void allPosts() {
        restTestClient.get().uri("/posts")
                .accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Post>>() {
                })
                .value(posts -> {
                    assertThat(posts).hasSize(100);
                });
    }

    @Test
    void deletePost() {
        restTestClient.get().uri("/posts/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();
    }

    @Test
    void createPost() {
        ObjectMapper objectMapper = new ObjectMapper();
        Post newPost = new Post(1, 0, "tommorow", "todos");
        restTestClient.post().uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(newPost))
                .exchange().expectStatus().isOk()
                .expectBody(Post.class)
                .value(post -> {
                    assertThat(post.id()).isNotEqualTo(0);
                });
    }

}
