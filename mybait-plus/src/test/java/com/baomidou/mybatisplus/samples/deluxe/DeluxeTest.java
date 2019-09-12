package com.baomidou.mybatisplus.samples.deluxe;

import java.util.*;

import javax.annotation.Resource;
import javax.sql.DataSource;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    @Qualifier("acDataSource")
    DataSource dataSource;

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
        attribute.put("sex", 19);
        attribute.put("high", 170);
        attribute.put("like", "coder");


        User u = new User().setId(IdUtil.fastSimpleUUID()).setEmail("122@qq.com").setVersion(1).setDeleted(0).setTags(tags).setAttributes(attribute).setCreatedAt(DateUtil.tomorrow());
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
    public void testSelect() {
        System.out.println(mapper.selectById(1L));
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }


    @Test
    public void selectAll() {
        List<User> list = mapper.selectAll(DateUtil.yesterday(), DateUtil.tomorrow());
        for (User actionLog : list) {
            System.out.println(actionLog);
        }
    }

    @Test
    public void createTable() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        Map result = jdbcTemplate.queryForMap("SELECT count(1) FROM information_schema.TABLES WHERE table_name ='u2';");
        System.out.println(result);
        int size = result.get("count(1)") != null ? (Integer) result.get("count(1)") : 0;
        if (size == 0) {
            String sql = "CREATE TABLE u2 (`id` bigint(20) NOT NULL COMMENT '主键ID', `name` varchar(30) DEFAULT NULL COMMENT '姓名', `age` int(11) DEFAULT NULL COMMENT '年龄', `email` varchar(50) DEFAULT NULL COMMENT '邮箱', `tags` json DEFAULT NULL COMMENT '标签', `attributes` json DEFAULT NULL COMMENT '属性', `created_at` date DEFAULT NULL COMMENT '创建时间', `version` int(10) DEFAULT '1' COMMENT '乐观锁版本', `deleted` int(11) DEFAULT '1' COMMENT '逻辑删除字段',PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
            jdbcTemplate.execute(sql);
        }

//        mapper.createTable("u_20190911");
    }


    @Test
    public void testDate() {
        System.out.println(DateUtil.format(DateUtil.offsetDay(DateUtil.date(), 1), "YYYYMMdd"));
    }
}
