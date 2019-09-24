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
public class B2BBuyTypePopup extends AttachPopupView {
    @BindView(R.id.tv_market_price)
    TextView mTvMarketPrice;
    @BindView(R.id.tv_custom_price)
    TextView mTvCustomPrice;
    private OnTypeChooseListener mOnTypeChooseListener;
    private int mBuytType;
    private Context mContext;

    /**
     * @param context
     * @param buytType              0 - 买入 1- 卖出
     * @param mOnTypeChooseListener
     */
    public B2BBuyTypePopup(@NonNull Context context, int buytType, OnTypeChooseListener mOnTypeChooseListener) {
        super(context);
        this.mContext = context;
        this.mBuytType = buytType;
        this.mOnTypeChooseListener = mOnTypeChooseListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_b2b_buy_type;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        mTvMarketPrice.setText(mBuytType == 0 ? mContext.getString(R.string.market_price_buy) : mContext.getString(R.string.market_price_sell));
        mTvCustomPrice.setText(mBuytType == 0 ? mContext.getString(R.string.limit_price_buy) : mContext.getString(R.string.limit_price_sell));
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
