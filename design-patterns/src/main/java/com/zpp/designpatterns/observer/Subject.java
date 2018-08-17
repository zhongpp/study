package com.zpp.designpatterns.observer;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 11:49
 */
public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyAllObservers();
}
