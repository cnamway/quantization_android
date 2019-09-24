package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.core.BottomPopupView;
import com.spark.acclient.pojo.CoinWithdrawAddressResult;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.adapter.CoinWithDrawAddresssAdapter;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinWithdrawAddressPopup extends BottomPopupView {
    @BindView(R.id.rv_coin_withdraw)
    RecyclerView mRecyclerView;
    private OnEtContentListener mOnEtContentListener;
    private List<CoinWithdrawAddressResult.DataBean> dataBeanList;
    private CoinWithDrawAddresssAdapter mCoinWithDrawAddresssAdapter;
    private Context mContext;

    public CoinWithdrawAddressPopup(@NonNull Context context, List<CoinWithdrawAddressResult.DataBean> dataBeanList, OnEtContentListener onEtContentListener) {
        super(context);
        this.mContext = context;
        this.dataBeanList = dataBeanList;
        this.mOnEtContentListener = onEtContentListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_coin_withdraw_addresss;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mCoinWithDrawAddresssAdapter = new CoinWithDrawAddresssAdapter(dataBeanList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mCoinWithDrawAddresssAdapter);
        mCoinWithDrawAddresssAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mOnEtContentListener.onCEtContentInput(dataBeanList.get(position).getAddress());
                dismiss();
            }
        });
    }


    @OnClick({R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

}

