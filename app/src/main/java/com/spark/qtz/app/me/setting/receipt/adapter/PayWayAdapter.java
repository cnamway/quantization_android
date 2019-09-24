package com.spark.qtz.app.me.setting.receipt.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.SmoothCheckBox;
import com.spark.otcclient.pojo.PayListBean;

import java.util.List;

import me.spark.mvvm.base.Constant;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/7/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PayWayAdapter extends BaseQuickAdapter<PayListBean.DataBean, PayWayAdapter.PayWayViewHolder> {
    private OnSelectListener mOnSelectListener;

    public interface OnSelectListener {
        void onSelect(PayListBean.DataBean dataBean);
    }

    //设置监听
    /*public void setOnSelectListener(OnSelectListener listener) {
        this.mOnSelectListener = listener;
    }*/

    public PayWayAdapter(@Nullable List<PayListBean.DataBean> data) {
        super(R.layout.item_pay_way, data);
    }

    @Override
    protected void convert(final PayWayViewHolder helper, final PayListBean.DataBean item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.paywayBean, item);
        binding.executePendingBindings();

       /* helper.getView(R.id.pay_way_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //观察选中项
                mOnSelectListener.onSelect(item);
            }
        });*/

        switch (item.getPayType()) {
            case Constant.alipay:
                helper.setImageResource(R.id.ivType, R.drawable.svg_alipay);
                helper.setText(R.id.tvBankName, App.getInstance().getString(R.string.str_alipay));
                break;
            case Constant.wechat:
                helper.setImageResource(R.id.ivType, R.drawable.svg_wechatpay);
                helper.setText(R.id.tvBankName, App.getInstance().getString(R.string.str_wechat));
                break;
            case Constant.card:
                helper.setImageResource(R.id.ivType, R.drawable.svg_bankpay);
                helper.setText(R.id.tvBankName, item.getBank());
                break;
            case Constant.PAYPAL:
                helper.setImageResource(R.id.ivType, R.drawable.svg_pay_paypal);
                helper.setText(R.id.tvBankName, App.getInstance().getString(R.string.str_paypal));
                break;
            case Constant.other:
                helper.setImageResource(R.id.ivType, R.drawable.svg_pay_other);
                helper.setText(R.id.tvBankName, App.getInstance().getString(R.string.str_other));
                break;
            default:
                helper.setImageResource(R.id.ivType, R.drawable.svg_pay_other);
                helper.setText(R.id.tvBankName, App.getInstance().getString(R.string.str_other));
                break;
        }

        if (item.getStatus() == 1) {
            helper.getView(R.id.ivStatus).setSelected(true);
        } else {
            helper.getView(R.id.ivStatus).setSelected(false);
        }

        helper.addOnClickListener(R.id.ivStatus);

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

    public static class PayWayViewHolder extends BaseViewHolder {

        public PayWayViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }

    public void update() {
        notifyDataSetChanged();
    }
}
