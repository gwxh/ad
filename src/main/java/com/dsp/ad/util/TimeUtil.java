package com.dsp.ad.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static int now() {
        return (int) LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

    public static String getYYYYMMDDhhmmss(LocalDateTime localDateTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return df.format(localDateTime);
    }
}
