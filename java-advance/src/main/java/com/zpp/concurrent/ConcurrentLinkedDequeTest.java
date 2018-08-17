package com.zpp.concurrent;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author pingpingZhong
 * Date: 2018/3/15
 * Time: 14:17
 */
public class ConcurrentLinkedDequeTest {

    public static void main(String[] args) {
        ConcurrentLinkedDeque concurrentLinkedDeque = new ConcurrentLinkedDeque();
        Thread[] threadArr = new Thread[100];
        for (int i = 0; i < 100; i++) {
            AddTask addTask = new AddTask(concurrentLinkedDeque);
            threadArr[i] = new Thread(addTask);
            threadArr[i].start();
        }
        System.out.printf("Main: %d AddTask threads have been launched\n", threadArr.length);

        for (int i = 0; i < threadArr.length; i++) {
            try {
                threadArr[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Main: Size of the List: %d\n", concurrentLinkedDeque.size());

        for (int i = 0; i < threadArr.length; i++) {
            PollTask task = new PollTask(concurrentLinkedDeque);
            threadArr[i] = new Thread(task);
            threadArr[i].start();
        }
        System.out.printf("Main: %d PollTask threads have been launched\n", threadArr.length);

        for (int i = 0; i < threadArr.length; i++) {
            try {
                threadArr[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Main: Size of the List: %d\n", concurrentLinkedDeque.size());
    }
}


class AddTask implements Runnable {
    private ConcurrentLinkedDeque concurrentLinkedDeque;

    AddTask(ConcurrentLinkedDeque concurrentLinkedDeque) {
        this.concurrentLinkedDeque = concurrentLinkedDeque;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 10000; i++) {
            concurrentLinkedDeque.add(name + ":Element " + i);
        }
    }
}

class PollTask implements Runnable {
    private ConcurrentLinkedDeque concurrentLinkedDeque;

    PollTask(ConcurrentLinkedDeque concurrentLinkedDeque) {
        this.concurrentLinkedDeque = concurrentLinkedDeque;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5000; i++) {
            concurrentLinkedDeque.pollFirst();
            concurrentLinkedDeque.pollLast();
        }
    }
}
