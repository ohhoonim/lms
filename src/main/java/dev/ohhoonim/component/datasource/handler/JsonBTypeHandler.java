package dev.ohhoonim.component.datasource.handler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.postgresql.util.PGobject;

import com.fasterxml.jackson.databind.ObjectMapper;

@MappedJdbcTypes(JdbcType.OTHER)
public class JsonBTypeHandler<T> extends BaseTypeHandler<T> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> type;

    public JsonBTypeHandler() {

    }

    public JsonBTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        try {
            PGobject jsonObject = new PGobject();
            jsonObject.setType("jsonb");
            jsonObject.setValue(objectMapper.writeValueAsString(parameter));
            ps.setObject(i, jsonObject);
        } catch (IOException e) {
            throw new SQLException("Error serializing object to JSON", e);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            PGobject pgObject = (PGobject) rs.getObject(columnName);
            if (pgObject != null) {
                return objectMapper.readValue(pgObject.getValue(), type);
            }
            return null;
        } catch (IOException e) {
            throw new SQLException("Error deserializing JSON to object", e);
        }
    }

    // Add other getNullableResult methods here (for columnIndex, CallableStatement)
    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            PGobject pgObject = (PGobject) rs.getObject(columnIndex);
            if (pgObject != null) {
                return objectMapper.readValue(pgObject.getValue(), type);
            }
            return null;
        } catch (IOException e) {
            throw new SQLException("Error deserializing JSON to object", e);
        }
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            PGobject pgObject = (PGobject) cs.getObject(columnIndex);
            if (pgObject != null) {
                return objectMapper.readValue(pgObject.getValue(), type);
            }
            return null;
        } catch (IOException e) {
            throw new SQLException("Error deserializing JSON to object", e);
        }
    }

}
