package com.zpp.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * 一种集合点，其中两个线程可以交换对象
 * http://tutorials.jenkov.com/java-util-concurrent/exchanger.html
 *
 * @author pingpingZhong
 * Date: 2018/3/12
 * Time: 16:50
 */
public class ExchangerTest {
    public static void main(String[] args) {

        // 16. 创建2个buffers。分别给producer和consumer使用.
        List<String> buffer1 = new ArrayList<String>();
        List<String> buffer2 = new ArrayList<String>();

        // 17. 创建Exchanger对象，用来同步producer和consumer。
        Exchanger<List<String>> exchanger = new Exchanger<List<String>>();

        // 18. 创建Producer对象和Consumer对象。
        Producer producer = new Producer(buffer1, exchanger);
        Consumer consumer = new Consumer(buffer2, exchanger);

        // 19. 创建线程来执行producer和consumer并开始线程。
        Thread threadProducer = new Thread(producer);
        Thread threadConsumer = new Thread(consumer);
        threadProducer.start();
        threadConsumer.start();
    }
}


//1. 首先，从实现producer开始吧。创建一个类名为Producer并一定实现 Runnable 接口。
class Producer implements Runnable {

    // 2. 声明 List<String>对象，名为 buffer。这是等等要被相互交换的数据类型。
    private List<String> buffer;

    // 3. 声明 Exchanger<List<String>>; 对象，名为exchanger。这个 exchanger 对象是用来同步producer和consumer的。
    private final Exchanger<List<String>> exchanger;

    // 4. 实现类的构造函数，初始化这2个属性。
    public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    // 5. 实现 run() 方法. 在方法内，实现10次交换。
    @Override
    public void run() {
        int cycle = 1;
        for (int i = 0; i < 10; i++) {
            System.out.printf("Producer: Cycle %d\n", cycle);

            // 6. 在每次循环中，加10个字符串到buffer。
            for (int j = 0; j < 10; j++) {
                String message = "Event " + ((i * 10) + j);
                System.out.printf("Producer: %s\n", message);
                buffer.add(message);
            }

            // 7. 调用 exchange() 方法来与consumer交换数据。此方法可能会抛出InterruptedException 异常, 加上处理代码。
            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Producer: " + buffer.size());
            cycle++;
        }
    }
}

class Consumer implements Runnable {

    // 9. 声明名为buffer的 List<String>对象。这个对象类型是用来相互交换的。
    private List<String> buffer;

    // 10. 声明一个名为exchanger的 Exchanger<List<String>> 对象。用来同步 producer和consumer。
    private final Exchanger<List<String>> exchanger;

    // 11. 实现类的构造函数，并初始化2个属性。
    public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    // 12. 实现 run() 方法。在方法内，实现10次交换。
    @Override
    public void run() {
        int cycle = 1;
        for (int i = 0; i < 10; i++) {
            System.out.printf("Consumer: Cycle %d\n", cycle);

            // 13. 在每次循环，首先调用exchange()方法来与producer同步。Consumer需要消耗数据。此方法可能会抛出InterruptedException异常, 加上处理代码。
            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 14. 把producer发来的在buffer里的10字符串写到操控台并从buffer内删除，留空。System.out.println("Consumer: " + buffer.size());
            for (int j = 0; j < 10; j++) {
                String message = buffer.get(0);
                System.out.println("Consumer: " + message);
                buffer.remove(0);
            }
            cycle++;
        }
    }
}