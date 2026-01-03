package dev.ohhoonim.para.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Para;
import dev.ohhoonim.para.Para.Project;
import dev.ohhoonim.para.ProjectStatus;
import dev.ohhoonim.para.activity.port.ProjectPort;

@Repository
public class ProjectRepository implements ProjectPort {

    private final JdbcClient jdbcClient;

    public ProjectRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void addProject(Project project, Id newParaId) {
        var sql = """
                insert into para_project  (project_id, title, content, start_date, end_date, status)
                values (:project_id, :title, :content, :start_date, :end_date, :status)
                    """;
        jdbcClient.sql(sql)
                .params(toMap.apply(project, newParaId))
                .update();
    }

    private final BiFunction<Project, Id, Map<String, Object>> toMap = (project, getParaId) -> {
        Map<String, Object> map = new HashMap<>();
        map.put("project_id", getParaId.toString());
        map.put("title", project.getTitle());
        map.put("content", project.getContent());
        map.put("start_date", project.getStartDate());
        map.put("end_date", project.getEndDate());
        map.put("status", project.getStatus() == null ? null : project.getStatus().toString());
        return map;
    };

    @Override
    public Optional<Para> getProject(Id getParaId) {
        var sql = """
                select
                    project_id, title, content, start_date, end_date, status
                from
                    para_project
                where
                    project_id = :projectId
                    """;
        return jdbcClient.sql(sql).param("projectId", getParaId.toString())
                .query(projectMapper).optional();
    }

    private final RowMapper<Para> projectMapper = (rs, idx) -> {
        return new Project(
                Id.valueOf(rs.getString("project_id")),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("start_date") == null ? null : rs.getTimestamp("start_date").toLocalDateTime().toLocalDate(),
                rs.getTimestamp("end_date") == null ? null : rs.getTimestamp("end_date").toLocalDateTime().toLocalDate(),
                rs.getString("status") == null ? null : ProjectStatus.valueOf(rs.getString("status")));
    };

    @Override
    public void modifyProject(Project p) {
        var sql = """
                update
                    para_project
                set
                    title = :title,
                    content = :content,
                    start_date = :start_date,
                    end_date = :end_date,
                    status = :status
                where
                    project_id = :project_id
                    """;
        jdbcClient.sql(sql)
                .params(toMap.apply(p, p.getParaId()))
                .update();
    }

    @Override
    public void removeProject(Project p) {
        var sql = """
                delete from para_project where project_id = :project_id
                    """;
        jdbcClient.sql(sql).param("project_id", p.getParaId().toString()).update();
    }

    @Override
    public void registNote(Id noteId, Project project) {
        var sql = """
                insert into para_project_note (project_id, note_id)
                values (:project_id, :note_id)
                 """;
        jdbcClient.sql(sql)
                .param("project_id", project.getParaId().toString())
                .param("note_id", noteId.toString())
                .update();
    }

    @Override
    public List<Note> notes(Project para) {
        var sql = """
                select
                    n.note_id, n.title
                from para_project_note pn
                join para_note n
                on pn.note_id = n.note_id
                where pn.project_id = :project_id
                """;
        return jdbcClient.sql(sql)
                .param("project_id", para.getParaId().toString())
                .query(noteRowMapper)
                .list();
    }

    private final RowMapper<Note> noteRowMapper = (rs, idx) -> {
        return new Note(
                Id.valueOf(rs.getString("note_id")),
                rs.getString("title"));
    };

    @Override
    public void removeNote(Id noteId, Project p) {
        var sql = """
                delete from para_project_note
                where project_id = :project_id and note_id = :note_id
                """;
        jdbcClient.sql(sql)
                .param("project_id", p.getParaId().toString())
                .param("note_id", noteId.toString())
                .update();
    }

    @Override
    public Set<Para> findProjectInNote(Id noteId) {
        var sql = """
                select
                    p.project_id,
                    p.title
                from para_project_note pn
                join para_project p
                on pn.project_id = p.project_id
                where  pn.note_id = :note_id
                """;
        return jdbcClient.sql(sql)
                .param("note_id", noteId.toString())
                .query(projectInNoteMapper).set();
    }
    private final RowMapper<Para> projectInNoteMapper = (rs, idx) -> {
        return new Project(
                Id.valueOf(rs.getString("project_id")),
                rs.getString("title"),
                null,
                null,
                null,
                null);
    };

    @Override
    public List<Project> findProjects(String searchString, Page page) {
        // TOOD select 절, where 절에 대한 query byilder 제작 필요함. 
        var sql = """
                select project_id, title, content, start_date, end_date, status 
                from para_project
                """;
                // where title like '%:title%'
                // limit :limit
                    // --and project_id < :lastSeenKey
        return jdbcClient.sql(sql)
            .param("title", searchString)
            // .param("lastSeenKey", page.lastSeenKey())
            // .param("limit", page.limit())
            .query(projectSearchMapper).list();
    }

    private final RowMapper<Project> projectSearchMapper = (rs, idx) -> {
        return new Project(
                Id.valueOf(rs.getString("project_id")),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("start_date") == null ? null : rs.getTimestamp("start_date").toLocalDateTime().toLocalDate(),
                rs.getTimestamp("end_date") == null ? null : rs.getTimestamp("end_date").toLocalDateTime().toLocalDate(),
                rs.getString("status") == null ? null : ProjectStatus.valueOf(rs.getString("status")));
    };
}
