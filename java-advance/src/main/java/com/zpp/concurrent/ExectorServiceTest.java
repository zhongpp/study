package com.zpp.concurrent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author pingpingZhong
 * Date: 2018/3/14
 * Time: 11:12
 */
public class ExectorServiceTest {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.execute(() -> System.out.println("Asynchronous task"));

        executorService.execute(() -> System.out.println("Asynchronous task"));

        Future future = executorService.submit(() -> System.out.println("Asynchronous task"));

        future.get();  //returns null if the task has finished correctly.


        Future future2 = executorService.submit(
                () -> {
                    System.out.println("Asynchronous Callable");
                    return "Callable Result";
                });

        System.out.println("future.get() = " + future2.get());

        executorService.shutdown();


        ExecutorService executorService2 = Executors.newSingleThreadExecutor();

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(
                () -> {
            return "Task 1";
        });
        callables.add(() -> {
            return "Task 2";
        });
        callables.add(() -> {
            return "Task 3";
        });

        String result = executorService2.invokeAny(callables);

        System.out.println("result = " + result);

        ExecutorService executorService3 = Executors.newSingleThreadExecutor();

        Set<Callable<String>> callables3 = new HashSet<Callable<String>>();

        callables3.add(() -> {
            return "Task 1";
        });
        callables3.add(() -> {
            return "Task 2";
        });
        callables3.add(() -> {
            return "Task 3";
        });

        List<Future<String>> futures = executorService3.invokeAll(callables);

        for (Future<String> future3 : futures) {
            System.out.println("future.get = " + future3.get());
        }
    }
}
