package dev.ohhoonim.lms.api.post;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.post.api.PostAgent;
import dev.ohhoonim.lms.domain.utils.Condition;

@WebMvcTest(PostRest.class)
public class PostRestTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostAgent postAgent;

    @Test
    public void postsTest() throws Exception {
        var condition = new Condition<Post, Long>(null, null);

        when(postAgent.posts(condition))
            .thenReturn(List.of(new Post(1L, "matthew", "title2", "contents korea", LocalDateTime.now()) ));

        mockMvc.perform(get("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(condition)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", equalTo(1)));
    }

    @Test
    public void getPostTest() throws Exception {
        mockMvc.perform(get("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
        verify(postAgent, times(1)).getPost(any());
    }

    @Test
    public void addPostTest() throws Exception {
        var post = new Post(null, "matthew", "title2", "contents korea", LocalDateTime.now());
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
            .andDo(print())
            .andExpect(status().isOk());
        verify(postAgent, times(1)).addPost(any());
    }

    @Test
    public void updatePostTest() throws Exception {
        var post = new Post(1L, "matthew", "title2", "contents korea", LocalDateTime.now());
        mockMvc.perform(put("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
            .andDo(print())
            .andExpect(status().isOk());
        verify(postAgent, times(1)).updatePost(any());
    }

    @Test
    public void deletePostTest() throws Exception {
        mockMvc.perform(delete("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
        verify(postAgent, times(1)).deletePost(any());
    }
}
