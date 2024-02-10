package dev.ohhoonim.lms.infra.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        return jdbcClient.sql("select id, title as title_w, created_date_time, author from posts")
                .query(PostPartial.class)
                .list();
    }

    public record PostPartial(String author, String titleW, LocalDateTime createdDateTime) {
        public Post toPost() {
            return new Post(null, author, titleW, author, createdDateTime, null);
        }
    }

    public Optional<Post> getPost(Long id) {
        Post post = null;;
        try {
            post = jdbcClient.sql("""
                    select 
                        id,
                        author,
                        title,
                        contents,
                        created_date_time                    
                    from posts 
                    where id = :id
                """)
                .param("id", id)
                .query(PostSingle.class).single().toPost();
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(post);
    }

    public record PostSingle(Long id, String author, String title, String contents, LocalDateTime createdDateTime) {
        public Post toPost() {
            return new Post(id, author, title, contents, createdDateTime, List.of());
        }
    }
}
