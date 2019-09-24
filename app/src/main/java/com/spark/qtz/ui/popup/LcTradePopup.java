package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.PointLengthFilter;
import com.spark.qtz.ui.popup.impl.OnOrderCreateListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.pojo.FindPageResult.DataBean.RecordsBean;
import com.spark.otcclient.pojo.OrderCreateBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SpanUtils;
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
public class LcTradePopup extends BottomPopupView {
    @BindView(R.id.coin_name)
    TextView mCoinName;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.buy_type)
    TextView mBuyType;
    @BindView(R.id.buy_type_root)
    LinearLayout mBuyTypeRoot;
    @BindView(R.id.number)
    EditText mNumber;
    @BindView(R.id.limit)
    TextView mLimit;
    @BindView(R.id.trade_volume)
    TextView mTradeVolume;
    @BindView(R.id.trade_num)
    TextView mTradeNum;
    @BindView(R.id.order)
    TextView mOrder;
    @BindView(R.id.cancel)
    TextView mCancel;
    @BindView(R.id.type_title)
    TextView mTypeTitle;
    @BindView(R.id.type_unit)
    TextView mTypeUnit;
    @BindView(R.id.ll_limit)
    LinearLayout mLlLimit;
    @BindView(R.id.tvTradeType)
    TextView tvTradeType;
    @BindView(R.id.trade_use)
    TextView mTradeUse;

    private Context mContext;
    private RecordsBean mRecordsBean;
    private PointLengthFilter mPointLengthFilter;
    private int buyType = 0;// 0 - 按价格购买  1 - 按数量购买
    private int mTradeType;// 1 - 买  0 - 卖
    private OnOrderCreateListener mOnOrderCreateListener;
    private double money, number;
    private List<SpotWalletResult.DataBean> walletList;
    private String[] typeArr = {App.getInstance().getString(R.string.str_money_buy),
            App.getInstance().getString(R.string.str_amount_buy)};

    public LcTradePopup(@NonNull Context context, List<SpotWalletResult.DataBean> walletList, int tradeType, RecordsBean recordsBean, OnOrderCreateListener onOrderCreateListener) {
        super(context);
        this.mContext = context;
        this.walletList = walletList;
        this.mTradeType = tradeType;
        this.mRecordsBean = recordsBean;
        this.mOnOrderCreateListener = onOrderCreateListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_lc_trade;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTradeType.setText(mTradeType == 1 ? BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_ad_buy_way) : BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_ad_sell_way));
        mCoinName.setText((mTradeType == 1 ? BaseApplication.getInstance().getString(me.spark.mvvm.R.string.buy2) : BaseApplication.getInstance().getString(me.spark.mvvm.R.string.sell2)) + mRecordsBean.getCoinName());
        mPrice.setText(new SpanUtils().append(BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_price))
                .append(String.valueOf(mRecordsBean.getPrice())).setForegroundColor(ContextCompat.getColor(mContext, R.color.green)).setFontSize(16, true)
                .append(" CNY").create());
        mBuyType.setText(mTradeType == 1 ? BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_money_buy) : BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_money_sell));
        mTypeTitle.setText(BaseApplication.getInstance().getString(me.spark.mvvm.R.string.price));
        mTypeUnit.setText("CNY");
        mLimit.setText(BaseApplication.getInstance().getString(me.spark.mvvm.R.string.limit) + MathUtils.getRundNumber(mRecordsBean.getMinLimit(), 2, null) + " - " + MathUtils.getRundNumber(mRecordsBean.getMaxLimit(), 2, null) + " CNY");
        mTradeVolume.setText(BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_trade_amount) + "-- CNY");
        mTradeNum.setText("" + "-- " + mRecordsBean.getCoinName());

        mPointLengthFilter = new PointLengthFilter(2);
        mNumber.setFilters(new InputFilter[]{mPointLengthFilter});

        mNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isEmpty(s.toString())) return;
                switch (buyType) {
                    //按价格购买
                    case 0:
                        money = Double.valueOf(MathUtils.getRundNumber(Double.valueOf(s.toString()), 2, null));
                        number = Double.valueOf(MathUtils.getRundNumber(Double.valueOf(s.toString()) / mRecordsBean.getPrice(), 8, null));
                        break;
                    //按数量购买
                    case 1:
                        money = Double.valueOf(MathUtils.getRundNumber(Double.valueOf(s.toString()) * mRecordsBean.getPrice(), 2, null));
                        number = Double.valueOf(MathUtils.getRundNumber(Double.valueOf(s.toString()), 8, null));
                        break;
                }
                mTradeVolume.setText(MathUtils.getRundNumber(money, 2, null) + " CNY");
                mTradeNum.setText(BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_trade_amount) + MathUtils.getRundNumber(number, 8, null) + mRecordsBean.getCoinName());
            }
        });

        getUseMoney();
    }

    private void getUseMoney() {
        for (SpotWalletResult.DataBean dataBean : walletList) {
            if (mRecordsBean.getCoinName().equals(dataBean.getCoinId())) {
                mTradeUse.setText(App.getInstance().getApplicationContext().getString(R.string.str_coin_can_banlance) + MathUtils.getRundNumber(dataBean.getBalance(), 8, null) + mRecordsBean.getCoinName());
            }
        }
    }

    @OnClick({R.id.buy_type_root,
            R.id.order,
            R.id.cancel})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.buy_type_root:
                new XPopup.Builder(getContext())
                        .atView(view)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(typeArr, null,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        mBuyType.setText(text);
                                        buyType = position;
                                        switch (position) {
                                            case 0:
                                                mTypeTitle.setText(App.getInstance().getApplicationContext().getString(R.string.price));
                                                mTypeUnit.setText("CNY");
                                                mPointLengthFilter = new PointLengthFilter(2);
                                                mNumber.setFilters(new InputFilter[]{mPointLengthFilter});
                                                mLlLimit.setVisibility(VISIBLE);
                                                break;
                                            case 1:
                                                mTypeTitle.setText(App.getInstance().getApplicationContext().getString(R.string.number));
                                                mTypeUnit.setText(mRecordsBean.getCoinName());
                                                mPointLengthFilter = new PointLengthFilter(8);
                                                mNumber.setFilters(new InputFilter[]{mPointLengthFilter});
                                                mLlLimit.setVisibility(GONE);
                                                break;
                                        }
                                        mNumber.setText("");
                                        mTradeVolume.setText("-- CNY");
                                        mTradeNum.setText(BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_trade_amount) + " -- " + mRecordsBean.getCoinName());
                                        requstFocus();
                                    }
                                })
                        .show();
                break;
            case R.id.order:
                if (TextUtils.isEmpty(mNumber.getText().toString().trim())) {
                    Toasty.showError(buyType == 0 ? BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_enter_money) : BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_enter_number));
                    return;
                }

                double max = mRecordsBean.getMaxLimit();
                double min = mRecordsBean.getMinLimit();

                if (money > max || money < min) {
                    if (mTradeType == 0) {
                        Toasty.showError(BaseApplication.getInstance().getString(me.spark.mvvm.R.string.sell2) + " >= " + min + BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_and) + " <= " + max);
                        return;
                    } else {
                        Toasty.showError(BaseApplication.getInstance().getString(me.spark.mvvm.R.string.buy2) + " >= " + min + BaseApplication.getInstance().getString(me.spark.mvvm.R.string.str_and) + " <= " + max);
                        return;
                    }
                }

                OrderCreateBean orderCreateBean = new OrderCreateBean();
                orderCreateBean.setAdvertiseId(mRecordsBean.getId());
                orderCreateBean.setMoney(money);
                orderCreateBean.setNumber(number);
                orderCreateBean.setRemark(mRecordsBean.getRemark());
                orderCreateBean.setOrderType(mTradeType == 0 ? "1" : "0");
                orderCreateBean.setPrice(mRecordsBean.getPrice());
                if (mTradeType == 1) {
                    orderCreateBean.setTradePwd("");
                }
                /* 1按数量购买  2按金额购买 */
                orderCreateBean.setTradeType(buyType);

                mOnOrderCreateListener.onOrderCreate(orderCreateBean);
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private void requstFocus() {
        mNumber.setFocusable(true);
        mNumber.requestFocus();
        InputMethodManager mSoftInputManager = (InputMethodManager) mNumber.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mSoftInputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }
}
