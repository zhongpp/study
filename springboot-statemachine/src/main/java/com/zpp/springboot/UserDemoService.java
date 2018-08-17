package com.zpp.springboot;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pingpingZhong
 * Date: 2018/1/23
 * Time: 14:28
 */

@Service
public class UserDemoService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = RuntimeException.class)
    public User create() throws Exception {
        User user = new User();
        user.setAge(20);
        user.setName("zhongpp");
        user.setCreatedAt(DateTime.now());
        user.setStatus("UNCONNECTED");
        user.setDescription("大字段乱码测试");

        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag("司机");
        Tag tag2 = new Tag("程序员");
        Tag tag3 = new Tag("颈椎病");
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        user.setTags(tags);

        Map attributes = new HashMap();
        attributes.put("name", "zhongpp");
        attributes.put("age", "28");
        attributes.put("high", "170");
        user.setAttributes(attributes);


        user = userDao.save(user);

        userService.triggerEvent(user.getUuid(), "CONNECT");

        userService.triggerEvent(user.getUuid(), "REGISTER");

        userService.triggerEvent(user.getUuid(), "REGISTER_SUCCESS");

        if (user != null) {
            throw new RuntimeException("出现异常");
        }
        return user;
    }

}
