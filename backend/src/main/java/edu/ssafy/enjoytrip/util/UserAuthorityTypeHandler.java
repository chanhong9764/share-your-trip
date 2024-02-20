package edu.ssafy.enjoytrip.util;

import edu.ssafy.enjoytrip.dto.user.Role;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthorityTypeHandler  extends BaseTypeHandler<Role> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Role parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public Role getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Role.valueOf(rs.getString(columnName));
    }

    @Override
    public Role getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Role.valueOf(rs.getString(columnIndex));
    }

    @Override
    public Role getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Role.valueOf(cs.getString(columnIndex));
    }
}
