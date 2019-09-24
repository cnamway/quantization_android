package com.spark.qtz.ui.impl;

import com.lxj.xpopup.impl.FullScreenPopupView;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/17
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface OnVerifyPhoneListener {
    void onReSendSMS(FullScreenPopupView fullScreenPopupView);

    void onReceiveCode(String smsCode);
}
