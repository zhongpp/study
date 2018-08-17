package thread;

import org.apache.log4j.MDC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 01366708 on 2017/3/10.
 */
public class test {
    private static int count = 0;
    private static int max = 20;
    private static int threadCount = 1;
    private static ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    private static Driver driver = new Driver("Driver",1,false);
    private static Driver driver2 = new Driver("Driver2",2,true);

    public static void main(String[] args) {

        while(waitTick()){
                System.out.println("run tick"+new Date());
                runTick();
                System.out.println("------------------------");
        }

    }

    private static boolean waitTick(){
        while(true){
            if(count>max){
                return false;
            }
            try {
                Thread.sleep(5000);
                return true;
            } catch (InterruptedException e) {
                return false;
            }
        }
    }

    private static void runTick(){
        Collection<Callable<Object>> collection = new ArrayList();
        collection.add(new DriverCallable(driver));
        collection.add(new DriverCallable(driver2));
        try {
            executor.invokeAll(collection);
        }catch (Exception e){
            e.printStackTrace();
        }
        count++;
    }


    private static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}

class DriverCallable implements Callable{

    private Driver driver;

    public DriverCallable(Driver driver){
        this.driver = driver;
    }

    public Object call() throws Exception {
        MDC.put(String.valueOf(driver.getId()),driver.getName());
        driver.runTick();
        System.out.println(" name: "+MDC.get(String.valueOf(driver.getId())));
        return null;
    }

}

class Driver{

    private String name;
    private int id;
    private boolean flag;

    public Driver(String name, int id, boolean flag){
        this.name = name;
        this.id = id;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void runTick(){
        try {
            if(this.flag){
                System.out.println(this.name + "=====" + flag);
            }
            Thread.currentThread().sleep(5000);
        } catch (Exception e) {

        }
    }

}