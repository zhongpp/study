package com.zpp.lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * @author pingpingZhong
 * Date: 2018/2/28
 * Time: 14:45
 */

public class TwinsLockTest {

    @Test
    public void test() {
        System.out.println("==============start=============");
        final Lock lock = new TwinsLock();

        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread());
                        Thread.sleep(1000L);
                    } catch (Exception ex) {

                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            Worker w = new Worker();
            w.start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200L);
                    System.out.println();
                } catch (Exception ex) {
                }
            }
        }).start();

        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}