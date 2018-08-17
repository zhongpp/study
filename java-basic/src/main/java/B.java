/**
 * @author pingpingZhong
 *         Date: 2017/8/24
 *         Time: 16:48
 */
public class B {

    public static void main(String[] args) {
        A a = new A2();
        a.show();
    }

}

class A {
    public void show(){
        System.out.println("A.show()");
    }
}

class A2 extends A{
    public void show(){
        System.out.println("A2.show()");
    }
}
