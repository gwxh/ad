package com.dsp.ad.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static int day() {
        return day(0);
    }

    public static int day(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    public static int month(){
        return month(0);
    }

    public static int month(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    public static int now() {
        return (int) LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

    public static String toDate(LocalDateTime localDateTime,String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDateTime);
    }

    public static int getTimestamp(String[] strings) {
        int hour = Integer.parseInt(strings[0]);
        int minute = Integer.parseInt(strings[1]);
        int second = Integer.parseInt(strings[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MINUTE, minute);
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    public static void main(String[] args) {
        System.out.println(day());
    }
}
