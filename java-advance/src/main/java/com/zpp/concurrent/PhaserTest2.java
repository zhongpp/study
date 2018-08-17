package com.zpp.concurrent;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author pingpingZhong
 * Date: 2018/3/12
 * Time: 16:39
 */
public class PhaserTest2 {
    public static void main(String[] args) {

        // 18. 创建 MyPhaser对象。
        MyPhaser phaser = new MyPhaser();

        // 19. 创建5个 Student 对象并使用register()方法在phaser中注册他们。
        MyPhaser.Student students[] = new MyPhaser.Student[5];
        for (int i = 0; i < students.length; i++) {
            students[i] = phaser.new Student(phaser);
            phaser.register();
        }

        // 20. 创建5个线程来运行students并开始它们。
        Thread threads[] = new Thread[students.length];
        for (int i = 0; i < students.length; i++) {
            threads[i] = new Thread(students[i], "Student " + i);
            threads[i].start();
        }

        // 21. 等待5个线程的终结。
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 22. 调用isTerminated()方法来写一条信息表明phaser是在termination状态。
        System.out.printf("Main: The phaser has finished: %s.\n",
                phaser.isTerminated());
    }
}


//1.   创建一个类，名为 MyPhaser，并特别的扩展 Phaser 类。
class MyPhaser extends Phaser {

    // 2. 覆盖 onAdvance() 方法。根据 phase 的属性的值，我们将调用不同的辅助方法。如果 phase 等于 0，调用
    // studentsArrived() 方法；又如果 phase 等于 1，调用 finishFirstExercise() 方法；又如果 phase
    // 等于 2，调用 finishSecondExercise() 方法；再如果 phase 等于 3，调用 finishExam()
    // 方法。否则，返回真值，表示phaser已经终结。
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                return studentsArrived();
            case 1:
                return finishFirstExercise();
            case 2:
                return finishSecondExercise();
            case 3:
                return finishExam();
            default:
                return true;
        }
    }

    // 3. 实现辅助方法 studentsArrived()。它在操控台写2条信息，并返回false值来表明phaser将继续执行。
    private boolean studentsArrived() {
        System.out.printf("Phaser: The exam are going to start. The students are ready.\n");
        System.out.printf("Phaser: We have %d students.\n",
                getRegisteredParties());
        return false;
    }

    // 4. 实现辅助方法 finishFirstExercise()。它在操控台写2条信息，并返回false值来表明phaser将继续执行。
    private boolean finishFirstExercise() {
        System.out.printf("Phaser: All the students have finished the first exercise.\n");
        System.out.printf("Phaser: It's time for the second one.\n");
        return false;
    }

    // 5. 实现辅助方法 finishSecondExercise()。它在操控台写2条信息，并返回false值来表明phaser将继续执行。
    private boolean finishSecondExercise() {
        System.out.printf("Phaser: All the students have finished the second exercise.\n");
        System.out.printf("Phaser: It's time for the third one.\n");
        return false;
    }

    // 6. 实现辅助方法 finishExam()。它在操控台写2条信息，并返回false值来表明phaser将继续执行。
    private boolean finishExam() {
        System.out.printf("Phaser: All the students have finished the exam.\n");
        System.out.printf("Phaser: Thank you for your time.\n");
        return true;
    }

    // 7. 创建一个类，名为 Student，并一定实现 Runnable 接口。这个类将模拟测验的学生。
    public class Student implements Runnable {

        // 8. 声明 a Phaser 对象，名为 phaser.
        private Phaser phaser;

        // 9. 实现类的构造函数，初始 Phaser 对象。
        public Student(Phaser phaser) {
            this.phaser = phaser;
        }

        // 10. 实现 run() 方法，模拟真实测验。
        @Override
        public void run() {

            // 11. 首先，方法写一条信息到操控台表明学生到达考场并调用 phaser 的 arriveAndAwaitAdvance()
            // 方法来等待其他线程们。
            System.out.printf("%s: Has arrived to do the exam. %s\n", Thread
                    .currentThread().getName(), new Date());
            phaser.arriveAndAwaitAdvance();

            // 12. 然后，写信息到操控台，调用私有 doExercise1() 方法模拟第一场测验，写另一条信息到操控台并调用 phaser
            // 的 arriveAndAwaitAdvance() 方法来等待其他学生结束第一场测验。
            System.out.printf("%s: Is going to do the first exercise. %s\n",
                    Thread.currentThread().getName(), new Date());
            doExercise1();
            System.out.printf("%s: Has done the first exercise. %s\n", Thread
                    .currentThread().getName(), new Date());
            phaser.arriveAndAwaitAdvance();

            // 13. 为第二场和第三场实现相同的代码。
            System.out.printf("%s: Is going to do the second exercise.%s\n",
                    Thread.currentThread().getName(), new Date());
            doExercise2();
            System.out.printf("%s: Has done the second exercise. %s\n", Thread
                    .currentThread().getName(), new Date());
            phaser.arriveAndAwaitAdvance();
            System.out.printf("%s: Is going to do the third exercise. %s\n",
                    Thread.currentThread().getName(), new Date());
            doExercise3();
            System.out.printf("%s: Has finished the exam. %s\n", Thread
                    .currentThread().getName(), new Date());
            phaser.arriveAndAwaitAdvance();
        }

        // 14. 实现辅助方法 doExercise1()。此方法让线程随机休眠一段时间。
        private void doExercise1() {
            try {
                long duration = (long) (Math.random() * 10);
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 15. 实现辅助方法 doExercise2()。此方法让线程随机休眠一段时间。
        private void doExercise2() {
            try {
                long duration = (long) (Math.random() * 10);
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 16. 实现辅助方法 doExercise3()。此方法让线程随机休眠一段时间。
        private void doExercise3() {
            try {
                long duration = (long) (Math.random() * 10);
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}