package thread;

/**
 * sleep()睡眠时，保持对象锁，仍然占有该锁；
 * wait()睡眠时，释放对象锁。
 *
 * @author pingpingZhong
 * Date: 2018/3/2
 * Time: 10:02
 */
public class ThreadSleepAndWaitTest implements Runnable {
    int number = 10;

    public void firstMethod() throws Exception {
        System.out.println("firstMethod");
        synchronized (this) {
            number += 100;
            System.out.println(number);
        }
    }

    public void secondMethod() throws Exception {
        System.out.println("secondMethod");
        synchronized (this) {
            /**
             * (休息2S,阻塞线程)
             * 以验证当前线程对象的机锁被占用时,
             * 是否被可以访问其他同步代码块
             */
            Thread.sleep(2000);
            //this.wait(2000);
            number *= 200;
        }
    }

    @Override
    public void run() {
        try {
            firstMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadSleepAndWaitTest threadTest = new ThreadSleepAndWaitTest();
        Thread thread = new Thread(threadTest);
        thread.start();
        threadTest.secondMethod();
        System.out.println("number=" + threadTest.number);
    }
}
