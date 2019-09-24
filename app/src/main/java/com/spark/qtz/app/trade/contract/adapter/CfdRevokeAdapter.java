package com.spark.qtz.app.trade.contract.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.modulecfd.pojo.CfdRevokeResult;
import com.spark.qtz.R;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/7
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdRevokeAdapter extends BaseQuickAdapter<CfdRevokeResult.DataBean.RecordsBean, CfdRevokeAdapter.CfdCfdRevokeViewHolder> {
    public CfdRevokeAdapter(@Nullable List<CfdRevokeResult.DataBean.RecordsBean> data) {
        super(R.layout.item_cfd_revoke, data);
    }

    @Override
    protected void convert(CfdCfdRevokeViewHolder helper, CfdRevokeResult.DataBean.RecordsBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.itemCfdRevokeViewModel, item);
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

    public static class CfdCfdRevokeViewHolder extends BaseViewHolder {

        public CfdCfdRevokeViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
