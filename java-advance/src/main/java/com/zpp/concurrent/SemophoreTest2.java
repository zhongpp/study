package com.zpp.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pingpingZhong
 * Date: 2018/3/7
 * Time: 9:18
 */
public class SemophoreTest2 {
    public static void main(String[] args) {
        //16. 创建PrintQueue对象名为printQueue。
        PrintQueue2 printQueue2 = new PrintQueue2();
        //17. 创建10个threads。每个线程会执行一个发送文档到print queue的Job对象。
        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new Job2(printQueue2), "Thread" + i);
        }
        //18. 最后，开始这10个线程们。
        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }
}

//1.   创建一个会实现print queue的类名为 PrintQueue。
class PrintQueue2 {
    //1.  如我们之前提到的，你将实现semaphores来修改print queue例子。打开PrintQueue类并声明一个boolean array名为 freePrinters。这个array储存空闲的等待打印任务的和正在打印文档的printers。
    private boolean freePrinters[];

    //2.   接着，声明一个名为lockPrinters的Lock对象。将要使用这个对象来保护freePrinters array的访问。
    private Lock lockPrinters;

    //2.   声明一个对象为Semaphore，称它为semaphore。
    private final Semaphore semaphore;

    //3.   实现类的构造函数并初始能保护print quere的访问的semaphore对象的值。
    public PrintQueue2() {
        semaphore = new Semaphore(3);
        freePrinters = new boolean[3];
        for (int i = 0; i < 3; i++) {
            freePrinters[i] = true;
        }
        lockPrinters = new ReentrantLock();
    }

    //4.   修改printJob()方法。它接收一个称为document的对象最为唯一参数。
    public void printJob(Object document) {
        //5.   首先，调用acquire()方法获得semaphore的访问。由于此方法会抛出 InterruptedException异常，所以必须加入处理它的代码。
        try {
            semaphore.acquire();
            //6.   接着使用私有方法 getPrinter()来获得被安排打印任务的打印机的号码。
            int assignedPrinter = getPrinter();
            //7.	然后， 随机等待一段时间来实现模拟打印文档的行。
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job in Printer%d during %d seconds\n", Thread.currentThread().getName(), assignedPrinter, duration);
            TimeUnit.SECONDS.sleep(duration);
            //8.   最后，调用release() 方法来解放semaphore并标记打印机为空闲，通过在对应的freePrinters array引索内分配真值。
            freePrinters[assignedPrinter] = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }

    //9.  实现 getPrinter() 方法。它是一个私有方法，返回一个int值，并不接收任何参数。
    private int getPrinter() {
        //10. 首先，声明一个int变量来保存printer的引索值。
        int ret = -1;
        //11. 然后， 获得lockPrinters对象 object的访问。
        try {
            lockPrinters.lock();
            //12. 然后，在freePrinters array内找到第一个真值并在一个变量中保存这个引索值。修改值为false，因为等会这个打印机就会被使用。
            for (int i = 0; i < freePrinters.length; i++) {
                if (freePrinters[i]) {
                    ret = i;
                    freePrinters[i] = false;
                    break;
                }
            }
            //13. 最后，解放lockPrinters对象并返回引索对象为真值。
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPrinters.unlock();
        }
        return ret;
    }
}

//8.创建一个名为Job的类并一定实现Runnable 接口。这个类实现把文档传送到打印机的任务。
class Job2 implements Runnable {
    //9.   声明一个对象为PrintQueue，名为printQueue。
    private PrintQueue2 printQueue;

    //10. 实现类的构造函数，初始化这个类里的PrintQueue对象。
    public Job2(PrintQueue2 printQueue) {
        this.printQueue = printQueue;
    }

    //11. 实现方法run()。
    @Override
    public void run() {
        //12. 首先， 此方法写信息到操控台表明任务已经开始执行了。
        System.out.printf("%s: Going to print a job\n", Thread.currentThread().getName());
        //13. 然后，调用PrintQueue 对象的printJob()方法。
        printQueue.printJob(new Object());
        //14. 最后， 此方法写信息到操控台表明它已经结束运行了。
        System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
    }
}

