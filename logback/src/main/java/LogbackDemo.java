import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pingpingZhong
 * Date: 2017/11/27
 * Time: 16:21
 */
@Slf4j
public class LogbackDemo {

    private static final Logger logger = LoggerFactory.getLogger(LogbackDemo.class);

    public static void main(String[] args) {
        while (true) {
            log.debug("=====debug=====");
            log.info("======info======");
            log.error("======error======");
            System.out.printf("dd");
        }
    }
}
