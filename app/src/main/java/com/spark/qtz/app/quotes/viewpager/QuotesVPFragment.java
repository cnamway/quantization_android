package com.spark.qtz.app.quotes.viewpager;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.api.pojo.CoinPairPushBean;
import com.spark.qtz.app.quotes.adapter.QuotesVPAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentQuotesVpBinding;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QuotesVPFragment extends BaseFragment<FragmentQuotesVpBinding, QuotesVPViewModel> {
    private static final String TYPE = "type";
    private List<CoinPairPushBean> mCoinPairPushBeanList = new ArrayList<>();
    private QuotesVPAdapter mQuotesVPAdapter;

    public static QuotesVPFragment newInstance(String quotesType) {
        QuotesVPFragment quotesVPFragment = new QuotesVPFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, quotesType);
        quotesVPFragment.setArguments(bundle);
        return quotesVPFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_quotes_vp;
    }

    @Override
    public int initVariableId() {
        return BR.quotesVPViewModel;
    }

    @Override
    public void initView() {
        //type注入vm
        viewModel.initType(getArguments().getString(TYPE));
        mQuotesVPAdapter = new QuotesVPAdapter(mCoinPairPushBeanList);
        binding.quotesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.quotesRv.setAdapter(mQuotesVPAdapter);
        mQuotesVPAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_QUOTES_KLINE)
                        .withParcelable("quotesVPClick", mCoinPairPushBeanList.get(position))
                        .navigation();
            }
        });
    }

    @Override
    public void initViewObservable() {
        //监听websocket数据
        viewModel.mUIChangeObservable.mCoinPairPushBean.observe(this, new Observer<CoinPairPushBean>() {
            @Override
            public void onChanged(@Nullable CoinPairPushBean coinPairPushBean) {
                if (!isVisibleToUser) return;
                if (mCoinPairPushBeanList.contains(coinPairPushBean)) {
                    LogUtils.e("update", getArguments().getString(TYPE));
                    mCoinPairPushBeanList.set(mCoinPairPushBeanList.indexOf(coinPairPushBean), coinPairPushBean);
                } else {
                    LogUtils.e("add");
                    mCoinPairPushBeanList.add(coinPairPushBean);
                }
                mQuotesVPAdapter.notifyDataSetChanged();
            }
        });
    }
}
