package dev.ohhoonim.lms.infra.post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import dev.ohhoonim.lms.domain.post.Post;
import dev.ohhoonim.lms.domain.utils.Condition;

@Repository("postJdbcRepository")
public class PostJdbcRepository {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    public final DataSource datasource;

    public PostJdbcRepository(DataSource datasource) {
        this.datasource = datasource;
    }

    public List<Post> posts(Condition<Post, Long> condition) {
        var posts = new ArrayList<Post>();
        try (Connection conn = datasource.getConnection();
                ResultSet rs = conn.prepareStatement("select * from posts").executeQuery();) {
            while (rs.next()) {
                posts.add(new Post(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("created_date_time").toLocalDateTime()));
            }
        } catch (SQLException e) {
            logger.error("jdbc error", e);
            throw new RuntimeException(e);
        }

        return posts;
    }

}
