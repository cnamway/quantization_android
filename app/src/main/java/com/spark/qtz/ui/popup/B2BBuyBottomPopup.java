package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.R;
import com.spark.modulespot.SpotCoinClient;
import com.spark.modulespot.pojo.PlaceOrderBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class B2BBuyBottomPopup extends BottomPopupView {
    @BindView(R.id.tv_popup_title)
    TextView tv_popup_title;
    @BindView(R.id.tv_popup_type)
    TextView tv_popup_type;
    @BindView(R.id.tv_popup_price)
    TextView tv_popup_price;
    @BindView(R.id.tv_popup_num)
    TextView tv_popup_num;
    @BindView(R.id.tv_popup_amount)
    TextView tv_popup_amount;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;

    private Context mContext;
    private int type;
    private String tradePrice;
    private PlaceOrderBean mPlaceOrderBean;

    public B2BBuyBottomPopup(@NonNull Context context, int type, String tradePrice, PlaceOrderBean placeOrderBean) {
        super(context);
        this.mContext = context;
        this.type = type;
        this.tradePrice = tradePrice;
        this.mPlaceOrderBean = placeOrderBean;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_b2b_buy_botttom;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String numCoin = "";
        String priceCoin = "";
        if (StringUtils.isNotEmpty(mPlaceOrderBean.getSymbol()) && mPlaceOrderBean.getSymbol().contains("/")) {
            String[] strings = mPlaceOrderBean.getSymbol().split("/");
            if (strings.length > 1) {
                numCoin = strings[0];
                priceCoin = strings[1];
            }
        }

        String strType;
        if (type == 0) {
            strType = mContext.getString(R.string.buy);
            tv_confirm.setText(mContext.getString(R.string.buy_ensure));
            tv_confirm.setBackground(getResources().getDrawable(R.drawable.green_4dp_bg));
        } else {
            strType = mContext.getString(R.string.sell);
            tv_confirm.setText(mContext.getString(R.string.sell));
            tv_confirm.setBackground(getResources().getDrawable(R.drawable.orange_4dp_bg));
        }

        String strPriceType;
        if (mPlaceOrderBean.getPriceType() == 0) {
            strPriceType = mContext.getString(R.string.market_price);
        } else {
            strPriceType = mContext.getString(R.string.limit_price);
        }

        tv_popup_title.setText(strType + numCoin);
        tv_popup_type.setText(strPriceType + strType);
        tv_popup_price.setText(mPlaceOrderBean.getPriceType() == 0 ? mContext.getString(R.string.market_price) : (mPlaceOrderBean.getPrice() + " " + priceCoin));
        tv_popup_num.setText(mPlaceOrderBean.getOrderQty() + " " + ((mPlaceOrderBean.getPriceType() == 0 && type == 0) ? priceCoin : numCoin));
        tv_popup_amount.setText(mPlaceOrderBean.getPriceType() == 0 ? "--" : (tradePrice + " " + priceCoin));
    }

    @OnClick({R.id.tv_confirm,
            R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                SpotCoinClient.getInstance().placeOrder(mPlaceOrderBean);
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
