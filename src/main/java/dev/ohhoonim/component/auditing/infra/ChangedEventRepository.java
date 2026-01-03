package dev.ohhoonim.component.auditing.infra;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import dev.ohhoonim.component.auditing.ChangedEvent;
import dev.ohhoonim.component.auditing.LookupEvent;
import dev.ohhoonim.component.auditing.SigninEvent;
import dev.ohhoonim.component.auditing.model.Created;
import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.auditing.port.ChangedEventPort;
import dev.ohhoonim.component.sign.model.SignUser;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Repository
public class ChangedEventRepository<T extends Entity> implements ChangedEventPort<T> {

    Logger log = LoggerFactory.getLogger(getClass());

    private final JdbcClient jdbcClient;
    private ObjectMapper objectMapper;

    public ChangedEventRepository(JdbcClient jdbcClient, ObjectMapper objectMapper) {
        this.jdbcClient = jdbcClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void recordingChangedData(ChangedEvent<T> event) {
        if (event == null || event.getEntityId() == null) {
            throw new RuntimeException("entity_id는 필수입니다.");
        }

        var sql = """
                INSERT INTO component_changed_event
                 (id, entity_type, entity_id, creator, created, json_data)
                 VALUES(:id, :entityType, :entityId, :creator, :created, :jsonData);
                 """;
        jdbcClient.sql(sql)
                .params(toParamMap.apply(event))
                .update();

    }

    private Function<String, PGobject> toJsonb = (json) -> {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("jsonb");
        try {
            jsonObject.setValue(objectMapper.writeValueAsString(json));
        } catch (JacksonException | SQLException e) {
            throw new RuntimeException("jsonb로 만드는 중 에러가 발생함");
        }

        return jsonObject;};

    private Function<ChangedEvent<T>, Map<String, Object>> toParamMap = (event) -> {
        var map = new HashMap<String, Object>();
        map.put("id", event.getId().toString());
        map.put("entityType", event.getEntityType());
        map.put("entityId", event.getEntityId());
        map.put("creator", event.getCreator().getCreator());
        map.put("created", Timestamp.from(event.getCreator().getCreated()));
        map.put("jsonData", toJsonb.apply(event.getJsonData()));
        return map;
    };

    @Override
    public List<LookupEvent<T>> lookupEvent(LookupEvent<T> lookup) {
        var sql = """
                SELECT id, entity_type, entity_id, creator, created, json_data
                FROM component_changed_event
                WHERE entity_id = :entityId
                  AND entity_type = :entityType
                """;
        return jdbcClient.sql(sql)
                .param("entityId", lookup.getEntityId())
                .param("entityType", lookup.getEntityType())
                .query(rowMapper).list();

    }

    private RowMapper<LookupEvent<T>> rowMapper = (rs, _) -> {

        Instant created = rs.getTimestamp("created").toInstant();
        Created creator = new Created(rs.getString("creator"), created);

        PGobject pgObject = (PGobject) rs.getObject("json_data");

        return new LookupEvent<T>(
                Id.valueOf((rs.getString("id"))),
                rs.getString("entity_type"),
                rs.getString("entity_id"),
                null,
                creator,
                pgObject.getValue());
    };

    @Override
    public void recordingSignin(SigninEvent signUser) {
        if (signUser == null || signUser.username() == null) {
            throw new RuntimeException("사용자 정보가 존재하지 않습니다");
        }

        var sql = """
                INSERT INTO component_changed_event
                 (id, entity_type, entity_id, creator, created, json_data)
                 VALUES(:id, :entityType, :entityId, :creator, :created, :jsonData);
                 """;
        jdbcClient.sql(sql)
                .params(signToParamMap.apply(signUser))
                .update();
    }

    private Function<ChangedEvent<SignUser>, Map<String, Object>> signToParamMap = (event) -> {
        var map = new HashMap<String, Object>();
        map.put("id", event.getId().toString());
        map.put("entityType", event.getEntityType());
        map.put("entityId", event.getEntityId());
        map.put("creator", event.getCreator().getCreator());
        map.put("created", Timestamp.from(event.getCreator().getCreated()));
        map.put("jsonData", toJsonb.apply(event.getJsonData()));
        return map;
    };
}
