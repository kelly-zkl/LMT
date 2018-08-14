package com.wisec.scanner.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qwe on 2018/5/14.
 */

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat;

    @SuppressLint("SimpleDateFormat")
    public static String getDefaultDate(long time) {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateString(long time, String formate) {
        simpleDateFormat = new SimpleDateFormat(formate);
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }
}
