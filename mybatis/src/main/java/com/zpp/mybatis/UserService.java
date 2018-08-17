package com.zpp.mybatis;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author pingpingZhong
 *         Date: 2017/7/31
 *         Time: 10:30
 */
@Service
public class UserService implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    public int save(User user) throws Exception {
        userMapper.insert(user);
        return 1;
    }

    public User findOne(String uuid) {
        return userMapper.findByUuid(uuid);
    }

    public int update(User user) {
        return userMapper.update(user);
    }

    public int delete(String uuid) {
        return userMapper.delete(uuid);
    }

    public List<User> findByName(String name, RowBounds rowBounds) {
        return userMapper.findByName(name, rowBounds);
    }

    public List<User> findByCreatedAtNotNull() {
        return userMapper.findByCreatedAtNotNull();
    }

    public List<User> findByByNameAndAge(String name, int age, String orderBy) {
        return userMapper.findByNameAndAge(name, age, orderBy);
    }

    public List<User> findByByNameAndSex(String name, String sex, String orderBy) {
        return userMapper.findByNameAndSex(name, sex, orderBy);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setName("zhong");
        user.setAge(20);
        user.setStatus("");
        //int flag = save(user);

        //User user1 = findOne("8af6b4df5d360e2a015d360e34b70011");
        //System.out.println(user1.toString());

        //user1.setAge(30);
        //user1.setDescription("who am I?");
        //update(user1);

        //delete(user1.getUuid());

        /*int offset = 2;
        int limit = 5;
        RowBounds rowBounds = new RowBounds(offset, limit);
        List<User> list = findByName("yang",rowBounds);
        for (User u : list){
            System.out.println(u.getUuid());
        }*/


//        List<User> users = findByCreatedAtNotNull();


        //List<User> users = findByByNameAndAge("zhong", 20, "created_at");


        //List<User> users = findByByNameAndSex("zhong", "WOMAN", "created_at");
    }
}
