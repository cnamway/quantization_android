package com.spark.qtz.app.trade.currency.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.qtz.R;
import com.spark.modulespot.pojo.MarketSymbolResult;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MarketBuyAdapter extends BaseQuickAdapter<MarketSymbolResult.DataBean.BidBean, MarketBuyAdapter.MarketBuyViewHolder> {
    public MarketBuyAdapter(@Nullable List<MarketSymbolResult.DataBean.BidBean> data) {
        super(R.layout.item_market_buy, data);
    }

    @Override
    protected void convert(MarketBuyAdapter.MarketBuyViewHolder helper, MarketSymbolResult.DataBean.BidBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.marketBuyViewModel, item);
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

    public static class MarketBuyViewHolder extends BaseViewHolder {

        public MarketBuyViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}