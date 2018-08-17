package com.zpp.util;

import org.joda.time.DateTime;

/**
 * @author pingpingZhong
 *         Date: 2017/9/19
 *         Time: 16:20
 */
public class JodaUtil {

    public static void main(String[] args) {
        System.out.println(new DateTime("2017-10-11T19:16:36.564+08:00").toDate());
        System.out.println(DateTime.now());
        String now = DateTime.now().toString();
        DateTime startTime = new DateTime(now);
        System.out.println(startTime);
        System.out.println(DateTime.now().plusHours(-1).toString());
        System.out.println(DateTime.now().plusHours(-1).plusMinutes(5).toString());
        System.out.println(DateTime.now().plusHours(-24).toString());
        System.out.println(DateTime.now().plusHours(-24).plusMinutes(30).toString());
        System.out.println(DateTime.now().plusHours(1));
        System.out.println(DateTime.now().withTimeAtStartOfDay());
        System.out.println(DateTime.now().withTime(23, 59, 59, 100));
        System.out.println(DateTime.now().dayOfYear().get());
        System.out.println(DateTime.now().plusDays(-1).withHourOfDay(0).withMillisOfDay(0).withSecondOfMinute(0));
        System.out.println(DateTime.now().withDayOfMonth(1));
        System.out.println(DateTime.now().plusSeconds(60 * 60 * 24));
        System.out.println(DateTime.now().plusSeconds(60 * 60 * 24).isAfterNow());


    }
}
