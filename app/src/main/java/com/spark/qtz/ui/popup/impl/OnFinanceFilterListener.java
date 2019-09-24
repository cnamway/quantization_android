package com.spark.qtz.ui.popup.impl;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface OnFinanceFilterListener {
    void onSelect(String filterTypeSelect, String filterSubTypeSelect, String minLimit, String maxLimit, String startTime, String endTime);
}
