package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.modulecfd.pojo.CfdTradePlaceBean;
import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnCfdTradePlaceListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdTradePopup extends BottomPopupView {
    @BindView(R.id.tv_popup_title)
    TextView mTvPopupTitle;
    @BindView(R.id.buy_type)
    TextView mBuyType;
    @BindView(R.id.number)
    TextView mNumber;
    @BindView(R.id.multiplier)
    TextView mMultiplier;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.priceTrans)
    TextView mPriceTrans;
    @BindView(R.id.marginLever)
    TextView mMarginLever;
    @BindView(R.id.commissionAccount)
    TextView mCommissionAccount;
    @BindView(R.id.openFee)
    TextView mOpenFee;
    @BindView(R.id.stopProfitPrice)
    EditText mStopProfitPrice;
    @BindView(R.id.stopLossPrice)
    EditText mStopLossPrice;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;

    private Context mContext;
    private boolean tradeType;                      //true - 市价 false - 限价
    private int buyType;                            //0 - 买入 1 - 卖出
    private String commissionAccount;               //委托保证金
    private CfdTradePlaceBean mCfdTradePlaceBean;
    private OnCfdTradePlaceListener mOnCfdTradePlaceListener;

    public CfdTradePopup(@NonNull Context context, boolean tradeType, int buyType, String commissionAccount, CfdTradePlaceBean cfdTradePlaceBean, OnCfdTradePlaceListener onCfdTradePlaceListener) {
        super(context);
        this.mContext = context;
        this.tradeType = tradeType;
        this.buyType = buyType;
        this.commissionAccount = commissionAccount;
        this.mCfdTradePlaceBean = cfdTradePlaceBean;
        this.mOnCfdTradePlaceListener = onCfdTradePlaceListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_cfd_trade;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvPopupTitle.setText(buyType == 0 ? App.getInstance().getString(R.string.commission_buy) : App.getInstance().getString(R.string.commission_sell));
        mBuyType.setText((tradeType ? App.getInstance().getString(R.string.market_price)
                : App.getInstance().getString(R.string.limit_price))
                + (buyType == 0 ? App.getInstance().getString(R.string.buy)
                : App.getInstance().getString(R.string.sell)));
        mNumber.setText(mCfdTradePlaceBean.getVolume() + App.getInstance().getString(R.string.hands));
        mMultiplier.setText(mCfdTradePlaceBean.getMultiplier() + "X");
        mPrice.setText(tradeType ? App.getInstance().getString(R.string.optimal_price) : mCfdTradePlaceBean.getPriceType() + " USDT");
        mPriceTrans.setText(tradeType ? App.getInstance().getString(R.string.market_price) : mCfdTradePlaceBean.getPriceType() * mCfdTradePlaceBean.getVolume() + " USDT");
        mMarginLever.setText(mCfdTradePlaceBean.getMarginLever() + "X");
        mCommissionAccount.setText(commissionAccount + " USDT");
        mOpenFee.setText(DfUtils.numberFormat(mCfdTradePlaceBean.getOpenFee(), 2) + " USDT");
    }

    @OnClick({R.id.tv_confirm,
            R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                mOnCfdTradePlaceListener.onChooseType(StringUtils.isEmpty(mStopProfitPrice.getText().toString().trim()) ?
                        0 :
                        Double.valueOf(mStopProfitPrice.getText().toString().trim()), StringUtils.isEmpty(mStopLossPrice.getText().toString().trim()) ?
                        0 :
                        Double.valueOf(mStopLossPrice.getText().toString().trim()));
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
