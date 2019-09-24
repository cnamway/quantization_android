package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.api.pojo.PayTypeBean;
import com.spark.qtz.ui.SmoothCheckBox;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.LcTradeClient;
import com.spark.otcclient.pojo.LcOrderResult;
import com.spark.otcclient.pojo.OrderPayBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.DfUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcPayPopup extends BottomPopupView {
    @BindView(R.id.title_sub)
    TextView mTitleSub;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.num)
    TextView mNum;
    @BindView(R.id.account)
    TextView mAccount;
    @BindView(R.id.wechat_cb)
    SmoothCheckBox mWechatCb;
    @BindView(R.id.alipay_cb)
    SmoothCheckBox mAlipayCb;
    @BindView(R.id.bank_cb)
    SmoothCheckBox mBankCb;
    @BindView(R.id.ensure)
    TextView mEnsure;
    @BindView(R.id.cancel)
    TextView mCancel;
    @BindView(R.id.ll_wechat)
    LinearLayout mLlWechat;
    @BindView(R.id.ll_alipay)
    LinearLayout mLlAlipay;
    @BindView(R.id.ll_bank)
    LinearLayout mLlBank;
    @BindView(R.id.paypal_cb)
    SmoothCheckBox mPaypalCb;
    @BindView(R.id.ll_paypal)
    LinearLayout mLlPaypal;

    private LcOrderResult.DataBean.RecordsBean mRecordsBean;
    private PayTypeBean mPayTypeBean;
    private String actualPayment;

    public LcPayPopup(@NonNull Context context, LcOrderResult.DataBean.RecordsBean mRecordsBean, PayTypeBean mPayTypeBean) {
        super(context);
        this.mRecordsBean = mRecordsBean;
        this.mPayTypeBean = mPayTypeBean;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_lc_pay;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleSub.setText(App.getInstance().getApplicationContext().getString(R.string.str_after_order) + mRecordsBean.getCoinName() + App.getInstance().getApplicationContext().getString(R.string.str_will_lock));
        mPrice.setText(DfUtils.numberFormat(mRecordsBean.getPrice(), mRecordsBean.getPrice() == 0 ? 0 : 8) + " CNY");
        mNum.setText(DfUtils.numberFormat(mRecordsBean.getNumber(), mRecordsBean.getNumber() == 0 ? 0 : 8) + " " + mRecordsBean.getCoinName());
        mAccount.setText(DfUtils.numberFormat(mRecordsBean.getMoney(), mRecordsBean.getMoney() == 0 ? 0 : 8) + " CNY");
        for (PayTypeBean.PayTypeBeanBean payTypeBeanBean : mPayTypeBean.getPayTypeBean()) {
            if (payTypeBeanBean.getPayType().contains(Constant.wechat)) {
                mLlWechat.setVisibility(VISIBLE);
            } else if (payTypeBeanBean.getPayType().contains(Constant.alipay)) {
                mLlAlipay.setVisibility(VISIBLE);
            } else if (payTypeBeanBean.getPayType().contains(Constant.card)) {
                mLlBank.setVisibility(VISIBLE);
            } else if (payTypeBeanBean.getPayType().contains(Constant.PAYPAL)) {
                mLlPaypal.setVisibility(VISIBLE);
            }
        }

        initCb();
    }

    private void initCb() {
        mWechatCb.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    mAlipayCb.setChecked(false);
                    mBankCb.setChecked(false);
                    mPaypalCb.setChecked(false);
                    actualPayment = Constant.wechat;
                }
            }
        });

        mAlipayCb.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    mWechatCb.setChecked(false);
                    mBankCb.setChecked(false);
                    mPaypalCb.setChecked(false);
                    actualPayment = Constant.alipay;
                }
            }
        });

        mBankCb.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    mWechatCb.setChecked(false);
                    mAlipayCb.setChecked(false);
                    mPaypalCb.setChecked(false);
                    actualPayment = Constant.card;
                }
            }
        });

        mPaypalCb.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    mWechatCb.setChecked(false);
                    mAlipayCb.setChecked(false);
                    mBankCb.setChecked(false);
                    actualPayment = Constant.PAYPAL;
                }
            }
        });
    }

    @OnClick({R.id.ensure,
            R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ensure:
                if (mWechatCb.isChecked() || mAlipayCb.isChecked() || mBankCb.isChecked()|| mPaypalCb.isChecked()) {
                    OrderPayBean orderPayBean = new OrderPayBean();
                    orderPayBean.setActualPayment(actualPayment);
                    orderPayBean.setOrderSn(mRecordsBean.getOrderSn());
                    LcTradeClient.getInstance().orderPayMent(orderPayBean);
                    dismiss();
                } else {
                    Toasty.showError(App.getInstance().getString(R.string.str_set_paypay));
                }
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
