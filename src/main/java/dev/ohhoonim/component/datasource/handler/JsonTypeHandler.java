package dev.ohhoonim.component.datasource.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import tools.jackson.databind.ObjectMapper;

@MappedTypes(Object.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonTypeHandler extends BaseTypeHandler<Object> {
    // 객체를 JSON 문자열로 변환하고, JSON 문자열을 객체로 변환하기 위한 ObjectMapper
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter,
            JdbcType jdbcType) throws SQLException {
        try {
            // 자바 객체를 JSON 문자열로 직렬화하여 DB에 저장
            ps.setString(i, objectMapper.writeValueAsString(parameter));
        } catch (Exception e) {
            throw new SQLException("Error serializing object to JSON", e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String json = rs.getString(columnName);
            // DB에서 가져온 JSON 문자열을 Object로 역직렬화
            return json != null ? objectMapper.readValue(json, Object.class) : null;
        } catch (Exception e) {
            throw new SQLException("Error deserializing JSON to object", e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String json = rs.getString(columnIndex);
            return json != null ? objectMapper.readValue(json, Object.class) : null;
        } catch (Exception e) {
            throw new SQLException("Error deserializing JSON to object", e);
        }
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String json = cs.getString(columnIndex);
            return json != null ? objectMapper.readValue(json, Object.class) : null;
        } catch (Exception e) {
            throw new SQLException("Error deserializing JSON to object", e);
        }
    }
}
