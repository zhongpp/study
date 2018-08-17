package com.zpp.mybatis.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongpp
 * on
 * 2017/8/6.
 */
public class ListJsonTypeHandler implements TypeHandler {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            List list = (ArrayList) parameter;
            try {
                String value = mapper.writeValueAsString(list);
                ps.setString(i, value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            ps.setString(i, "[]");
        }
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (value == null){
            return null;
        }
        List list = null;
        try {
            list = mapper.readValue(value.getBytes("ISO-8859-1"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if (value == null){
            return null;
        }
        List list = null;
        try {
            list = mapper.readValue(value.getBytes("ISO-8859-1"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if (value == null){
            return null;
        }
        List list = null;
        try {
            list = mapper.readValue(value.getBytes("ISO-8859-1"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
