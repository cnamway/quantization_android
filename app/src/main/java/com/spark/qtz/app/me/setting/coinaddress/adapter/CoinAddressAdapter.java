package com.spark.qtz.app.me.setting.coinaddress.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.acclient.pojo.CoinAddressListBean;
import com.spark.qtz.R;
import com.spark.qtz.ui.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/22
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinAddressAdapter extends BaseQuickAdapter<CoinAddressListBean.DataBean, CoinAddressAdapter.CoinAddressViewHolder> {
    //选择List
    private List<CoinAddressListBean.DataBean> mSelectList = new ArrayList<>();
    private OnSelectListener mOnSelectListener;

    public interface OnSelectListener {
        void onSelect(List<CoinAddressListBean.DataBean> mSelectList);
    }

    //设置监听
    public void setOnSelectListener(OnSelectListener listener) {
        this.mOnSelectListener = listener;
    }


    public CoinAddressAdapter(@Nullable List<CoinAddressListBean.DataBean> data) {
        super(R.layout.item_coin_address, data);
    }

    @Override
    protected void convert(final CoinAddressViewHolder helper, final CoinAddressListBean.DataBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.coinAddressBean, item);
        binding.executePendingBindings();

        ((SmoothCheckBox) helper.getView(R.id.coin_address_select)).setChecked(false);
        helper.getView(R.id.coin_address_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SmoothCheckBox) helper.getView(R.id.coin_address_select)).setChecked(!((SmoothCheckBox) helper.getView(R.id.coin_address_select)).isChecked());
            }
        });

        ((SmoothCheckBox) helper.getView(R.id.coin_address_select)).setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    mSelectList.add(item);
                } else {
                    mSelectList.remove(item);
                }
                //观察选中项
                mOnSelectListener.onSelect(mSelectList);
            }
        });
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

    public static class CoinAddressViewHolder extends BaseViewHolder {

        public CoinAddressViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }

    public void update() {
        mSelectList.clear();
        notifyDataSetChanged();
    }
}
