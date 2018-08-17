package com.zpp.designpatterns.proxy;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 15:37
 */
public class UserDao implements IUserDao {
    public void save() {
        System.out.println("----已经保存数据!----");
    }
}
