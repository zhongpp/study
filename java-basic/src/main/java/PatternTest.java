import java.util.regex.Pattern;

/**
 * @author pingpingZhong
 *         Date: 2017/9/12
 *         Time: 19:34
 */
public class PatternTest {

    private static final Pattern[] API_WHITE_LIST_PATTERNS = new Pattern[]{
            Pattern.compile("/calculations/*/output.csv"),
    };

    public static void main(String[] args) {
        String url = "/calculations/2c9a85895e708069015e750b8d7c10aa/output.csv";
        System.out.println(Pattern.compile("/calculations/.*/output.csv").matcher(url).matches());
    }

}
