package com.spark.qtz.ui.popup.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.acclient.pojo.CoinWithdrawAddressResult;
import com.spark.qtz.R;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinWithDrawAddresssAdapter extends BaseQuickAdapter<CoinWithdrawAddressResult.DataBean, CoinWithDrawAddresssAdapter.CoinWithDrawViewHolder> {
    public CoinWithDrawAddresssAdapter(@Nullable List<CoinWithdrawAddressResult.DataBean> data) {
        super(R.layout.item_coin_withdraw, data);
    }

    @Override
    protected void convert(CoinWithDrawViewHolder helper, CoinWithdrawAddressResult.DataBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.coinWithdrawAddress, item);
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

    public static class CoinWithDrawViewHolder extends BaseViewHolder {

        public CoinWithDrawViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
