package com.zpp.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author pingpingZhong
 *         Date: 2017/8/2
 *         Time: 15:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void getAll() {
        PageHelper.startPage(3,20);
        List<User> list = userMapper.getAllUser();
        PageInfo<User> page = new PageInfo<User>(list);
        assertEquals(20, list.size());
        assertEquals(41, page.getStartRow());
        assertEquals(3, page.getPageNum());
        assertEquals(100070, page.getTotal());
    }

    @Test
    public void findByUuid() {
        User user = userMapper.findByUuid("40288aea5db30f0c015db30f14690000");
        Assert.assertEquals("40288aea5db30f0c015db30f14690000", user.getUuid());
    }


    @Test
    public void findByCreatedAtNotNull() throws Exception {
        List<User> users = userMapper.findByCreatedAtNotNull();
        Assert.assertEquals(100, users.size());
    }

    @Test
    public void findByNameAndSex() throws Exception {
        PageHelper.startPage(2,10);
        List<User> users = userMapper.findByNameAndSex("yang", "MAN", "created_at");
        PageInfo<User> page = new PageInfo<User>(users);
        Assert.assertEquals(11, page.getStartRow());
        Assert.assertEquals(13, page.getTotal());
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setName("zhong");
        user.setAge(20);
        user.setStatus("REGISTERED");
        //user.setSex();
        user.setDescription("I am a coder 我是一名程序员");
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag("司机");
        Tag tag2 = new Tag("程序员");
        Tag tag3 = new Tag("颈椎病");
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        user.setTags(tags);
        user.setCreatedAt(DateTime.now());

        Assert.assertEquals(1, userMapper.insert(user));
    }

}
