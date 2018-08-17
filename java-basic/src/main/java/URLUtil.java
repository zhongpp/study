import com.google.common.base.Splitter;

import java.util.Map;

/**
 * @author pingpingZhong
 *         Date: 2017/9/8
 *         Time: 14:05
 */
public class URLUtil {

    public static void main(String[] args) {
        String url = "xxx/query_request_by_time_and_city?param1='2017-08-04T00:00:00.000+0800'&param2='2017-08-08T23:59:59.999+0800'&param3='º¼ÖÝ'";
        //getParams(url);
        System.out.println(getPara(url,"param1"));
    }

    public static String getPara(String url, String name) {
        String params = url.substring(url.indexOf("?") + 1, url.length());
        Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(params);
        return split.get(name);
    }

}
