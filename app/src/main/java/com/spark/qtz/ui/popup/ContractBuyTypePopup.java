package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.AttachPopupView;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnTypeChooseListener;

import butterknife.BindView;
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
public class ContractBuyTypePopup extends AttachPopupView {
    @BindView(R.id.tv_market_price)
    TextView mTvMarketPrice;
    @BindView(R.id.tv_custom_price)
    TextView mTvCustomPrice;

    private OnTypeChooseListener mOnTypeChooseListener;

    /**
     * @param context
     * @param mOnTypeChooseListener
     */
    public ContractBuyTypePopup(@NonNull Context context,  OnTypeChooseListener mOnTypeChooseListener) {
        super(context);
        this.mOnTypeChooseListener = mOnTypeChooseListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_contract_buy_type;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_market_price,
            R.id.tv_custom_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_market_price:
                //市价
                mOnTypeChooseListener.onChooseType(0, mTvMarketPrice.getText().toString());
                dismiss();
                break;
            case R.id.tv_custom_price:
                //限价
                mOnTypeChooseListener.onChooseType(1, mTvCustomPrice.getText().toString());
                dismiss();
                break;
        }
    }


}
