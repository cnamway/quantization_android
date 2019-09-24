package com.spark.qtz.app.trade.legal_currency.viewpager;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.legal_currency.adapter.LcVPAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.LcAdFilterBean;
import com.spark.qtz.databinding.FragmentLcVpBinding;
import com.spark.qtz.ui.RvLoadMoreView;
import com.spark.qtz.ui.popup.LcTradePopup;
import com.spark.qtz.ui.popup.TradePwdPopup;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.popup.impl.OnOrderCreateListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.LcTradeClient;
import com.spark.otcclient.pojo.FindPageResult;
import com.spark.otcclient.pojo.OrderCreateBean;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcVPFragment extends BaseFragment<FragmentLcVpBinding, LcViewModel> {
    private static final String COIN_NAME = "type";
    private int pageIndex = 1;
    private static final int PAGE_SIZE = 3;
    private LcVPAdapter mLcVPAdapter;
    private List<FindPageResult.DataBean.RecordsBean> mRecordsBeanList = new ArrayList<>();
    private String payMode = "", country = "", minLimit = "", maxLimit = "";

    public static LcVPFragment newInstance(String coinName) {
        LcVPFragment lcVPFragment = new LcVPFragment();
        Bundle bundle = new Bundle();
        bundle.putString(COIN_NAME, coinName);
        lcVPFragment.setArguments(bundle);
        return lcVPFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_lc_vp;
    }

    @Override
    public int initVariableId() {
        return BR.lcViewModel;
    }

    @Override
    public void initView() {
        mLcVPAdapter = new LcVPAdapter(mRecordsBeanList);
        binding.lcRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.lcRv.setAdapter(mLcVPAdapter);
        mLcVPAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                if (!App.getInstance().isAppLogin()) {
                    ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                            .navigation();
                } else {
                    viewModel.findData(new OnRequestListener<SpotWalletResult>() {
                        @Override
                        public void onSuccess(SpotWalletResult spotWalletResult) {
                            new XPopup.Builder(getContext())
                                    .moveUpToKeyboard(false)
                                    .asCustom(new LcTradePopup(getContext(), spotWalletResult.getData(), Constant.lcBuyOrSell, mRecordsBeanList.get(position), new OnOrderCreateListener() {
                                        @Override
                                        public void onOrderCreate(final OrderCreateBean orderCreateBean) {
                                            if (orderCreateBean.getOrderType().equals("1")) {
                                                showDialog(getString(R.string.loading));
                                                LcTradeClient.getInstance().orderCreate(orderCreateBean, getArguments().getString(COIN_NAME));
                                            } else {
                                                if (orderCreateBean.getTradePwd() == null) {
                                                    new XPopup.Builder(getContext())
                                                            .autoOpenSoftInput(true)
                                                            .asCustom(new TradePwdPopup(getContext(), new OnEtContentListener() {
                                                                @Override
                                                                public void onCEtContentInput(String content) {
                                                                    showKeyboard(false);
                                                                    orderCreateBean.setTradePwd(content);
                                                                    showDialog(App.getInstance().getString(R.string.loading));
                                                                    LcTradeClient.getInstance().orderCreate(orderCreateBean, getArguments().getString(COIN_NAME));
                                                                }
                                                            }))
                                                            .show();
                                                } else {
                                                    showDialog(getString(R.string.loading));
                                                    LcTradeClient.getInstance().orderCreate(orderCreateBean, getArguments().getString(COIN_NAME));
                                                }
                                            }
                                        }
                                    }))
                                    .show();
                        }

                        @Override
                        public void onFail(String message) {
                            Toasty.showError(message);
                            mLcVPAdapter.setEnableLoadMore(true);
                            binding.swipeLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });

        //下拉刷新
        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        //上拉加载
        mLcVPAdapter.setLoadMoreView(new RvLoadMoreView());
        mLcVPAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, binding.lcRv);
    }

    @Override
    public void loadLazyData() {
        refresh();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.tradeType.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Constant.lcBuyOrSell = integer.intValue();
                mRecordsBeanList.clear();
                mLcVPAdapter.setEmptyView(R.layout.view_rv_empty, binding.lcRv);
                refresh();
            }
        });
        viewModel.uc.adFilter.observe(this, new Observer<LcAdFilterBean>() {
            @Override
            public void onChanged(@Nullable LcAdFilterBean lcAdFilterBean) {
                payMode = lcAdFilterBean.getPayMode();
                country = lcAdFilterBean.getCountry();
                minLimit = lcAdFilterBean.getMinLimit();
                maxLimit = lcAdFilterBean.getMaxLimit();
                refresh();
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void refresh() {
        binding.swipeLayout.setRefreshing(true);

        pageIndex = 1;
        mLcVPAdapter.setEnableLoadMore(false);
        viewModel.findPage(pageIndex, getArguments().getString(COIN_NAME), Constant.lcBuyOrSell, payMode, country, minLimit, maxLimit, new OnRequestListener<FindPageResult>() {
            @Override
            public void onSuccess(FindPageResult findPageResult) {
                mRecordsBeanList.clear();
                mRecordsBeanList.addAll(findPageResult.getData().getRecords());
                mLcVPAdapter.notifyDataSetChanged();
                setData(true, findPageResult.getData().getRecords());
                mLcVPAdapter.setEnableLoadMore(true);
                binding.swipeLayout.setRefreshing(false);
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                mLcVPAdapter.setEnableLoadMore(true);
                binding.swipeLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        viewModel.findPage(pageIndex, getArguments().getString(COIN_NAME), Constant.lcBuyOrSell, payMode, country, minLimit, maxLimit, new OnRequestListener<FindPageResult>() {
            @Override
            public void onSuccess(FindPageResult findPageResult) {
                boolean isRefresh = pageIndex == 1;
                setData(isRefresh, findPageResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                mLcVPAdapter.loadMoreFail();
            }
        });
    }

    /**
     * * @param isRefresh
     *
     * @param data
     */
    private void setData(boolean isRefresh, List data) {
        pageIndex++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mLcVPAdapter.setNewData(data);
            if (size == 0) {
                mLcVPAdapter.setEmptyView(R.layout.view_rv_empty, binding.lcRv);
            }
        } else {
            if (size > 0) {
                mLcVPAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mLcVPAdapter.loadMoreEnd(isRefresh);
        } else {
            mLcVPAdapter.loadMoreComplete();
        }
    }
}
