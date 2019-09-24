package com.spark.qtz.ui;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/27
 * 描    述：限制输入小数位数
 * 修订历史：
 * ================================================
 */
public class PointLengthFilter implements InputFilter {


    /**
     * 输入框小数的位数  示例保留一位小数
     */
    private static int DECIMAL_DIGITS = 1;

    public PointLengthFilter(int decimal_digits) {
        DECIMAL_DIGITS = decimal_digits;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        //以点开始的时候，自动在前面添加0
        if (source.toString().equals(".") && dstart == 0) {
            return "0.";
        }
        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (!source.toString().equals(".") && dest.toString().equals("0")) {
            return "";
        }

        // 删除等特殊字符，直接返回
        if ("".equals(source.toString())) {
            return null;
        }
        String dValue = dest.toString();
        String[] splitArray = dValue.split("\\.");
        if (splitArray.length > 1) {
            String dotValue = splitArray[1];
            int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
            if (diff > 0 && end >= diff && start <= end - diff) {
                return source.subSequence(start, end - diff);
            }
        }
        return null;
    }
}
