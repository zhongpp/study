package com.zpp.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pingpingZhong
 * Date: 2018/2/27
 * Time: 11:13
 */
public class DistributedReentrantLockTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final int count = 50;
        final CountDownLatch latch = new CountDownLatch(count);

        final DistributedReentrantLock lock = new DistributedReentrantLock("/locks"); //单个锁
        for (int i = 0; i < count; i++) {
            executor.submit(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                        lock.lock();
                        Thread.sleep(100);

                        System.out.println("id: " + lock.getId() + " is leader: " + lock.isOwner());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                        try {
                            lock.unlock();
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}
