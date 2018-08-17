import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pingpingZhong
 *         Date: 2017/9/18
 *         Time: 16:41
 */
public class StringUtil {

    public static void main(String[] args) {
        String str = "111111,222222,4444444";
        List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
        items.forEach(code -> System.out.println(code));

        String address = "中国浙江省杭州市上城区浙江朗闰资产管理有限公司";
        String add = "拱墅,西湖,上城,下城,江干,滨江";

        Object s = null;
        Integer i = (Integer)s;
        System.out.println(i);


        Map map = new HashMap();
        map.put("name","zhongpp");
        System.out.println(map.size());

        Map map2 = new HashMap();
        map2.put("age","21");
        map2.put("high","170");
        map.putAll(map2);
        System.out.println(map.size());

        Map map1 = null;
        //map.putAll(map1);
        System.out.println(map.size());

        String tripUuid = "11111";
        List<String> trips = ImmutableList.of("11111");
        List<String> tripUuids = trips.stream()
                .filter(_uuid -> {
                    System.out.println(!tripUuid.equals(_uuid));
                    return !tripUuid.equals(_uuid);
                })
                .collect(Collectors.toList());
        System.out.println(tripUuids.size());

        List<String> list = new ArrayList<String>();
        list.add("TIDAkocy");
        list.add("2c9a858960c423300160c450bdd501c6");
        list.add("2c9a858960c423300160c450bdd501c6");
        list.add("1.0.0");
        list.add("E1iCU9r3YtNH0ccSkF9KBx56oRs3mI5texic8zkaJXQOvIw8yIm1JNezZMs5LTT9");

        System.out.println(sign(list));
    }


    public static String sign(List<String> values) {
        if (values == null) {
            throw new NullPointerException("values is null");
        }

        values.removeAll(Collections.singleton(null));// remove null
        Collections.sort(values);
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }

        return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
    }

}
