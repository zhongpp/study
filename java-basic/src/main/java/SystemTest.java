import java.util.Iterator;
import java.util.Map;

/**
 * @author pingpingZhong
 *         Date: 2017/8/22
 *         Time: 11:24
 */
public class SystemTest {

    public static void main(String[] args) {
        Map map = System.getenv();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.print(entry.getKey() + "=");
            System.out.println(entry.getValue());
        }
    }

}
