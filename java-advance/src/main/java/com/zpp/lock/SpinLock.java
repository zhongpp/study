package com.zpp.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author pingpingZhong
 * Date: 2018/2/28
 * Time: 16:41
 */
public class SpinLock {

    private AtomicReference<Thread> sign = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!sign.compareAndSet(null, current)) {
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        sign.compareAndSet(current, null);
    }
}