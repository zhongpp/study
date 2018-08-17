/**
 * @author pingpingZhong
 *         Date: 2017/8/16
 *         Time: 14:19
 */
public class SwitchTest {

    public static void main(String[] args) {
        Integer i = null;
        switch (i) {
            case 1:
                System.out.println(1);
                break;
            case 2:
                System.out.println(2);
                break;
            case 3:
                System.out.println(3);
                break;
            case 4:
                System.out.println(4);
                break;
            default:
                System.out.println("default");
        }

        SexEnum sex = null;
        switch (sex) {
            case MAN:
                System.out.println("男的");
            case WOMAN:
                System.out.println("女的");
            default:
                System.out.println("?");
        }

    }


}

