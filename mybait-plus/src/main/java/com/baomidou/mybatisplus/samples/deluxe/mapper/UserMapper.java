package com.baomidou.mybatisplus.samples.deluxe.mapper;

import com.baomidou.mybatisplus.samples.deluxe.config.MyBaseMapper;
import com.baomidou.mybatisplus.samples.deluxe.entity.User;
import com.baomidou.mybatisplus.samples.deluxe.model.UserPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author miemie
 * @since 2018-08-12
 */
public interface UserMapper extends MyBaseMapper<User> {

    /**
     * 自定义分页查询
     *
     * @param userPage 单独 user 模块使用的分页
     * @return 分页数据
     */
    UserPage selectUserPage(UserPage userPage);

    void createTable(@Param("table_name") String tableName);

    //    @Select("select * from user where created_at between #{begin_date} and #{end_date}")
    List<User> selectAll(@Param("begin_date") Date beginDate, @Param("end_date") Date endDate);

}
