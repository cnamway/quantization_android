package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.lxj.xpopup.core.AttachPopupView;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.qtz.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * CoinExtractNoticePopup
 */
public class CoinExtractNoticePopup extends AttachPopupView {
    @BindView(R.id.extract_notice_tv)
    TextView mExtractNoticeTv;
    private CoinSupportBean.DataBean mCoinSupportBean;
    private Context mContext;
    public CoinExtractNoticePopup(@NonNull Context context, CoinSupportBean.DataBean coinSupportBean) {
        super(context);
        this.mContext = context;
        this.mCoinSupportBean = coinSupportBean;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_coin_extract_notice;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);

        mExtractNoticeTv.setText(mContext.getString(R.string.fee_range) + mCoinSupportBean.getMinWithdrawFee() + " - " + mCoinSupportBean.getMaxWithdrawFee() + " " + mCoinSupportBean.getCoinName());
    }
}
