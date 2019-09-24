package me.spark.mvvm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/16
 * 描    述：
 * 修订历史：
 * ================================================
 */

public class DateUtils {
    /**
     * 格式化日期
     *
     * @param date 待格式日期
     * @return 格式后的日期
     */
    public static String formatDate(String format, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = new Date(Long.valueOf(date));
        String newDate = sdf.format(d);
        return newDate;
    }

    public static String formatDate(String format, long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = new Date(date);
        String newDate = sdf.format(d);
        return newDate;
    }
}
