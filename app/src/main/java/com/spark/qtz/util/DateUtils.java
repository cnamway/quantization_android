package com.spark.qtz.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DateUtils {

    public static String formatKlineTime(int type, long time) {
        String pattern;
        if (type == 4 || type == 8 || type == 7) {
            pattern = "yyyy-MM-dd";
        } else if (type == 0 || type == 1) {
            pattern = "HH:mm";
        } else {
            pattern = "MM-dd HH:mm";
        }
        return getFormatTime(pattern, new Date(time));
    }

    /**
     * 将时间戳转化成固定格式（默认 yyyy-MM-dd HH:mm:ss 当前时间 ）
     *
     * @param format
     * @param date
     * @return
     */
    public static String getFormatTime(String format, Date date) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 判断2个字符的时间 哪个大
     *
     * @return 1 :大    0：相等   -1：小/异常
     */
    public static int compare(String time1, String time2, int tag) {
        SimpleDateFormat sdf = null;
        if (tag == 4 || tag == 8 || tag == 7) {
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        } else if (tag == 0 || tag == 1) {
            sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        } else {
            sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        }
        // 将字符串形式的时间转为Date类型的时间
        try {
            Date a = sdf.parse(time1);
            Date b = sdf.parse(time2);
            if (a.getTime() > b.getTime()) {
                return 1;
            } else if (a.getTime() == b.getTime()) {
                return 0;
            } else {
                return -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
