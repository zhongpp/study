package com.zpp.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by zhongpp
 * on 2018/3/11.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {

        //33. 声明并初始5个常熟来储存应用的参数。
        final int ROWS = 10000;
        final int NUMBERS = 1000;
        final int SEARCH = 5;
        final int PARTICIPANTS = 5;
        final int LINES_PARTICIPANT = 2000;

        //34. Create a MatrixMock 对象，名为 mock. 它将有 10,000 行，每行1000个元素。现在，你要查找的数字是5。
        MatrixMock mock = new MatrixMock(ROWS, NUMBERS, SEARCH);

        //35. 创建 Results 对象，名为 results。它将有 10,000 元素。
        Results results = new Results(ROWS);

        //36. 创建 Grouper 对象，名为 grouper。
        Grouper grouper = new Grouper(results);

        //37.  创建 CyclicBarrier 对象，名为 barrier。此对象会等待5个线程。当此线程结束后，它会执行前面创建的 Grouper 对象。
        CyclicBarrier barrier = new CyclicBarrier(PARTICIPANTS, grouper);

        //38. 创建5个 Searcher 对象，5个执行他们的线程，并开始这5个线程。
        Searcher searchers[] = new Searcher[PARTICIPANTS];
        for (int i = 0; i < PARTICIPANTS; i++) {
            searchers[i] = new Searcher(i * LINES_PARTICIPANT, (i * LINES_PARTICIPANT) + LINES_PARTICIPANT, mock, results, 5, barrier);
            Thread thread = new Thread(searchers[i]);
            thread.start();
        }
        System.out.printf("Main: The main thread has finished.\n");
    }
}

//1.  我们从实现2个辅助类开始。首先，创建一个类名为 MatrixMock。此类随机生成一个在1-10之间的 数字矩阵，我们将从中查找数字。
class MatrixMock {

    //2.   声明私有 int matrix，名为 data。
    private int data[][];

    //3.   实现类的构造函数。此构造函数将接收矩阵的行数，行的长度，和我们将要查找的数字作为参数。3个参数全部int 类型。
    public MatrixMock(int size, int length, int number) {

        //4.   初始化构造函数将使用的变量和对象。
        int counter = 0;
        data = new int[size][length];
        Random random = new Random();

        //5.   用随机数字填充矩阵。每生成一个数字就与要查找的数字对比，如果相等，就增加counter值。
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < length; j++) {
                data[i][j] = random.nextInt(10);
                if (data[i][j] == number) {
                    counter++;
                }
            }
        }

        //6.   最后，在操控台打印一条信息，表示查找的数字在生成的矩阵里的出现次数。此信息是用来检查线程们获得的正确结果的。
        System.out.printf("Mock: There are %d ocurrences of number in generated data.\n", counter, number);                   //译者注：把字符串里的number改为%d.
    }

    //7.	实现 getRow() 方法。此方法接收一个 int为参数，是矩阵的行数。返回行数如果存在，否则返回null。
    public int[] getRow(int row) {
        if ((row >= 0) && (row < data.length)) {
            return data[row];
        }
        return null;
    }
}

//8.   现在，实现一个类名为 Results。此类会在array内保存被查找的数字在矩阵的每行里出现的次数。
class Results {

    //9.   声明私有 int array 名为 data。
    private int data[];

    //10. 实现类的构造函数。此构造函数接收一个表明array元素量的整数作为参数。
    public Results(int size) {
        data = new int[size];
    }

    //11. 实现 setData() 方法。此方法接收array的某个位置和一个值作为参数，然后把array的那个位置设定为那个值。
    public void setData(int position, int value) {
        data[position] = value;
    }

    //12. 实现 getData() 方法。此方法返回结果 array。
    public int[] getData() {
        return data;
    }
}

//13. 现在你有了辅助类，是时候来实现线程了。首先，实现 Searcher 类。这个类会在随机数字的矩阵中的特定的行里查找数字。创建一个类名为Searcher 并一定实现  Runnable 接口.
class Searcher implements Runnable {

    //14. 声明2个私有int属性名为 firstRow 和 lastRow。这2个属性是用来确定将要用的子集的行。
    private int firstRow;
    private int lastRow;

    //15. 声明一个私有 MatrixMock 属性，名为 mock。
    private MatrixMock mock;

    //16. 声明一个私有 Results 属性，名为 results。
    private Results results;

    //17.  声明一个私有 int 属性名为 number，用来储存我们要查找的数字。
    private int number;

    //18. 声明一个 CyclicBarrier 对象，名为 barrier。
    private final CyclicBarrier barrier;

    //19. 实现类的构造函数，并初始化之前声明的全部属性。
    public Searcher(int firstRow, int lastRow, MatrixMock mock, Results results, int number, CyclicBarrier barrier) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.mock = mock;
        this.results = results;
        this.number = number;
        this.barrier = barrier;
    }

    //20. 实现 run() 方法，用来查找数字。它使用内部变量，名为counter，用来储存数字在每行出现的次数。
    @Override
    public void run() {
        int counter;

        //21. 在操控台打印一条信息表明被分配到这个对象的行。
        System.out.printf("%s: Processing lines from %d to %d.\n", Thread.currentThread().getName(), firstRow, lastRow);

        //22. 处理分配给这个线程的全部行。对于每行，记录正在查找的数字出现的次数，并在相对于的 Results 对象中保存此数据。
        for (int i = firstRow; i < lastRow; i++) {
            int row[] = mock.getRow(i);
            counter = 0;
            for (int j = 0; j < row.length; j++) {
                if (row[j] == number) {
                    counter++;
                }
            }

            results.setData(i, counter);
        }

        //23. 打印信息到操控台表明此对象已经结束搜索。
        System.out.printf("%s: Lines processed.\n", Thread.currentThread().getName());

        //24. 调用 CyclicBarrier 对象的 await() 方法 ，由于可能抛出的异常，要加入处理 InterruptedException and BrokenBarrierException 异常的必需代码。
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

//25. 现在，实现一个类来计算数字在这个矩阵里出现的总数。它使用储存了矩阵中每行里数字出现次数的 Results 对象来进行运算。创建一个类，名为 Grouper 并一定实现 Runnable 接口.
class Grouper implements Runnable {

    //26. 声明一个私有 Results 属性，名为 results。
    private Results results;

    //27.  实现类的构造函数，并初始化 Results 属性。
    public Grouper(Results results) {
        this.results = results;
    }

    //28.实现 run() 方法，用来计算结果array里数字出现次数的总和。
    @Override
    public void run() {

        //29. 声明一个 int 变量并写在操控台写一条信息表明开始处理了。
        int finalResult = 0;
        System.out.printf("Grouper: Processing results...\n");

        //30. 使用 results 对象的 getData() 方法来获得每行数字出现的次数。然后，处理array的全部元素，把每个元素的值加给 finalResult 变量。
        int data[] = results.getData();
        for (int number : data) {
            finalResult += number;
        }

        //31. 在操控台打印结果。
        System.out.printf("Grouper: Total result: %d.\n", finalResult);
    }
}
