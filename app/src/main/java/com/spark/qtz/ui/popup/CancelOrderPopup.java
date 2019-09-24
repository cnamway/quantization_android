package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.modulespot.SpotCoinClient;
import com.spark.modulespot.pojo.OpenOrdersResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class CancelOrderPopup extends BottomPopupView {
    @BindView(R.id.tv_popup_type)
    TextView mTvPopupType;
    @BindView(R.id.tv_popup_price)
    TextView mTvPopupPrice;
    @BindView(R.id.tv_popup_num)
    TextView mTvPopupNum;
    @BindView(R.id.tv_popup_amount)
    TextView mTvPopupAmount;
    private Context mContext;

    private OpenOrdersResult.DataBean.ListBean listBean;


    public CancelOrderPopup(@NonNull Context context, OpenOrdersResult.DataBean.ListBean listBean) {
        super(context);
        this.mContext = context;
        this.listBean = listBean;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_cancel_order;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvPopupType.setText(listBean.getSide() == 0 ? App.getInstance().getString(R.string.buy) : App.getInstance().getString(R.string.sell));
        mTvPopupPrice.setText(DfUtils.numberFormat(listBean.getPrice(), 8) + " " + listBean.getSymbol().split("/")[1]);
        mTvPopupNum.setText(DfUtils.numberFormat(listBean.getOrderQty(), 8) + " " + listBean.getSymbol().split("/")[0]);
        mTvPopupAmount.setText(DfUtils.numberFormat(listBean.getTradedAmount(), 8) + " " + listBean.getSymbol().split("/")[1]);
    }

    @OnClick({R.id.tv_confirm,
            R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                SpotCoinClient.getInstance().cancelOrder(listBean.getOrderId());
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
