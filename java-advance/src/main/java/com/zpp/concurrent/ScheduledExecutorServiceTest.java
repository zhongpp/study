package com.zpp.concurrent;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author pingpingZhong
 * Date: 2018/3/14
 * Time: 14:09
 */
public class ScheduledExecutorServiceTest {
    public static void main(String[] args) throws Exception {
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(5);

        /*ScheduledFuture scheduledFuture =
                scheduledExecutorService.schedule(
                        () -> {
                            System.out.println("Executed!");
                            return "Called!";
                        },
                        5,
                        TimeUnit.SECONDS);
        System.out.println("result = " + scheduledFuture.get());*/

        //周期为两个任务的开始时间差
       /* scheduledExecutorService.scheduleAtFixedRate(
                () -> {
                    long time = (long) (Math.random() * 10000);
                    System.out.println("executed! " + time + " start: " + new Date());
                    try {
                        Thread.currentThread().sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("executed! " + time + " end: " + new Date());
                }, 1, 5, TimeUnit.SECONDS);*/

        //周期为前个任务的结束时间和后个任务的开始时间差
        scheduledExecutorService.scheduleWithFixedDelay(
                () -> {
                    long time = (long) (Math.random() * 10000);
                    System.out.println("executed! " + time + " start: " + new Date());
                    try {
                        Thread.currentThread().sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("executed! " + time + " end: " + new Date());
                }, 1, 5, TimeUnit.SECONDS);


        //scheduledExecutorService.shutdown();
    }
}
