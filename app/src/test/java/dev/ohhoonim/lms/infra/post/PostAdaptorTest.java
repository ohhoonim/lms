package dev.ohhoonim.lms.infra.post;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostAdaptorTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostJdbcRepository postJdbcRepository;

    @Test
    void postJdbcTest() {
        var posts = postJdbcRepository.posts(null);
        assertEquals(13, posts.size());
    }

    @Autowired
    private PostJdbcTemplateRepository postJdbcTemplateRepository;

    @Test
    void postJdbcTemplateTest() {
        var posts = postJdbcTemplateRepository.posts(null);
        assertEquals(13, posts.size());
    }

    @Autowired
    private PostJdbcClientRepository postJdbcClientRepository;

    @Test
    void postJdbcClientTest() {
        var posts = postJdbcClientRepository.posts(null);
        assertEquals(13, posts.size());
        logger.info("{}", posts.stream().findFirst().get().toPost());
    }
}
