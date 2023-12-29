package dev.ohhoonim.lms.infra.post;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.utils.Condition;

@Repository("postJdbcTemplateRepository")
public class PostJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> posts(Condition<Post, Long> condition) {
        return jdbcTemplate.query(
                "select * from posts",
                toPostMapper);
    }

    private final RowMapper<Post> toPostMapper = (rs, rowNum) -> new Post(
            rs.getLong("id"),
            rs.getString("author"),
            rs.getString("title"),
            rs.getString("contents"),
            rs.getTimestamp("created_date_time").toLocalDateTime());
}
