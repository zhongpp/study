package com.zpp.mybatis;

import com.mysql.cj.core.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author pingpingZhong
 *         Date: 2017/8/3
 *         Time: 10:35
 */
public class UserSqlBuilder {

    // If not use @Param, you should be define same arguments with mapper method
    public String buildGetUsersByNameAndSex(
            @Param("name") String name, @Param("orderByColumn") final String orderByColumn) {
        return new SQL() {{
            SELECT("*");
            FROM("users");
            if (!StringUtils.isNullOrEmpty(name)) {
                WHERE("name like concat(#{name},'%')");
            }
            WHERE("sex = #{sex}");
            ORDER_BY(orderByColumn);
        }}.toString();
    }

    // If use @Param, you can define only arguments to be used
    public String buildGetUsersByNameAndAge(@Param("name") String name, @Param("orderByColumn") final String orderByColumn) {
        return new SQL() {{
            SELECT("*");
            FROM("users");
            if (!StringUtils.isNullOrEmpty(name)) {
                WHERE("name like concat(#{name},'%')");
            }
            WHERE("age = #{age}");
            ORDER_BY(orderByColumn);
        }}.toString();
    }

    //也可以写join语句
}
