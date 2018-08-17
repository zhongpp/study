package com.zpp.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author pingpingZhong
 * Date: 2018/3/15
 * Time: 11:51
 */
public class BlockingQueueTest {
    public static void main(String[] args) throws Exception {

        BlockingQueue queue = new ArrayBlockingQueue(1024);

        BlockingQueueTestProducer producer = new BlockingQueueTestProducer(queue);
        BlockingQueueTestConsumer consumer = new BlockingQueueTestConsumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);
    }
}

class BlockingQueueTestProducer implements Runnable {

    protected BlockingQueue queue = null;

    public BlockingQueueTestProducer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);

            queue.put("2");
            Thread.sleep(1000);
            queue.put("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class BlockingQueueTestConsumer implements Runnable {

    protected BlockingQueue queue = null;

    public BlockingQueueTestConsumer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}