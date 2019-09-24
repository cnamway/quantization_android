package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.flowlayout.FlowLayout;
import com.spark.qtz.ui.flowlayout.TagAdapter;
import com.spark.qtz.ui.flowlayout.TagFlowLayout;
import com.spark.qtz.ui.popup.impl.OnLcFilterListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.base.Constant;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcOrderFilterPopup extends PartShadowPopupView {
    @BindView(R.id.fl_ad_type)
    TagFlowLayout mFlAdType;
    @BindView(R.id.fl_status)
    TagFlowLayout mFlStatus;
    @BindView(R.id.fl_coin_name)
    TagFlowLayout mFlCoinName;
    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;
    @BindView(R.id.btn_ensure)
    TextView mBtnEnsure;

    private String all = App.getInstance().getString(R.string.all);
    private String sell = App.getInstance().getString(R.string.sell2);
    private String buy = App.getInstance().getString(R.string.buy2);

    private String str_un_pay = App.getInstance().getString(R.string.str_un_pay);
    private String str_paid = App.getInstance().getString(R.string.str_paid);
    private String completed = App.getInstance().getString(R.string.completed);
    private String cancelled = App.getInstance().getString(R.string.cancelled);
    private String str_order_appealing = App.getInstance().getString(R.string.str_order_appealing);

    //交易类型
    private String[] mAdtype = new String[]{all, sell, buy};
    //订单状态
    private String[] mStatus = new String[]{all, str_un_pay, str_paid, completed, cancelled, str_order_appealing};
    //
    private String[] mCoinName = Constant.lcCoinNameArray;

    private TagAdapter mTagAdapterAdType, mTagAdapterStatus, mTagAdapterCoinName;
    private Context mContext;
    private OnLcFilterListener mOnLcFilterListener;
    private String selectAdtype = mAdtype[0], selectStatus = mStatus[0], selectCoinName;

    public LcOrderFilterPopup(@NonNull Context context, OnLcFilterListener onLcFilterListener) {
        super(context);
        this.mContext = context;
        this.mOnLcFilterListener = onLcFilterListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_lc_order_filter;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mCoinName = new String[Constant.lcCoinNameArray.length + 1];
        mCoinName[0] = App.getInstance().getString(R.string.all);
        for (int i = 0; i < Constant.lcCoinNameArray.length; i++) {
            mCoinName[i + 1] = Constant.lcCoinNameArray[i];
        }
        selectCoinName = mCoinName[0];

        mTagAdapterAdType = new TagAdapter<String>(mAdtype) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv,
                        mFlAdType, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlAdType.setAdapter(mTagAdapterAdType);
        mFlAdType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                selectAdtype = mAdtype[position];
                return false;
            }
        });
        mTagAdapterAdType.setSelectedList(0);


        mTagAdapterStatus = new TagAdapter<String>(mStatus) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv,
                        mFlStatus, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlStatus.setAdapter(mTagAdapterStatus);
        mFlStatus.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                selectStatus = mStatus[position];
                return false;
            }
        });
        mTagAdapterStatus.setSelectedList(0);

        mTagAdapterCoinName = new TagAdapter<String>(mCoinName) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv,
                        mFlCoinName, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlCoinName.setAdapter(mTagAdapterCoinName);
        mFlCoinName.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                selectCoinName = mCoinName[position];
                return false;
            }
        });
        mTagAdapterCoinName.setSelectedList(0);
    }

    @OnClick({R.id.btn_cancel,
            R.id.btn_ensure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ensure:
                String adType = "", status = "", coinName = "";
                if (selectAdtype.equals(all)) {
                    adType = "";
                } else if (selectAdtype.equals(sell)) {
                    adType = "1";
                } else if (selectAdtype.equals(buy)) {
                    adType = "0";
                }
                if (selectStatus.equals(all)) {
                    status = "-1";
                } else if (selectStatus.equals(str_un_pay)) {
                    status = "1";
                } else if (selectStatus.equals(str_paid)) {
                    status = "2";
                } else if (selectStatus.equals(completed)) {
                    status = "3";
                } else if (selectStatus.equals(cancelled)) {
                    status = "0";
                } else if (selectStatus.equals(str_order_appealing)) {
                    status = "4";
                }
                coinName = selectCoinName.equals(App.getInstance().getString(R.string.all)) ? "" : selectCoinName;
                mOnLcFilterListener.onLcSelect(adType, status, coinName);
                dismiss();
                break;
        }
    }
}
