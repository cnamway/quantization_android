package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.core.AttachPopupView;
import com.spark.acclient.pojo.CoinWithdrawAddressResult;
import com.spark.qtz.R;
import com.spark.qtz.app.quotes.adapter.IDAdapter;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class IDPopup extends AttachPopupView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private OnEtContentListener mOnEtContentListener;
    private List<CoinWithdrawAddressResult.DataBean> dataBeanList;
    private IDAdapter mAdapter;
    private Context mContext;
    private String ids;

    public IDPopup(@NonNull Context context, String ids, List<CoinWithdrawAddressResult.DataBean> dataBeanList, OnEtContentListener onEtContentListener) {
        super(context);
        this.mContext = context;
        this.ids = ids;
        this.dataBeanList = dataBeanList;
        this.mOnEtContentListener = onEtContentListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_id;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mAdapter = new IDAdapter(dataBeanList, ids);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mOnEtContentListener.onCEtContentInput(dataBeanList.get(position).getCoinId());
                dismiss();
            }
        });
    }

    public void setIDs(String ids) {
        this.ids = ids;
        mAdapter = new IDAdapter(dataBeanList, ids);
        mAdapter.notifyDataSetChanged();
    }

}

