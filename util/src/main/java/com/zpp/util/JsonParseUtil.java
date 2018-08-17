package com.zpp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;


/**
 * @author pingpingZhong
 *         Date: 2017/6/26
 *         Time: 10:28
 */
public class JsonParseUtil {
    private static JsonNodeFactory jsonNodeFactory = new JsonNodeFactory(false);
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] args) throws Exception{
        System.out.println(new DateTime());
        String params = "[2017-06-26T10:47:13.820+08:00,111,9,[11,222]]";

        List list = objectMapper.readValue(params, List.class);

        list.forEach(
                param ->  {
                    if (param instanceof Integer) {
                        int value = ((Integer) param).intValue();
                        //prepStatement.setInt(i + 1, value);
                    } else if (param instanceof String) {
                        String s = (String) param;
                        //prepStatement.setString(i + 1, s);
                    } else if (param instanceof Double) {
                        double d = ((Double) param).doubleValue();
                        //prepStatement.setDouble(i + 1, d);
                    } else if (param instanceof Float) {
                        float f = ((Float) param).floatValue();
                        //prepStatement.setFloat(i + 1, f);
                    } else if (param instanceof Long) {
                        long l = ((Long) param).longValue();
                        //prepStatement.setLong(i + 1, l);
                    } else if (param instanceof Boolean) {
                        boolean b = ((Boolean) param).booleanValue();
                        //prepStatement.setBoolean(i + 1, b);
                    } else if (param instanceof Date) {
                        Date d = (Date) param;
                        //prepStatement.setDate(i + 1, (Date) param);
                    }

                }
        );

    }
}
