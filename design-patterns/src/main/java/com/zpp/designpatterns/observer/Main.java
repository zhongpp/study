package com.zpp.designpatterns.observer;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 11:51
 */
public class Main {
    public static void main(String[] args) {
        HeadCounter headCounter = new HeadCounter();
        //注册订阅者
        headCounter.registerObserver(new JobSeeker("Mike"));
        headCounter.registerObserver(new JobSeeker("Chris"));
        headCounter.registerObserver(new JobSeeker("Jeff"));
        //通知所有订阅者有新的工作机会
        headCounter.addJob("Google Job");
        headCounter.addJob("Yahoo Job");
        headCounter.addJob("facebook Job");
    }
}
