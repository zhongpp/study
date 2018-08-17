package com.zpp.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pingpingZhong
 *         Date: 2017/6/2
 *         Time: 14:33
 */

public interface UserDao extends JpaRepository<User, String> {

    User findByUuid(String uuid);

    User findByName(String name);

    User findByNameAndAge(String name, Integer age);

    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = ?2 WHERE u.uuid = ?1 and u.status = ?3")
    void updateUserStatus(String uuid, String newStatus, String oldStatus);
}
