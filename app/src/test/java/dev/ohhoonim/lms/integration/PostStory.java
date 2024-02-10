package dev.ohhoonim.lms.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import dev.ohhoonim.lms.domain.post.Post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostStory {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void postSingleStoryTest() {
        var response = restTemplate.getForEntity("/api/posts/100", Post.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        // assertEquals(5, response.getBody().comments().size());
    }
    
}
