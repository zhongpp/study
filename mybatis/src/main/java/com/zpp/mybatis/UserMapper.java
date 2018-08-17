package com.zpp.mybatis;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pingpingZhong
 *         Date: 2017/7/31
 *         Time: 10:21
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS")
    List<User> getAllUser();

    //@SelectKey(statement = {"selectUserById"})
    @Select("SELECT * FROM USERS WHERE UUID = #{uuid}")
    @Results({
            @Result(property = "createdAt", column = "created_at", typeHandler = com.zpp.mybatis.typehandler.DateTimeTypeHandler.class),
            @Result(property = "description", column = "description", typeHandler = com.zpp.mybatis.typehandler.BlobTypeHandler.class),
            @Result(property = "tags", column = "tags", typeHandler = com.zpp.mybatis.typehandler.ListJsonTypeHandler.class),
    })
    User findByUuid(@Param("uuid") String uuid);

    @Select("SELECT * FROM USERS WhERE CREATED_AT IS NULL LIMIT 1, 100")
    @Results({
            @Result(property = "createdAt", column = "created_at", typeHandler = com.zpp.mybatis.typehandler.DateTimeTypeHandler.class),
            @Result(property = "description", column = "description", typeHandler = com.zpp.mybatis.typehandler.BlobTypeHandler.class),
            @Result(property = "tags", column = "tags", typeHandler = com.zpp.mybatis.typehandler.ListJsonTypeHandler.class),
    })
    List<User> findByCreatedAtNotNull();

    @Transactional
    @Insert("INSERT INTO USERS(UUID, NAME, AGE, STATUS,CREATED_AT,DESCRIPTION, TAGS) VALUES(#{uuid}, #{name}, #{age},#{status}, #{createdAt,typeHandler=com.zpp.mybatis.typehandler.DateTimeTypeHandler},#{description},#{tags,typeHandler=com.zpp.mybatis.typehandler.ListJsonTypeHandler})")
    int insert(User user);

    @Update("UPDATE USERS SET NAME=#{name},DESCRIPTION=#{description}")
    int update(User user);

    @Delete("DELETE FROM USERS WHERE UUID=#{uuid}")
    int delete(String uuid);

    @Select("SELECT * FROM USERS WHERE NAME=#{name}")
    List<User> findByName(@Param("name") String name, RowBounds rowBounds);

    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetUsersByNameAndAge")
    List<User> findByNameAndAge(@Param("name") String name, @Param("age") int age, @Param("orderByColumn") String orderByColumn);


    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetUsersByNameAndSex")
    @Results({
            @Result(property = "createdAt", column = "created_at", typeHandler = com.zpp.mybatis.typehandler.DateTimeTypeHandler.class),
            @Result(property = "description", column = "description", typeHandler = com.zpp.mybatis.typehandler.BlobTypeHandler.class),
            @Result(property = "tags", column = "tags", typeHandler = com.zpp.mybatis.typehandler.ListJsonTypeHandler.class),
    })
    List<User> findByNameAndSex(@Param("name") String name, @Param("sex") String sex, @Param("orderByColumn") String orderByColumn);

}