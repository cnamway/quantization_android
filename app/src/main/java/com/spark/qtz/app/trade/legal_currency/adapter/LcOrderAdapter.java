package com.spark.qtz.app.trade.legal_currency.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.qtz.R;
import com.spark.otcclient.pojo.LcOrderResult;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcOrderAdapter extends BaseQuickAdapter<LcOrderResult.DataBean.RecordsBean, LcOrderAdapter.LcOrderViewHolder> {
    public LcOrderAdapter(@Nullable List<LcOrderResult.DataBean.RecordsBean> data) {
        super(R.layout.item_lc_order, data);
    }

    @Override
    protected void convert(LcOrderViewHolder helper, LcOrderResult.DataBean.RecordsBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.lcOrderViewModel, item);
        binding.executePendingBindings();
    }


    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    public static class LcOrderViewHolder extends BaseViewHolder {

        public LcOrderViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
