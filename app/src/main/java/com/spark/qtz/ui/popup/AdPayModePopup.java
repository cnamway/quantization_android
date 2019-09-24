package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.lxj.xpopup.core.AttachPopupView;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnTypeChooseListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AdPayModePopup extends AttachPopupView {
    private OnTypeChooseListener mOnTypeChooseListener;

    public AdPayModePopup(@NonNull Context context, OnTypeChooseListener mOnTypeChooseListener) {
        super(context);
        this.mOnTypeChooseListener = mOnTypeChooseListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_ad_pay_mode;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.mode_alipay,
            R.id.mode_wechatpay,
            R.id.mode_bankpay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.mode_alipay:
                mOnTypeChooseListener.onChooseType(0, App.getInstance().getString(R.string.str_alipay));
                break;
            case R.id.mode_wechatpay:
                mOnTypeChooseListener.onChooseType(1, App.getInstance().getString(R.string.str_wechat));
                break;
            case R.id.mode_bankpay:
                mOnTypeChooseListener.onChooseType(2, App.getInstance().getString(R.string.str_bank));
                break;
        }
        dismiss();
    }
}
