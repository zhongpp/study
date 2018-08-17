package com.zpp.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author pingpingZhong
 * Date: 2018/2/28
 * Time: 16:47
 */
public class SpinLockTest {


    static int count = 0;

    public static void main(String[] args) throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(3000);

        SpinLock spinLock = new SpinLock();

        for (int i = 0; i < 3000; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (Exception e) {

                }
                spinLock.lock();
                try {
                    count = count + 1;
                    System.out.println("当前线程：" + Thread.currentThread() + "当前值：" + count);
                } catch (Exception ex) {

                } finally {
                    spinLock.unlock();
                }
            }).start();
            countDownLatch.countDown();
        }


    }

}
