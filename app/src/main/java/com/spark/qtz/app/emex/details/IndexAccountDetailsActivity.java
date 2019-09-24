package com.spark.qtz.app.emex.details;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.spark.acclient.pojo.CoinTransDetailsResult;
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.property.adapter.PropertyDetailsAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityIndexAccountDetailsBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/4
 * 描    述：账户详情
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_EMEX_ACCOUNT_DETAILS)
public class IndexAccountDetailsActivity extends BaseActivity<ActivityIndexAccountDetailsBinding, IndexAccountDetailsViewModel> {
    @Autowired(name = "propertDetails")
    SpotWalletResult.DataBean propertDetailsBean;

    private TitleBean mTitleModel;
    private int pageIndex = 1;
    private PropertyDetailsAdapter mPropertyDetailsAdapter;
    private List<CoinTransDetailsResult.DataBean.RecordsBean> mRecordsBeanList = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_index_account_details;
    }

    @Override
    public int initVariableId() {
        return BR.indexAccountDetailsViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.title.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, getColor(R.color.bg_main));

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleName(getString(R.string.str_account_details));
        binding.title.setViewTitle(mTitleModel);
        setTitleListener(binding.title.titleRootLeft);

        mPropertyDetailsAdapter = new PropertyDetailsAdapter(mRecordsBeanList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(mPropertyDetailsAdapter);
        mPropertyDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_EMEX_STRATEGY_DETAILS)
                        .withParcelable("propertDetails", mRecordsBeanList.get(position))
                        .navigation();
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

        binding.nestedScrollView.setOnScrollChangeListener(
                new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        //判断是否滑到的底部
                        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                            loadMore();
                        }
                    }

                });
    }

    @Override
    public void initData() {
        //viewModel.getType();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.refresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    refresh();
                }
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void refresh() {
        binding.swipeLayout.setRefreshing(true);

        pageIndex = 1;
//        viewModel.loadPageDateFilter(pageIndex, filterTypeSelect, filterSubTypeSelect, minLimitSelect, maxLimitSelect, startTimeSelect, endTimeSelect,
//                new OnRequestListener<CoinTransDetailsResult>() {
//                    @Override
//                    public void onSuccess(CoinTransDetailsResult coinTransDetailsResult) {
//                        mRecordsBeanList.clear();
//                        mRecordsBeanList.addAll(coinTransDetailsResult.getData().getRecords());
//                        mPropertyDetailsAdapter.notifyDataSetChanged();
//                        setData(true, coinTransDetailsResult.getData().getRecords());
//                        binding.swipeLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onFail(String message) {
//                        Toasty.showError(message);
//                        binding.swipeLayout.setRefreshing(false);
//                    }
//                });

    }

    /**
     * 上拉加载
     */
    private void loadMore() {
//        viewModel.loadPageDateFilter(pageIndex, filterTypeSelect, filterSubTypeSelect, minLimitSelect, maxLimitSelect, startTimeSelect, endTimeSelect,
//                new OnRequestListener<CoinTransDetailsResult>() {
//                    @Override
//                    public void onSuccess(CoinTransDetailsResult coinTransDetailsResult) {
//                        boolean isRefresh = pageIndex == 1;
//                        setData(isRefresh, coinTransDetailsResult.getData().getRecords());
//                    }
//
//                    @Override
//                    public void onFail(String message) {
//                        Toasty.showError(message);
//                        binding.swipeLayout.setRefreshing(false);
//                    }
//                });
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
            mPropertyDetailsAdapter.setNewData(data);
            if (size == 0) {
                mPropertyDetailsAdapter.setEmptyView(R.layout.view_rv_empty, binding.recyclerView);
            }
        } else {
            if (size > 0) {
                mPropertyDetailsAdapter.addData(data);
            }
        }
    }

}
