package me.spark.mvvm.utils.language;

import android.content.Context;

import java.util.Locale;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/7/3
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface LanguageLocalListener {

    /**
     * 获取选择设置语言
     *
     * @param context
     * @return
     */
    Locale getSetLanguageLocale(Context context);
}
