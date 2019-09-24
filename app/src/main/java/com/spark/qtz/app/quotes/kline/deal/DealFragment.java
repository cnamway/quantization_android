package com.spark.qtz.app.quotes.kline.deal;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.api.pojo.DealBean;
import com.spark.qtz.app.quotes.kline.adapter.DealAdapter;
import com.spark.qtz.databinding.FragmentDealBinding;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.modulespot.pojo.DealResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/30
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DealFragment extends BaseFragment<FragmentDealBinding, DealViewModel> {
    private AllThumbResult.DataBean allThumbResult;
    private DealAdapter mDealAdapter;
    private List<DealResult.DataBean> mDataBeanList = new ArrayList<>();
    private final int defaultSize = 20;

    public static DealFragment newInstance(AllThumbResult.DataBean allThumbResult) {
        DealFragment dealFragment = new DealFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", allThumbResult);
        dealFragment.setArguments(bundle);
        return dealFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_deal;
    }

    @Override
    public int initVariableId() {
        return BR.dealViewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.mDealResultSingleLiveEvent.observe(this, new Observer<DealBean>() {
            @Override
            public void onChanged(@Nullable DealBean dealBean) {
                if (mDealAdapter == null || dealBean.getDate().size() == 0) return;
                if (!dealBean.getDate().get(0).getSymbol().equals(allThumbResult.getSymbol()))
                    return;
                List<DealResult.DataBean> mTransList = new ArrayList<>();
                for (DealBean.DateBean dateBean : dealBean.getDate()) {
                    DealResult.DataBean listBean = new DealResult.DataBean();
                    listBean.setAmount(dateBean.getAmount());
                    listBean.setBuyOrderId(dateBean.getBuyOrderId());
                    listBean.setBuyTurnover(dateBean.getBuyTurnover());
                    listBean.setCompletedTime(dateBean.getCompletedTime());
                    listBean.setPrice(dateBean.getPrice());
                    listBean.setSellOrderId(dateBean.getSellOrderId());
                    listBean.setSellTurnover(dateBean.getSellTurnover());
                    listBean.setSide(dateBean.getSide());
                    listBean.setSymbol(dateBean.getSymbol());
                    mTransList.add(listBean);
                }
                Collections.reverse(mTransList);
                Collections.reverse(mDataBeanList);
                for (DealResult.DataBean dataBean : mTransList) {
                    mDataBeanList.add(dataBean);
                }
                for (int i = 0; i < mTransList.size(); i++) {
                    mDataBeanList.remove(i);
                }
                Collections.reverse(mDataBeanList);
                mDealAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void loadLazyData() {
        binding.root.showLoading();
        allThumbResult = getArguments().getParcelable("key");
        mDealAdapter = new DealAdapter(mDataBeanList);
        binding.rvDeal.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        binding.rvDeal.setAdapter(mDealAdapter);

        //默认配置20个数据
        for (int i = 0; i < defaultSize; i++) {
            DealResult.DataBean dataBean = new DealResult.DataBean();
            mDataBeanList.add(dataBean);
        }

        viewModel.initThumb(allThumbResult, new OnRequestListener<DealResult>() {
            @Override
            public void onSuccess(DealResult dealResult) {
                LogUtils.json("mDataBeanList", App.gson.toJson(mDataBeanList));
                mDataBeanList.clear();
                mDataBeanList.addAll(dealResult.getData());
                mDealAdapter.notifyDataSetChanged();
                if (binding.root.isLoadingCurrentState()) {
                    binding.root.showContent();
                }
            }

            @Override
            public void onFail(String message) {
                LogUtils.json("onFail", message);
                mDealAdapter.notifyDataSetChanged();
                if (binding.root.isLoadingCurrentState()) {
                    binding.root.showContent();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtils.unRegister(this);
    }
}
