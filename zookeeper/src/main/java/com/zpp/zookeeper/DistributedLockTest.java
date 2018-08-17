package com.zpp.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pingpingZhong
 * Date: 2018/2/27
 * Time: 11:09
 */
public class DistributedLockTest {

    public static void main(String [] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final int count = 50;
        final CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final DistributedLock node = new DistributedLock("/locks");
            executor.submit(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
//						node.tryLock(); // 无阻塞获取锁
                        node.lock(); // 阻塞获取锁
                        Thread.sleep(100);

                        System.out.println("id: " + node.getId() + " is leader: " + node.isOwner());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                        try {
                            node.unlock();
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
