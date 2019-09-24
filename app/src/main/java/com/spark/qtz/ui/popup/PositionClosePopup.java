package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.modulecfd.CfdClient;
import com.example.modulecfd.pojo.CfdCommissionResult;
import com.example.modulecfd.pojo.CfdOrderPostBean;
import com.example.modulecfd.pojo.CfdPositionResult;
import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.SpanUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PositionClosePopup extends BottomPopupView {

    @BindView(R.id.order_id)
    TextView mOrderId;
    @BindView(R.id.details)
    TextView mDetails;
    @BindView(R.id.fee)
    TextView mFee;
    @BindView(R.id.confirm)
    TextView mConfirm;
    @BindView(R.id.cancel)
    TextView mCancel;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.ll_fee)
    LinearLayout mLlFee;

    private Context mContext;
    private CfdPositionResult.DataBean mDataBean;
    private CfdCommissionResult.DataBean.RecordsBean mRecordsBean;

    private int mType;//0 - 平仓  1 - 撤单
    private double fee;

    public PositionClosePopup(@NonNull Context context, int type, CfdPositionResult.DataBean dataBean) {
        super(context);
        this.mType = type;
        this.mContext = context;
        this.mDataBean = dataBean;
    }

    public PositionClosePopup(@NonNull Context context, int type, CfdCommissionResult.DataBean.RecordsBean recordsBean) {
        super(context);
        this.mType = type;
        this.mContext = context;
        this.mRecordsBean = recordsBean;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_postion_close;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        mTitle.setText(mType == 0 ? mContext.getString(R.string.ensure) + mContext.getString(R.string.position_close) : mContext.getString(R.string.ensure) + mContext.getString(R.string.withdrawal));

        mOrderId.setText(mType == 0 ? mDataBean.getOrderId() : mRecordsBean.getOrderId());
        if (mType == 0) {
            CharSequence text = new SpanUtils()
                    .append(mDataBean.getSymbol())
                    .append(":")
                    .append(mDataBean.getSide() == 0 ? mContext.getString(R.string.bullish) : mContext.getString(R.string.bearish))
                    .setForegroundColor(mDataBean.getSide() == 0 ? ContextCompat.getColor(mContext, R.color.green) : ContextCompat.getColor(mContext, R.color.orange))
                    .append("X")
                    .append(String.valueOf(mDataBean.getVolume()))
                    .append(mContext.getString(R.string.hands))
                    .create();
            mDetails.setText(text);
        } else {
            CharSequence text = new SpanUtils()
                    .append(mRecordsBean.getSymbol())
                    .append(":")
                    .append(mRecordsBean.getSide() == 0 ? mContext.getString(R.string.bullish) : mContext.getString(R.string.bearish))
                    .setForegroundColor(mRecordsBean.getSide() == 0 ? ContextCompat.getColor(mContext, R.color.green) : ContextCompat.getColor(mContext, R.color.orange))
                    .append("X")
                    .append(String.valueOf(mRecordsBean.getVolume()))
                    .append(mContext.getString(R.string.hands))
                    .create();
            mDetails.setText(text);
        }
        if (mType == 0) {
            mLlFee.setVisibility(View.VISIBLE);
            fee = mDataBean.getOpenPrice() * mDataBean.getVolume() * mDataBean.getMultiplier() * mDataBean.getCloseFee();
            mFee.setText(DfUtils.numberFormat(fee, 4));
        } else {
            mLlFee.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.confirm,
            R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                CfdOrderPostBean cfdOrderPostBean = new CfdOrderPostBean();
                if (mType == 0) {
                    cfdOrderPostBean.setCloseType(2);
                    cfdOrderPostBean.setId(mDataBean.getId());
                    CfdClient.getInstance().orderClose(cfdOrderPostBean);
                } else {
                    cfdOrderPostBean.setId(mRecordsBean.getOrderId());
                    CfdClient.getInstance().orderCancel(cfdOrderPostBean);
                }

                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
