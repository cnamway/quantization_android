package com.spark.qtz.app.me.finance.ad;

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
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.ad.adapter.AdDownAdapter;
import com.spark.qtz.app.me.finance.ad.adapter.AdUpAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentAdBinding;
import com.spark.qtz.ui.RvLoadMoreView;
import com.spark.qtz.ui.popup.AdStatuePopup;
import com.spark.qtz.ui.popup.TradePwdPopup;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.popup.impl.OnPositionChooseListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.AdvertiseScanClient;
import com.spark.otcclient.pojo.AdSelfDownFindResult;
import com.spark.otcclient.pojo.AdSelfUpFindResult;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/31
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AdFragment extends BaseFragment<FragmentAdBinding, AdFgViewModel> {
    private static final String TYPE = "type";
    private AdUpAdapter mAdUpAdapter;
    private AdDownAdapter mAdDownAdapter;
    private List<AdSelfUpFindResult.DataBean> mAdUpList = new ArrayList<>();
    private List<AdSelfDownFindResult.DataBean.RecordsBean> mAdDownList = new ArrayList<>();
    private int pageIndex = 1;
    private static final int PAGE_SIZE = 3;
    private int upPosition, downPosition;

    /**
     * @param type 0 - 上架中 1 - 下架中
     * @return
     */
    public static AdFragment newInstance(int type) {
        AdFragment adFragment = new AdFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        adFragment.setArguments(bundle);
        return adFragment;
    }


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_ad;
    }

    @Override
    public void initView() {
        switch (getArguments().getInt(TYPE)) {
            case 0:
                mAdUpAdapter = new AdUpAdapter(mAdUpList);
                binding.adRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.adRv.setAdapter(mAdUpAdapter);
                mAdUpAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                        new XPopup.Builder(getContext())
                                .asCustom(new AdStatuePopup(getContext(), mAdUpList.get(position), new OnPositionChooseListener() {
                                    @Override
                                    public void onChoosePosition(int type) {
                                        switch (type) {
                                            //下架
                                            case 0:
                                                showDialog(getString(R.string.loading));
                                                downPosition = position;
                                                AdvertiseScanClient.getInstance().adOffShelves(mAdUpList.get(position).getId());
                                                break;
                                        }
                                    }
                                }))
                                .show();
                    }
                });

                break;
            case 1:
                mAdDownAdapter = new AdDownAdapter(mAdDownList);
                binding.adRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.adRv.setAdapter(mAdDownAdapter);
                mAdDownAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                        new XPopup.Builder(getContext())
                                .asCustom(new AdStatuePopup(getContext(), mAdDownList.get(position), new OnPositionChooseListener() {
                                    @Override
                                    public void onChoosePosition(int type) {
                                        switch (type) {
                                            //修改
                                            case 0:
                                                AdSelfDownFindResult.DataBean.RecordsBean recordsBean = mAdDownList.get(position);
                                                ARouter.getInstance().build(App.getInstance().isAppLogin() ? ARouterPath.ACTIVITY_TRADE_AD_CREATE : ARouterPath.ACTIVITY_ME_LOGIN)
                                                        .withParcelable("ads", recordsBean)
                                                        .navigation();
                                                break;
                                            //上架
                                            case 1:
                                                new XPopup.Builder(getContext())
                                                        .autoOpenSoftInput(true)
                                                        .asCustom(new TradePwdPopup(getContext(), new OnEtContentListener() {
                                                            @Override
                                                            public void onCEtContentInput(String content) {
                                                                showKeyboard(false);
                                                                showDialog(getString(R.string.loading));
                                                                upPosition = position;
                                                                AdvertiseScanClient.getInstance().adOnShelves(mAdDownList.get(position).getId(), content);
                                                            }
                                                        }))
                                                        .show();
                                                break;
                                            //删除
                                            case 2:
                                                new XPopup.Builder(getContext())
                                                        .autoOpenSoftInput(true)
                                                        .asCustom(new TradePwdPopup(getContext(), getString(R.string.str_confirm_delete), new OnEtContentListener() {
                                                            @Override
                                                            public void onCEtContentInput(String content) {
                                                                showKeyboard(false);
                                                                showDialog(getString(R.string.loading));
                                                                AdvertiseScanClient.getInstance().adDelete(mAdDownList.get(position).getId(), content);
                                                            }
                                                        }))
                                                        .show();
                                                break;
                                        }
                                    }
                                }))
                                .show();
                    }
                });


                //上拉加载
                mAdDownAdapter.setLoadMoreView(new RvLoadMoreView());
                mAdDownAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        loadMore();
                    }
                }, binding.adRv);
                break;
        }
        //下拉刷新
        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    public int initVariableId() {
        return BR.adFgViewModel;
    }

    @Override
    public void loadLazyData() {
        switch (getArguments().getInt(TYPE)) {
            case 0:
                viewModel.initAdType(getArguments().getInt(TYPE));
                break;
            case 1:
                refresh();
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            loadLazyData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        prepareFetchData(true);
    }

    private void refresh() {
        switch (getArguments().getInt(TYPE)) {
            case 0:
                viewModel.initAdType(getArguments().getInt(TYPE));
                break;
            case 1:
                binding.swipeLayout.setRefreshing(true);

                pageIndex = 1;
                mAdDownAdapter.setEnableLoadMore(false);
                viewModel.loadAdDown(getArguments().getInt(TYPE), pageIndex, new OnRequestListener<AdSelfDownFindResult>() {
                    @Override
                    public void onSuccess(AdSelfDownFindResult adSelfDownFindResult) {
                        mAdDownList.clear();
                        mAdDownList.addAll(adSelfDownFindResult.getData().getRecords());
                        mAdDownAdapter.notifyDataSetChanged();
                        setData(true, adSelfDownFindResult.getData().getRecords());
                        mAdDownAdapter.setEnableLoadMore(true);
                        binding.swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(String message) {
                        Toasty.showError(message);
                        mAdDownAdapter.setEnableLoadMore(true);
                        binding.swipeLayout.setRefreshing(false);
                    }
                });
                break;
        }


    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        viewModel.loadAdDown(getArguments().getInt(TYPE), pageIndex, new OnRequestListener<AdSelfDownFindResult>() {
            @Override
            public void onSuccess(AdSelfDownFindResult adSelfDownFindResult) {
                boolean isRefresh = pageIndex == 1;
                setData(isRefresh, adSelfDownFindResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                mAdDownAdapter.loadMoreFail();
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
            mAdDownAdapter.setNewData(data);
            if (size == 0) {
                mAdDownAdapter.setEmptyView(R.layout.view_rv_empty, binding.adRv);
            }
        } else {
            if (size > 0) {
                mAdDownAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdDownAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdDownAdapter.loadMoreComplete();
        }
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.mAdSelfUpFindResultSingleLiveEvent.observe(this, new Observer<AdSelfUpFindResult>() {
            @Override
            public void onChanged(@Nullable AdSelfUpFindResult adSelfUpFindResult) {
                if (mAdUpAdapter == null) return;
                mAdUpList.clear();
                mAdUpList.addAll(adSelfUpFindResult.getData());
                mAdUpAdapter.setEnableLoadMore(false);
                binding.swipeLayout.setRefreshing(false);
                final int size = mAdUpList == null ? 0 : mAdUpList.size();
                if (size == 0) {
                    mAdUpAdapter.setEmptyView(R.layout.view_rv_empty, binding.adRv);
                }
                mAdUpAdapter.notifyDataSetChanged();
            }
        });

        viewModel.uc.mAdUpdate.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                switch (integer.intValue()) {
                    case 0://
                        mAdDownAdapter.remove(downPosition);
                        refresh();
                        break;
                    case 1:
                        mAdUpAdapter.remove(upPosition);
                        refresh();
                        break;
                }

            }
        });

        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    loadLazyData();
                }
            }
        });
    }
}
