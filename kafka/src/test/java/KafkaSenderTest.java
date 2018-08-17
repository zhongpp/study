import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author pingpingZhong
 * Date: 2018/1/29
 * Time: 17:48
 */
public class KafkaSenderTest {
    private final static int THREAD_COUNT = 1000;
    private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread() + new Date().getTime());
                }
            }).start();
            countDownLatch.countDown();
        }
    }
}
