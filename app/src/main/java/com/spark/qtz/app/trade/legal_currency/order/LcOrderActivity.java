package com.spark.qtz.app.trade.legal_currency.order;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.legal_currency.adapter.LcOrderAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityLcOrderBinding;
import com.spark.qtz.ui.RvLoadMoreView;
import com.spark.qtz.ui.popup.LcOrderFilterPopup;
import com.spark.qtz.ui.popup.impl.OnLcFilterListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.otcclient.pojo.LcOrderResult;
import com.spark.otcclient.pojo.TradeCoinListResult;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/11
 * 描    述：
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_TRADE_LC_ORDER)
public class LcOrderActivity extends BaseActivity<ActivityLcOrderBinding, LcOrderViewModel> {
    private TitleBean mTitleModel;
    private LcOrderAdapter mLcOrderAdapter;
    private List<LcOrderResult.DataBean.RecordsBean> mRecordsBeanList = new ArrayList<>();
    private int pageIndex = 1;
    private static final int PAGE_SIZE = 3;
    private String status = "-1", adType = "", coinName = "";
    private LcOrderFilterPopup mLcOrderFilterPopup;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_lc_order;
    }

    @Override
    public int initVariableId() {
        return BR.lcOrderViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.orderTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setShowRightImg(true);
        binding.orderTitle.titleRightImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_filter));
        binding.orderTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.orderTitle.titleRootLeft, binding.orderTitle.titleRootRight);

        mLcOrderAdapter = new LcOrderAdapter(mRecordsBeanList);
        binding.rvLvOrder.setLayoutManager(new LinearLayoutManager(this));
        binding.rvLvOrder.setAdapter(mLcOrderAdapter);
        mLcOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                //status订单状态 0-已取消 1-未付款 2-已付款 3-已完成 4-申诉中
                if (mRecordsBeanList.get(position).getStatus() == 1) {
                    ARouter.getInstance()
                            .build(ARouterPath.ACTIVITY_TRADE_LC_UNPAY_DETAILS_ORDER)
                            .withParcelable("orderDetails", mRecordsBeanList.get(position))
                            .navigation();
                } else if (mRecordsBeanList.get(position).getStatus() == 2) {
                    ARouter.getInstance()
                            .build(ARouterPath.ACTIVITY_TRADE_LC_PAIY_DETAILS_ORDER)
                            .withParcelable("orderDetails", mRecordsBeanList.get(position))
                            .navigation();
                } else {
                    ARouter.getInstance()
                            .build(ARouterPath.ACTIVITY_TRADE_LC_DETAILS_ORDER)
                            .withParcelable("orderDetails", mRecordsBeanList.get(position))
                            .navigation();
                }
            }
        });

        //下拉刷新
        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        //上拉加载
        mLcOrderAdapter.setLoadMoreView(new RvLoadMoreView());
        mLcOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, binding.rvLvOrder);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.mIsRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    refresh();
                }
            }
        });
    }

    @Override
    protected void onTitleRightClick() {
        //查询所有的币种
        if (Constant.lcCoinNameArray == null) {
            showDialog(App.getInstance().getString(R.string.loading));
            viewModel.getVCoinSupport(new OnRequestListener<TradeCoinListResult>() {
                @Override
                public void onSuccess(TradeCoinListResult tradeCoinListResult) {
                    dismissDialog();
                    showPop();
                }

                @Override
                public void onFail(String message) {
                    dismissDialog();
                    Toasty.showError(message);
                }
            });
        } else {
            showPop();
        }

    }

    private void showPop() {
        if (mLcOrderFilterPopup == null) {
            mLcOrderFilterPopup = (LcOrderFilterPopup) new XPopup.Builder(this)
                    .atView(binding.orderTitle.titleLeftImg)
                    .asCustom(new LcOrderFilterPopup(this, new OnLcFilterListener() {
                        @Override
                        public void onLcSelect(String selectAdType, String selectStatus, String selectCoinName) {
                            adType = selectAdType;
                            status = selectStatus;
                            coinName = selectCoinName;
                            refresh();
                        }
                    }));
        }
        mLcOrderFilterPopup.toggle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    /**
     * 下拉刷新
     */
    private void refresh() {
        binding.swipeLayout.setRefreshing(true);

        pageIndex = 1;
        mLcOrderAdapter.setEnableLoadMore(false);
        if (status.equals("-1")) {
            viewModel.getLcOrderAll(pageIndex, adType, coinName, new OnRequestListener<LcOrderResult>() {
                @Override
                public void onSuccess(LcOrderResult lcOrderResult) {
                    mRecordsBeanList.clear();
                    mRecordsBeanList.addAll(lcOrderResult.getData().getRecords());
                    mLcOrderAdapter.notifyDataSetChanged();
                    setData(true, lcOrderResult.getData().getRecords());
                    mLcOrderAdapter.setEnableLoadMore(true);
                    binding.swipeLayout.setRefreshing(false);
                }

                @Override
                public void onFail(String message) {
                    Toasty.showError(message);
                    mLcOrderAdapter.setEnableLoadMore(true);
                    binding.swipeLayout.setRefreshing(false);
                }
            });
        } else {
            viewModel.getLcOrder(pageIndex, status, adType, coinName, new OnRequestListener<LcOrderResult>() {
                @Override
                public void onSuccess(LcOrderResult lcOrderResult) {
                    mRecordsBeanList.clear();
                    mRecordsBeanList.addAll(lcOrderResult.getData().getRecords());
                    mLcOrderAdapter.notifyDataSetChanged();
                    setData(true, lcOrderResult.getData().getRecords());
                    mLcOrderAdapter.setEnableLoadMore(true);
                    binding.swipeLayout.setRefreshing(false);
                }

                @Override
                public void onFail(String message) {
                    Toasty.showError(message);
                    mLcOrderAdapter.setEnableLoadMore(true);
                    binding.swipeLayout.setRefreshing(false);
                }
            });
        }

    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        if (status.equals("-1")) {
            viewModel.getLcOrderAll(pageIndex, adType, coinName, new OnRequestListener<LcOrderResult>() {
                @Override
                public void onSuccess(LcOrderResult lcOrderResult) {
                    boolean isRefresh = pageIndex == 1;
                    setData(isRefresh, lcOrderResult.getData().getRecords());
                }

                @Override
                public void onFail(String message) {
                    mLcOrderAdapter.loadMoreFail();
                }
            });
        } else {
            viewModel.getLcOrder(pageIndex, status, adType, coinName, new OnRequestListener<LcOrderResult>() {
                @Override
                public void onSuccess(LcOrderResult lcOrderResult) {
                    boolean isRefresh = pageIndex == 1;
                    setData(isRefresh, lcOrderResult.getData().getRecords());
                }

                @Override
                public void onFail(String message) {
                    mLcOrderAdapter.loadMoreFail();
                }
            });
        }
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
            mLcOrderAdapter.setNewData(data);
            if (size == 0) {
                mLcOrderAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvLvOrder);
            }
        } else {
            if (size > 0) {
                mLcOrderAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mLcOrderAdapter.loadMoreEnd(isRefresh);
        } else {
            mLcOrderAdapter.loadMoreComplete();
        }
    }
}
