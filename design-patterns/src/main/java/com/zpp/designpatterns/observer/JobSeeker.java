package com.zpp.designpatterns.observer;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 11:50
 */
public class JobSeeker implements Observer {
    private String name;

    public JobSeeker(String name) {
        this.name = name;
    }

    @Override
    public void update(Subject subject) {
        System.out.println(this.name + " got notified!");
        System.out.println(subject);
    }
}
