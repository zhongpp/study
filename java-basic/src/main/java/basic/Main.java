package basic;

/**
 * javap -c xx.class 查看java文件编译后的指令
 * load 将本地变量加载到操作数栈的指令
 * store 将一个数值从操作数栈存储到局部变量
 * push 将一个常量加载到操作数栈
 * @author pingpingZhong
 * Date: 2018/3/27
 * Time: 14:27
 */
public class Main {
    //简单的整型操作
     void spin(){
        int i;
        for(i=0;i<100;i++){
            ;
        }
    }

    //对象操作
    void newInstance(){
         Main main = new Main();
            Main[] arrs = new Main[50];
     }

    //类型转换指令
    void i2l(){
        int j = 2;
        long i = j;
    }

    //控制转移指令
    int controller(int i){
        if(i > 0){
            System.out.println("no");
        }else{
            System.out.println("yes");
        }
        return i;
    }

    //方法调用和返回指令
    int call(){
       return controller(0);
    }

    //抛出异常指令
    void throwException() throws Exception{
        throw new Exception("exception");
    }

    //同步指令
    synchronized void add(){
        Object object = new Object();
        synchronized (object){

        }
    }
}
