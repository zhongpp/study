package com.baomidou.mybatisplus.samples.deluxe.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 测试自定义 typeHandler
 *
 * @author miemie
 * @since 2018-08-13
 */
public class ListJsonHandler extends BaseTypeHandler<List> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List parameter, JdbcType jdbcType) throws SQLException {
        String value = "";
        if (parameter == null || parameter.size() == 0) {
            value = "[]";
        }
        try {
            value = mapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ps.setString(i, value);
    }

    @Override
    public List getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        if (string == null) {
            return null;
        }
        try {
            return mapper.readValue(string.getBytes("utf8"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String string = rs.getString(columnIndex);
        if (string == null) {
            return null;
        }
        try {
            return mapper.readValue(string.getBytes("utf8"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String string = cs.getString(columnIndex);
        if (string == null) {
            return null;
        }
        try {
            return mapper.readValue(string.getBytes("utf8"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
