package me.spark.mvvm.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/12
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DfUtils {
    /**
     * 格式化数字 1234.5678 --> 1,234.5678
     *
     * @param num
     * @return
     */
    public static String formatNum(String num) {
        if (!num.contains(".")) return num;
        String front = num.split("\\.")[0];
        DecimalFormat formatter = new DecimalFormat();
        formatter.setGroupingSize(3);
        front = formatter.format(Double.valueOf(front));
        return front + "." + num.split("\\.")[1];
    }

    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }


    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }


    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).doubleValue();
    }


    /**
     * NumberFormat取消Double科学计数法
     *
     * @param scale
     * @param number
     * @return
     */
    public static String numberFormat(double number, int scale) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(scale);//设置保留多少位小数
        return nf.format(number);
    }

    public static String numberFormatDown(double number, int scale) {
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_DOWN);
        return bigDecimal.doubleValue() + "";
    }

    public static String numberFormat(String number, int scale) {
        double temp = Double.valueOf(number);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(scale);//设置保留多少位小数
        return nf.format(temp);
    }

    public static String numberFormatWithZero(double number, int scale) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(scale);
        nf.setMaximumFractionDigits(scale);//设置保留多少位小数
        return nf.format(number);
    }

    /**
     * 精确除法
     *
     * @param scale 精度
     */
    public static double divUp(double value1, double value2, int scale) {
        if (scale < 0) {
            return 0;
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入
     *
     * @param scale 小数点后保留几位
     */
    public static double roundUp(double v, int scale) {
        return divUp(v, 1, scale);
    }

    /**
     * 精确除法
     *
     * @param scale 精度
     */
    public static double divDown(double value1, double value2, int scale) {
        if (scale < 0) {
            return 0;
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 四舍五入
     *
     * @param scale 小数点后保留几位
     */
    public static double roundDown(double v, int scale) {
        return divUp(v, 1, scale);
    }
}
