package com.baomidou.mybatisplus.samples.deluxe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.samples.deluxe.entity.User;
import com.baomidou.mybatisplus.samples.deluxe.mapper.UserMapper;
import com.baomidou.mybatisplus.samples.deluxe.model.UserPage;

/**
 * @author miemie
 * @since 2018-08-13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeluxeTest {

    @Resource
    private UserMapper mapper;

    @Test
    public void testPage() {
        System.out.println("------ 自定义 xml 分页 ------");
        UserPage selectPage = new UserPage(1, 5).setSelectInt(20);
        UserPage userPage = mapper.selectUserPage(selectPage);
        Assert.assertSame(userPage, selectPage);
        System.out.println("总条数 ------> " + userPage.getTotal());
        System.out.println("当前页数 ------> " + userPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userPage.getSize());
        print(userPage.getRecords());


        System.out.println("------ baseMapper 自带分页 ------");
        Page<User> page = new Page<>(1, 5);
        IPage<User> userIPage = mapper.selectPage(page, new QueryWrapper<User>().eq("age", 20));
        Assert.assertSame(userIPage, page);
        System.out.println("总条数 ------> " + userIPage.getTotal());
        System.out.println("当前页数 ------> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userIPage.getSize());
        print(userIPage.getRecords());
    }

    @Test
    public void testDelAll() {
        mapper.deleteAll();
    }

    @Test
    public void testInsert() {
        List tags = new ArrayList();
        tags.add("司机");
        tags.add("小年轻");
        tags.add("幽默");


        Map attribute = new HashMap();
        attribute.put("sex",19);
        attribute.put("high",170);
        attribute.put("like","coder");


        User u = new User().setEmail("122@qq.com").setVersion(1).setDeleted(0).setTags(tags).setAttributes(attribute);
        mapper.insert(u);

        u.setAge(18);
        mapper.updateById(u);
        u = mapper.selectById(u.getId());

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("tags,attributes,likes");
        List<Map<String, Object>> list = mapper.selectMaps(wrapper);
        System.out.println(list);

        System.out.println(u);
        Assert.assertEquals("version should be updated", 2, u.getVersion().intValue());
    }

    @Test
    public void testPage2() {
        IPage<Map<String, Object>> page = mapper.selectMapsPage(new Page<>(1, 5), Wrappers.<User>query());
        List<Map<String, Object>> list = page.getRecords();
        list.forEach(map -> {
            System.out.println(map.get("tags"));
        });
    }

    @Test
    public void testSelect() {
        System.out.println(mapper.selectById(1L));
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}
