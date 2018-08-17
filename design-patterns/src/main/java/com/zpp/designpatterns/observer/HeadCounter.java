package com.zpp.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 11:50
 */
public class HeadCounter implements Subject {

    private List<Observer> observerList;
    private List<String> jobList;

    public HeadCounter() {
        observerList = new ArrayList<Observer>();
        jobList = new ArrayList<String>();
    }

    @Override
    public void registerObserver(final Observer observer) {
        if (!observerList.contains(observer)) {
            observerList.add(observer);
        }
    }

    @Override
    public void removeObserver(final Observer observer) {
        if (observerList.contains(observer)) {
            observerList.remove(observer);
        }
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observerList) {
            observer.update(this);
        }
    }

    public void addJob(final String job) {
        this.jobList.add(job);
        notifyAllObservers();
    }

    public List<String> getJobs() {
        return jobList;
    }

    public String toString() {
        return jobList.toString();
    }
}
