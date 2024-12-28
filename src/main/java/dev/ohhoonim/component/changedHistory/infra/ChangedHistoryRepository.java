package dev.ohhoonim.component.changedHistory.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import dev.ohhoonim.component.changedHistory.ChangedHistory;
import dev.ohhoonim.component.changedHistory.ChangedHistory.Classify;
import dev.ohhoonim.component.changedHistory.ChangedHistory.History;
import dev.ohhoonim.component.changedHistory.ChangedHistory.SearchCondition;
import dev.ohhoonim.component.dataBy.DataBy;


@Repository
public class ChangedHistoryRepository implements ChangedHistoryPort {

    private final JdbcClient client;

    public ChangedHistoryRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public List<History> histories(SearchCondition condition) {
        String sql = """
                    select
                        record_id,
                        classify,
                        reference_id,
                        old_contents,
                        new_contents,
                        created_by,
                        created_date
                    from component_changed_history
                    where classify = :classify and reference_id = :reference_id
                """;

        return client.sql(sql)
                .param("classify", condition.classify().toString())
                .param("reference_id", condition.referenceId().toString())
                .query(historiesMapper).list();
    }

    @Override
    public void recording(History changedHistory) {
        String sql = """
                insert into component_changed_history
                (record_id, classify, reference_id,
                old_contents, new_contents,
                created_by, created_date, last_modified_by, last_modified_date)
                values
                (:recordId, :classify, :referenceId,
                :oldContents, :newContents,
                :createdBy, :createdDate, :lastModifiedBy, :lastModifiedDate)
                """;

        client.sql(sql)
                .params(toParamMap.apply(changedHistory))
                .update();

    }

    private final Function<ChangedHistory.History, Map<String, Object>> toParamMap = (changedHistory) -> {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("recordId", changedHistory.recordId());
        paramMap.put("classify", changedHistory.classify().toString());
        paramMap.put("referenceId", changedHistory.referenceId());
        paramMap.put("oldContents", changedHistory.oldContents());
        paramMap.put("newContents", changedHistory.newContents());
        paramMap.put("createdBy", changedHistory.dataBy().createdBy());
        paramMap.put("createdDate", changedHistory.dataBy().createdDate());
        paramMap.put("lastModifiedBy", changedHistory.dataBy().createdBy());
        paramMap.put("lastModifiedDate", changedHistory.dataBy().createdDate());
        return paramMap;
    };

    private final RowMapper<History> historiesMapper = (rs, rownum) -> {

        var dataBy = new DataBy(
                UUID.fromString(rs.getString("created_by")),
                rs.getTimestamp("created_date").toLocalDateTime(),
                UUID.fromString(rs.getString("created_by")),
                rs.getTimestamp("created_date").toLocalDateTime());

        return new History(
                UUID.fromString(rs.getString("record_id")),
                Classify.valueOf(rs.getString("classify")),
                UUID.fromString(rs.getString("reference_id")),
                rs.getString("old_contents"),
                rs.getString("new_contents"),
                dataBy);
    };
}
