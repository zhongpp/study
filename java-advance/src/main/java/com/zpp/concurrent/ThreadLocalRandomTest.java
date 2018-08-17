package com.zpp.concurrent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author pingpingZhong
 * Date: 2018/3/15
 * Time: 15:06
 */
public class ThreadLocalRandomTest {
    public static void main(String[] args) {
        Thread threads[] = new Thread[3];
        for (int i = 0; i < 3; i++) {
            TaskLocalRandom task = new TaskLocalRandom();
            threads[i] = new Thread(task);
            threads[i].start();
        }

    }
}


class TaskLocalRandom implements Runnable {

    public TaskLocalRandom() {
        ThreadLocalRandom.current();
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: %d\n", name, ThreadLocalRandom.current().nextInt(10));
        }
    }

}