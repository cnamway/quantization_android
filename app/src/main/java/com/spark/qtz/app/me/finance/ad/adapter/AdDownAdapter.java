package com.spark.qtz.app.me.finance.ad.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.qtz.R;
import com.spark.otcclient.pojo.AdSelfDownFindResult;

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
public class AdDownAdapter extends BaseQuickAdapter<AdSelfDownFindResult.DataBean.RecordsBean, AdDownAdapter.AdDownViewHolder> {
    public AdDownAdapter(@Nullable List<AdSelfDownFindResult.DataBean.RecordsBean> data) {
        super(R.layout.item_ad_down, data);
    }

    @Override
    protected void convert(AdDownViewHolder helper, AdSelfDownFindResult.DataBean.RecordsBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.adDownResult, item);
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

    public static class AdDownViewHolder extends BaseViewHolder {

        public AdDownViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
