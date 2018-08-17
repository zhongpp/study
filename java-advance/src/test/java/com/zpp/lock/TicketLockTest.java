package com.zpp.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author pingpingZhong
 * Date: 2018/3/1
 * Time: 10:36
 */
public class TicketLockTest {

    static int count = 0;

    public static void main(String[] args) throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(30);

        TicketLock ticketLock = new TicketLock();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (Exception e) {

                }
                ticketLock.lock();
                try {
                    count = count + 1;
                    System.out.println("当前线程：" + Thread.currentThread() + "当前值：" + count);
                } catch (Exception ex) {

                } finally {
                    ticketLock.unlock();
                }
            }).start();
            countDownLatch.countDown();
        }

    }
}
