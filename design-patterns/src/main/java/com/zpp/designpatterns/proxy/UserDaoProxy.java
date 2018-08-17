package com.zpp.designpatterns.proxy;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 15:38
 */
public class UserDaoProxy implements IUserDao {
    //接收保存目标对象
    private IUserDao target;

    public UserDaoProxy(IUserDao target) {
        this.target = target;
    }

    public void save() {
        System.out.println("开始事务...");
        target.save();//执行目标对象的方法
        System.out.println("提交事务...");
    }
}