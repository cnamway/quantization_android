package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.core.BottomPopupView;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.adapter.ChooseCoinAdapter;
import com.spark.qtz.ui.popup.impl.OnCoinChooseListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinChoosePopup extends BottomPopupView {
    @BindView(R.id.rv_choose_coin)
    RecyclerView mRvChooseCoin;
    @BindView(R.id.choose_coin_order)
    TextView mChooseCoinOrder;
    private OnCoinChooseListener mOnChooseCoinListener;
    private List<CoinSupportBean.DataBean> dataBeanList;
    private ChooseCoinAdapter mChooseCoinAdapter;
    private Context mContext;
    private boolean mIsShowRecord = true;

    public CoinChoosePopup(@NonNull Context context, List<CoinSupportBean.DataBean> dataBeanList, OnCoinChooseListener mOnChooseCoinListener) {
        super(context);
        this.mContext = context;
        this.dataBeanList = dataBeanList;
        this.mOnChooseCoinListener = mOnChooseCoinListener;
    }

    public CoinChoosePopup(@NonNull Context context, List<CoinSupportBean.DataBean> dataBeanList, boolean isShowRecord, OnCoinChooseListener mOnChooseCoinListener) {
        super(context);
        this.mContext = context;
        this.dataBeanList = dataBeanList;
        this.mIsShowRecord = isShowRecord;
        this.mOnChooseCoinListener = mOnChooseCoinListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_choose_coin;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        try {
            mChooseCoinOrder.setVisibility(mIsShowRecord ? View.VISIBLE : View.GONE);
            mChooseCoinAdapter = new ChooseCoinAdapter(dataBeanList);
            mRvChooseCoin.setLayoutManager(new LinearLayoutManager(mContext));
            mRvChooseCoin.setAdapter(mChooseCoinAdapter);
            mChooseCoinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    mOnChooseCoinListener.onClickItem(position);
                    dismiss();
                }
            });
        } catch (Exception e) {
            LogUtils.e(e.toString());
            dismiss();
        }
    }


    @OnClick({R.id.choose_coin_order,
            R.id.choose_coin_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_coin_order:
                mOnChooseCoinListener.onClickOrder();
                dismiss();
                break;
            case R.id.choose_coin_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

}

