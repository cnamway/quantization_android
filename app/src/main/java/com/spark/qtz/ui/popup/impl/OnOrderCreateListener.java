package com.spark.qtz.ui.popup.impl;

import com.spark.otcclient.pojo.OrderCreateBean;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface OnOrderCreateListener {
    void onOrderCreate(OrderCreateBean orderCreateBean);
}
