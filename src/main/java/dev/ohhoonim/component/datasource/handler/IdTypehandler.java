package dev.ohhoonim.component.datasource.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import dev.ohhoonim.component.id.Id;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class IdTypehandler extends BaseTypeHandler<Id> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Id parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public Id getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String id = rs.getString(columnName);
        return id == null ? new Id() : Id.valueOf(id);
    }

    @Override
    public Id getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String id = rs.getString(columnIndex);
        return id == null ? new Id() : Id.valueOf(id);
    }

    @Override
    public Id getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String id = cs.getString(columnIndex);
        return id == null ? new Id() : Id.valueOf(id);
    }
}
