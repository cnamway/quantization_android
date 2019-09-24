package me.spark.mvvm.utils;

import android.widget.EditText;

import org.json.JSONObject;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/7
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class StringUtils {

    /**
     * 判断给定的字符串是否为null或者是空的
     *
     * @param string 给定的字符串
     */
    public static boolean isEmpty(String string) {
        return string == null || "".equals(string.trim());
    }


    /**
     * 判断给定的字符串是否不为null且不为空
     *
     * @param string 给定的字符串
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean isNotEmpty(String string1, String string2) {
        return !isEmpty(string1) && !isEmpty(string2);
    }

    /**
     * 手机号格式化
     *
     * @param phoneNum 13012340000 -->130****0000
     * @return
     */
    public static String formatPhone(String phoneNum) {
        if (phoneNum.length() == 13 && phoneNum.startsWith("86")) {
            return phoneNum.substring(2).replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return phoneNum;
    }

    //---chat
    public static int getCode(JSONObject jsonObject) {
        return jsonObject.optInt("code");
    }

    /**
     * 获取message
     *
     * @param jsonObject
     * @return
     */
    public static String getMessage(JSONObject jsonObject) {
        return jsonObject.optString("message");
    }

    /**
     * 获取url
     *
     * @param jsonObject
     * @return
     */
    public static String getUrl(JSONObject jsonObject) {
        return jsonObject.optString("url");
    }

    /**
     * 获取cid
     *
     * @param jsonObject
     * @return
     */
    public static String getCid(JSONObject jsonObject) {
        return jsonObject.optString("cid");
    }

    /**
     * code码为非200时，返回的数据
     *
     * @param jsonObject
     * @return
     */
    public static String getData(JSONObject jsonObject) {
        return jsonObject.optString("data");
    }

    /**
     * 获取text
     *
     * @param editText
     * @return
     */
    public static String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 去掉字符串最后一个逗号
     *
     * @param str
     * @return
     */
    public static String getRealString(String str) {
        if (StringUtils.isNotEmpty(str)) {
            if (str.length() > 1 && str.endsWith(",")) {
                str = str.substring(0, str.length() - 1);
            }
            return str;
        }
        return "";
    }

    public static int formatVersionCode(String versionName) {
        return Integer.valueOf(versionName.replaceAll("\\.",""));
    }
}
