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
 * 创建日期：2019/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LegalCurrencyTypePopup extends AttachPopupView {
    private OnTypeChooseListener mOnTypeChooseListener;

    public LegalCurrencyTypePopup(@NonNull Context context, OnTypeChooseListener onTypeChooseListener) {
        super(context);
        this.mOnTypeChooseListener = onTypeChooseListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_legal_currency_type;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.lc_buy,
            R.id.lc_sell})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lc_buy:
                mOnTypeChooseListener.onChooseType(1, App.getInstance().getString(R.string.str_buy));
                break;
            case R.id.lc_sell:
                mOnTypeChooseListener.onChooseType(0, App.getInstance().getString(R.string.str_sell));
                break;
        }
        dismiss();
    }
}