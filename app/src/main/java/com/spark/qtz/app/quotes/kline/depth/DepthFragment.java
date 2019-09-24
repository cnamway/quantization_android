package com.spark.qtz.app.quotes.kline.depth;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.quotes.kline.adapter.DepthBuyAdapter;
import com.spark.qtz.app.quotes.kline.adapter.DepthSellAdapter;
import com.spark.qtz.databinding.FragmentDepthBinding;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.modulespot.pojo.MarketSymbolResult;
import com.spark.modulespot.pojo.MarketSymbolResult.DataBean.AskBean;
import com.spark.modulespot.pojo.MarketSymbolResult.DataBean.BidBean;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.base.Constant;
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
public class DepthFragment extends BaseFragment<FragmentDepthBinding, DepthViewModel> {
    private DepthSellAdapter mDepthSellAdapter;
    private DepthBuyAdapter mDepthBuyAdapter;
    private List<AskBean> mDepthSellList = new ArrayList<>();
    private List<BidBean> mDepthBuyList = new ArrayList<>();
    private final int defaultSize = 20;
    private AllThumbResult.DataBean allThumbResult;

    public static DepthFragment newInstance(AllThumbResult.DataBean allThumbResult) {
        DepthFragment quotesVPFragment = new DepthFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", allThumbResult);
        quotesVPFragment.setArguments(bundle);
        return quotesVPFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_depth;
    }

    @Override
    public int initVariableId() {
        return BR.depthViewModel;
    }


    @Override
    public void loadLazyData() {
        binding.depthRoot.showLoading();
        allThumbResult = getArguments().getParcelable("key");

        mDepthSellAdapter = new DepthSellAdapter(mDepthSellList);
        binding.sellRv.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        binding.sellRv.setAdapter(mDepthSellAdapter);

        mDepthBuyAdapter = new DepthBuyAdapter(mDepthBuyList);
        binding.buyRv.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        binding.buyRv.setAdapter(mDepthBuyAdapter);

        //默认配置20个数据
        for (int i = 0; i < defaultSize; i++) {
            AskBean askBean = new AskBean();
            askBean.setIndex(String.valueOf(i + 1));
            mDepthSellList.add(askBean);
            BidBean bidBean = new BidBean();
            bidBean.setIndex(String.valueOf(i + 1));
            mDepthBuyList.add(bidBean);
        }
        if (Constant.isHttpAndWs) {
            if (!Constant.lastB2BMarketPushSymbol.equals(allThumbResult.getSymbol())) {
                viewModel.subscribeB2BTradePlate(allThumbResult);
            } else {
                viewModel.initThumbWithoutRequest(allThumbResult);
            }
        } else {
            viewModel.initThumb(allThumbResult);
        }
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.mMarketSymbolResultSingleLiveEvent.observe(this, new Observer<MarketSymbolResult>() {
            @Override
            public void onChanged(@Nullable MarketSymbolResult marketSymbolResult) {
                if (!isVisibleToUser) return;
                int askSize = marketSymbolResult.getData().getAsk().size();
                for (int i = 0; i < defaultSize; i++) {
                    if (i < askSize) {
                        marketSymbolResult.getData().getAsk().get(i).setIndex(mDepthSellList.get(i).getIndex());
                        mDepthSellList.set(i, marketSymbolResult.getData().getAsk().get(i));
                    } else {
                        AskBean askBean = new AskBean();
                        askBean.setIndex(String.valueOf(i + 1));
                        mDepthSellList.set(i, askBean);
                    }
                }

                int bidSize = marketSymbolResult.getData().getBid().size();
                for (int i = 0; i < defaultSize; i++) {
                    if (i < bidSize) {
                        marketSymbolResult.getData().getBid().get(i).setIndex(mDepthBuyList.get(i).getIndex());
                        mDepthBuyList.set(i, marketSymbolResult.getData().getBid().get(i));
                    } else {
                        BidBean bidBean = new BidBean();
                        bidBean.setIndex(String.valueOf(i + 1));
                        mDepthBuyList.set(i, bidBean);
                    }
                }

                mDepthSellAdapter.notifyDataSetChanged();
                mDepthBuyAdapter.notifyDataSetChanged();
                if (binding.depthRoot.isLoadingCurrentState()) {
                    binding.depthRoot.showContent();
                }
            }
        });

        viewModel.uc.mMarketBuySingleLiveEvent.observe(this, new Observer<List<BidBean>>() {
            @Override
            public void onChanged(@Nullable List<BidBean> bidBeans) {
                int bidSize = bidBeans.size();
                for (int i = 0; i < defaultSize; i++) {
                    if (i < bidSize) {
                        bidBeans.get(i).setIndex(mDepthBuyList.get(i).getIndex());
                        mDepthBuyList.set(i, bidBeans.get(i));
                    } else {
                        BidBean bidBean = new BidBean();
                        bidBean.setIndex(String.valueOf(i + 1));
                        mDepthBuyList.set(i, bidBean);
                    }
                }
                mDepthBuyAdapter.notifyDataSetChanged();
                if (binding.depthRoot.isLoadingCurrentState()) {
                    binding.depthRoot.showContent();
                }
            }
        });

        viewModel.uc.mMarketSellSingleLiveEvent.observe(this, new Observer<List<AskBean>>() {
            @Override
            public void onChanged(@Nullable List<AskBean> askBeans) {
                LogUtils.e("market", "askBeans");
                int bidSize = askBeans.size();
                for (int i = 0; i < defaultSize; i++) {
                    if (i < bidSize) {
                        askBeans.get(i).setIndex(mDepthBuyList.get(i).getIndex());
                        mDepthSellList.set(i, askBeans.get(i));
                    } else {
                        AskBean askBean = new AskBean();
                        askBean.setIndex(String.valueOf(i + 1));
                        mDepthSellList.set(i, askBean);
                    }
                }
                mDepthSellAdapter.notifyDataSetChanged();
                if (binding.depthRoot.isLoadingCurrentState()) {
                    binding.depthRoot.showContent();
                }
            }
        });
    }
}
