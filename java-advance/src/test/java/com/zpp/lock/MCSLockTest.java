package com.zpp.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author pingpingZhong
 * Date: 2018/3/1
 * Time: 15:25
 */
public class MCSLockTest {
    static int count = 0;

    public static void main(String[] args) throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(10);

        MCSLock lock = new MCSLock();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (Exception e) {

                }
                lock.lock();
                try {
                    count = count + 1;
                    System.out.println("当前线程：" + Thread.currentThread() + "当前值：" + count);
                } catch (Exception ex) {

                } finally {
                    lock.unlock();
                }
            }).start();
            countDownLatch.countDown();
        }

    }
}
