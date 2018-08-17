package com.zpp.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pingpingZhong
 *         Date: 2017/8/2
 *         Time: 10:03
 */
@Component
public class UserDao {

    @Autowired
    private SqlSession sqlSession;

    public User selectUserById(String id) {
        return null;
    }
}
