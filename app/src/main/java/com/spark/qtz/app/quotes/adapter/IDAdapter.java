package com.spark.qtz.app.quotes.adapter;

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
public class IDAdapter extends BaseQuickAdapter<CoinWithdrawAddressResult.DataBean, IDAdapter.CoinWithDrawViewHolder> {
    private String ids;

    public IDAdapter(@Nullable List<CoinWithdrawAddressResult.DataBean> data, String ids) {
        super(R.layout.item_id, data);
        this.ids = ids;
    }

    @Override
    protected void convert(CoinWithDrawViewHolder helper, CoinWithdrawAddressResult.DataBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.dataBeanResult, item);
        binding.executePendingBindings();
        if (ids.equals(item.getCoinId())) {
            helper.setVisible(R.id.ivTag, true);
        } else {
            helper.setVisible(R.id.ivTag, false);
        }
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
