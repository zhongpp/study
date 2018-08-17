package com.zpp.util;

import org.joda.time.DateTime;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 01366708 on 2017/5/23.
 */
public class PatternTest {
    public static void main(String[] args) {
        /**
        Pattern pattern = Pattern.compile("/captchas/.*.jpg");
        System.out.println(pattern.matcher("/captchas/2c9a85895c351c87015c351d223f0000.jpg").matches());
        System.out.println(Boolean.valueOf("0"));

        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher("dffdfdf@qq.com");
        boolean isMatched = matcher.matches();
        System.out.println(isMatched);
*/

        //2017-06-26T10:47:13.820+08:00
        String time = "^\\d{1,4}-\\d{1,2}-\\d{1,2}(\\d|:|T|.|\\+)*";
        Pattern regex2 = Pattern.compile(time);
        Matcher matcher2 = regex2.matcher("2017-06-26T10:47:13.820+08:00");
        boolean isMatched2 = matcher2.matches();
        System.out.println(isMatched2);

        System.out.println(new Date(new DateTime("2017-06-26").getMillis()));

        System.out.println(DateTime.now().plusSeconds(3600).isAfterNow());

    }
}
