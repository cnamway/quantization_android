package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.flowlayout.FlowLayout;
import com.spark.qtz.ui.flowlayout.TagAdapter;
import com.spark.qtz.ui.flowlayout.TagFlowLayout;
import com.spark.qtz.ui.popup.impl.OnLcAdFilterListener;
import com.spark.otcclient.pojo.TradeAreaListResult;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LegalCurrencyFilterPopup extends PartShadowPopupView {
    @BindView(R.id.fl_region)
    TagFlowLayout mFlRegion;
    @BindView(R.id.fl_pay_type)
    TagFlowLayout mFlPayType;
    @BindView(R.id.minLimit)
    EditText mMinLimit;
    @BindView(R.id.maxLimit)
    EditText mMaxLimit;
    @BindView(R.id.cancel)
    TextView mCancel;
    @BindView(R.id.ensure)
    TextView mEnsure;
    //区域
    private String[] mRegion;
    //支付方式
    private String[] mPayType = new String[]{App.getInstance().getString(R.string.all), App.getInstance().getString(R.string.str_alipay), App.getInstance().getString(R.string.str_wechat), App.getInstance().getString(R.string.str_bank)};

    private TagAdapter mTagAdapterRegion, mTagAdapterPayType;
    private Context mContext;
    private Set<Integer> mSelectPosSet = new HashSet<>();
    private TradeAreaListResult mTradeAreaListResult;
    private OnLcAdFilterListener mOnLcAdFilterListener;
    private String payMode = "", country = "", minLimit = "", maxLimit = "";

    public LegalCurrencyFilterPopup(@NonNull Context context, TradeAreaListResult tradeAreaListResult, OnLcAdFilterListener onLcAdFilterListener) {
        super(context);
        this.mContext = context;
        this.mTradeAreaListResult = tradeAreaListResult;
        this.mOnLcAdFilterListener = onLcAdFilterListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_legal_currency_filter;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mRegion = new String[mTradeAreaListResult.getData().size() + 1];
        mRegion[0] = App.getInstance().getString(R.string.all);
        for (int i = 0; i < mTradeAreaListResult.getData().size(); i++) {
            mRegion[i + 1] = mTradeAreaListResult.getData().get(i).getZhName();
        }

        mTagAdapterRegion = new TagAdapter<String>(mRegion) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv,
                        mFlRegion, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlRegion.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (position == 0) {
                    country = "";
                } else {
                    country = mTradeAreaListResult.getData().get(position - 1).getEnName();
                }
                return true;
            }
        });
        mTagAdapterRegion.setSelectedList(0);
        mFlRegion.setAdapter(mTagAdapterRegion);

        mTagAdapterPayType = new TagAdapter<String>(mPayType) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv,
                        mFlPayType, false);
                tv.setText(s);
                return tv;
            }
        };
        mTagAdapterPayType.setSelectedList(0);
        mFlPayType.setAdapter(mTagAdapterPayType);

        mFlPayType.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.contains(0)) {
                    selectPosSet.remove(0);
                }
                mSelectPosSet.clear();
                mSelectPosSet.addAll(selectPosSet);
            }
        });

        mFlPayType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (position == 0) {
                    mTagAdapterPayType.setSelectedList(0);
                } else {
                    mTagAdapterPayType.setSelectedList(mSelectPosSet);
                }
                return true;
            }
        });
    }

    @OnClick({R.id.cancel,
            R.id.ensure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.ensure:
                payMode = "";
                if (mFlPayType.getSelectedList().contains(0)) {
                    payMode = "";
                } else {
                    StringBuffer buffer = new StringBuffer();
                    for (Integer integer : mFlPayType.getSelectedList()) {
                        if (integer == 1) {
                            buffer.append("alipay");
                        } else if (integer == 2) {
                            buffer.append(mFlPayType.getSelectedList().contains("1") ? ",wechat" : "wechat");
                        } else if (integer == 3) {
                            buffer.append((mFlPayType.getSelectedList().contains("1") || mFlPayType.getSelectedList().contains("2")) ? ",card" : "card");
                        }
                    }
                    payMode = buffer.toString();
                }

                LogUtils.e("payMode", payMode);
                if (TextUtils.isEmpty(mMinLimit.getText().toString().trim())) {
                    minLimit = "";
                } else {
                    minLimit = String.valueOf(Double.valueOf(mMinLimit.getText().toString().trim()));
                }

                if (TextUtils.isEmpty(mMaxLimit.getText().toString().trim())) {
                    maxLimit = "";
                } else {
                    maxLimit = String.valueOf(Double.valueOf(mMaxLimit.getText().toString().trim()));
                }
                mMinLimit.setText("");
                mMaxLimit.setText("");
                mOnLcAdFilterListener.onLcAdFilter(country, payMode, minLimit, maxLimit);
                dismiss();
                break;
        }
    }
}
