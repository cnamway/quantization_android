package me.spark.mvvm.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/7
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MathUtils {
    public static String getRundNumber(double number, int n, String pattern) {
        if (StringUtils.isEmpty(pattern)) pattern = "########0.";
        String str = "";
        for (int j = 0; j < n; j++) str += "0";
        pattern += str;
        int m = (int) Math.pow(10, n);
        number = (Math.round(number * m)) / (m * 1.0);
        String result = new DecimalFormat(pattern).format(number);
        return result.endsWith(".") ? result.substring(0, result.length() - 1) : result;
    }

    /**
     * 过滤多余的0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (StringUtils.isNotEmpty(s) && s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
            return s;
        }
        return s;
    }

    /**
     * 精确到n位小数
     *
     * @param number
     * @param n
     * @return
     */
    public static String getBigDecimalRundNumber(String number, int n) {
        DecimalFormat df;
        String pattern = "0";
        if (n > 0) {
            pattern = "0.";
            for (int j = 0; j < n; j++) {
                pattern += "0";
            }
        }
        df = new DecimalFormat(pattern);
        if (StringUtils.isNotEmpty(number)) {
            BigDecimal resultBD = new BigDecimal(number.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);//四舍五入
            return df.format(resultBD).replace(",", ".");
        } else {
            return df.format(0).replace(",", ".");
        }
    }

    /**
     * 加
     *
     * @param number1
     * @param number2
     * @param n
     * @return
     */
    public static String getBigDecimalAddWithScale(String number1, String number2, int n) {
        if (StringUtils.isNotEmpty(number1, number2)) {
            BigDecimal aBD = new BigDecimal(number1.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            BigDecimal bBD = new BigDecimal(number2.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            BigDecimal resultBD = aBD.add(bBD).setScale(n, BigDecimal.ROUND_HALF_UP);
            return resultBD.toPlainString();
        }
        return "0";
    }

    /**
     * 减
     *
     * @param number1
     * @param number2
     * @param n
     * @return
     */
    public static String getBigDecimalSubtractWithScale(String number1, String number2, int n) {
        if (StringUtils.isNotEmpty(number1, number2)) {
            BigDecimal aBD = new BigDecimal(number1.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            BigDecimal bBD = new BigDecimal(number2.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            BigDecimal resultBD = aBD.subtract(bBD).setScale(n, BigDecimal.ROUND_DOWN);
            return resultBD.toPlainString();
        }
        return "0";
    }

    /**
     * 乘
     *
     * @param number1
     * @param number2
     * @param n
     * @return
     */
    public static String getBigDecimalMultiplyWithScale(String number1, String number2, int n) {
        if (StringUtils.isNotEmpty(number1, number2)) {
            BigDecimal aBD = new BigDecimal(number1.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            BigDecimal bBD = new BigDecimal(number2.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            BigDecimal resultBD = aBD.multiply(bBD).setScale(n, BigDecimal.ROUND_HALF_UP);
            return resultBD.toPlainString();
        }
        return "0";

    }

    /**
     * 除
     *
     * @param number1
     * @param number2
     * @param n
     * @return
     */
    public static String getBigDecimalDivideWithScale(String number1, String number2, int n) {
        if (StringUtils.isNotEmpty(number1, number2)) {
            BigDecimal aBD = new BigDecimal(number1.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            BigDecimal bBD = new BigDecimal(number2.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            BigDecimal resultBD = aBD.divide(bBD, n, BigDecimal.ROUND_HALF_UP);
            return resultBD.toPlainString();
        }
        return "0";
    }

    /**
     * 大小比较
     *
     * @param number1
     * @param number2
     * @param n
     * @return
     */
    //BigDecimal对比 BigDecimal为小于val返回-1，如果BigDecimal为大于val返回1，如果BigDecimal为等于val返回0
    public static int getBigDecimalCompareTo(String number1, String number2, int n) {
        if (StringUtils.isNotEmpty(number1, number2)) {
            BigDecimal aBD = new BigDecimal(number1.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            BigDecimal bBD = new BigDecimal(number2.trim()).setScale(n, BigDecimal.ROUND_HALF_UP);
            return aBD.compareTo(bBD);
        } else {
            return -2;
        }
    }
}
