package com.dsp.ad.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtil {

    public static int now(){
        return (int) LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }
}
