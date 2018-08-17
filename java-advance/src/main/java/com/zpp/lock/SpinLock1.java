package com.zpp.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 可重入的自旋锁
 *
 * @author pingpingZhong
 * Date: 2018/3/2
 * Time: 15:56
 */
public class SpinLock1 {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    private int count = 0;

    public void lock() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            count++;
            return;
        }

        while (!owner.compareAndSet(null, current)) {

        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            if (count != 0) {
                count--;
            } else {
                owner.compareAndSet(current, null);
            }

        }

    }
}
