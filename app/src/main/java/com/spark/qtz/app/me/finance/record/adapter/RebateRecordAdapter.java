package com.spark.qtz.app.me.finance.record.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.qtz.R;
import com.spark.ucclient.pojo.CommissionRecordResult;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/1
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RebateRecordAdapter extends BaseQuickAdapter<CommissionRecordResult.DataBean.RecordsBean, RebateRecordAdapter.RebateRecordViewHolder> {
    public RebateRecordAdapter(@Nullable List<CommissionRecordResult.DataBean.RecordsBean> data) {
        super(R.layout.item_rebate_record, data);
    }

    @Override
    protected void convert(RebateRecordViewHolder helper, CommissionRecordResult.DataBean.RecordsBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.rebateRecordVM, item);
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

    public static class RebateRecordViewHolder extends BaseViewHolder {

        public RebateRecordViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
