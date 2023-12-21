package dev.ohhoonim.lms.infra.post;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.utils.Condition;

@Repository
public class PostJdbcClientRepository {
    private final JdbcClient jdbcClient;

    public PostJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<PostPartial> posts(Condition<Post, Long> object) {

        return jdbcClient.sql("select title, created_date_time, author, id from posts")
                .query(PostPartial.class)
                .list();
    }

    public record PostPartial(String author, String title, LocalDateTime createdDateTime) {
        public Post toPost() {
            return new Post(null, author, title, author, createdDateTime);
        }
    }
}
