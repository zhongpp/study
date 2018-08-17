package thread;

/**
 * 说明锁是可以重入的
 * @author pingpingZhong
 * Date: 2018/3/2
 * Time: 11:31
 */
public class LockTest implements Runnable{

    public synchronized void get(){
        System.out.println(Thread.currentThread().getId());
        set();
    }

    public synchronized void set(){
        System.out.println(Thread.currentThread().getId());
    }

    @Override
    public void run() {
        get();
    }
    public static void main(String[] args) {
        LockTest ss=new LockTest();
        new Thread(ss).start();
        new Thread(ss).start();
        new Thread(ss).start();
    }
}
