package com.icode.jiling.na517demo_mvvm.utils;

/**
 * Created by jiling on 2018/4/3.
 */

public class TimeUtils {
    public static String getTimeStr(int timecount) {
        String hour = timecount/(60*60) != 0 ? timecount/(60*60)+":" : "";
        if(!"".equals(hour)){
            timecount = timecount%(60*60);
        }
        String min = (int) (Math.floor(timecount / 60)) + "".length() >= 2 ? (int) (Math.floor(timecount / 60)) + ""
                : "0" + (int) (Math.floor(timecount / 60));
        String second = timecount % 60 > 9 ? timecount % 60 + "" : "0" + timecount % 60;
        return hour + min + ":" + second;
    }
}
