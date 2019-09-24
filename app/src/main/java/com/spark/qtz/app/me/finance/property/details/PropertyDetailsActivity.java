package com.spark.qtz.app.me.finance.property.details;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
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
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.spark.acclient.pojo.CoinTransDetailsResult;
import com.spark.acclient.pojo.PropertyDetailsTypeResult;
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.property.adapter.PropertyDetailsAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityPropertyDetailsBinding;
import com.spark.qtz.ui.popup.FinancePropertyFilterPopup;
import com.spark.qtz.ui.popup.impl.OnFinanceFilterListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/4
 * 描    述：
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_ME_PROPERTY_DETAILS)
public class PropertyDetailsActivity extends BaseActivity<ActivityPropertyDetailsBinding, PropertyDetailsViewModel> {
    @Autowired(name = "propertDetails")
    SpotWalletResult.DataBean propertDetailsBean;
    @Autowired
    String busiType;

    private TitleBean mTitleModel;
    private int pageIndex = 1;
    private PropertyDetailsAdapter mPropertyDetailsAdapter;
    private List<CoinTransDetailsResult.DataBean.RecordsBean> mRecordsBeanList = new ArrayList<>();

    private String filterTypeSelect, filterSubTypeSelect, minLimitSelect, maxLimitSelect, startTimeSelect, endTimeSelect;
    private List<PropertyDetailsTypeResult.DataBean> mainTypeList;
    private List<PropertyDetailsTypeResult.DataBean> subTypeList;
    private FinancePropertyFilterPopup filterPopup;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_property_details;
    }

    @Override
    public int initVariableId() {
        return BR.propertyDetailsViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.propertyDetailsTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        if (!busiType.equals("SPOT")) {
            binding.hide1.setVisibility(View.INVISIBLE);
            binding.hide2.setVisibility(View.INVISIBLE);
        }
        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleName(propertDetailsBean.getCoinId() + getString(R.string.details2));
        binding.propertyDetailsTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.propertyDetailsTitle.titleRootLeft);
        viewModel.init(this, busiType, propertDetailsBean);


        mPropertyDetailsAdapter = new PropertyDetailsAdapter(mRecordsBeanList);
        binding.rvPropertyDetails.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPropertyDetails.setAdapter(mPropertyDetailsAdapter);
        mPropertyDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_RECORD_DETAILS)
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
        viewModel.getType();
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
        viewModel.uc.mainType.observe(this, new Observer<PropertyDetailsTypeResult>() {
            @Override
            public void onChanged(@Nullable PropertyDetailsTypeResult propertyDetailsTypeResult) {
                mainTypeList = new ArrayList<>();
                PropertyDetailsTypeResult.DataBean dataBean = new PropertyDetailsTypeResult.DataBean();
                dataBean.setDescript(App.getInstance().getString(R.string.all));
                dataBean.setValue("");
                mainTypeList.add(dataBean);
                mainTypeList.addAll(propertyDetailsTypeResult.getData());
            }
        });
        viewModel.uc.subType.observe(this, new Observer<PropertyDetailsTypeResult>() {
            @Override
            public void onChanged(@Nullable PropertyDetailsTypeResult propertyDetailsTypeResult) {
                subTypeList = new ArrayList<>();
                PropertyDetailsTypeResult.DataBean dataBean = new PropertyDetailsTypeResult.DataBean();
                dataBean.setDescript(App.getInstance().getString(R.string.all));
                dataBean.setValue("");
                subTypeList.add(dataBean);
                subTypeList.addAll(propertyDetailsTypeResult.getData());
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void refresh() {
        binding.swipeLayout.setRefreshing(true);

        pageIndex = 1;
        viewModel.loadPageDateFilter(pageIndex, filterTypeSelect, filterSubTypeSelect, minLimitSelect, maxLimitSelect, startTimeSelect, endTimeSelect,
                new OnRequestListener<CoinTransDetailsResult>() {
                    @Override
                    public void onSuccess(CoinTransDetailsResult coinTransDetailsResult) {
                        mRecordsBeanList.clear();
                        mRecordsBeanList.addAll(coinTransDetailsResult.getData().getRecords());
                        mPropertyDetailsAdapter.notifyDataSetChanged();
                        setData(true, coinTransDetailsResult.getData().getRecords());
                        binding.swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(String message) {
                        Toasty.showError(message);
                        binding.swipeLayout.setRefreshing(false);
                    }
                });

    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        viewModel.loadPageDateFilter(pageIndex, filterTypeSelect, filterSubTypeSelect, minLimitSelect, maxLimitSelect, startTimeSelect, endTimeSelect,
                new OnRequestListener<CoinTransDetailsResult>() {
                    @Override
                    public void onSuccess(CoinTransDetailsResult coinTransDetailsResult) {
                        boolean isRefresh = pageIndex == 1;
                        setData(isRefresh, coinTransDetailsResult.getData().getRecords());
                    }

                    @Override
                    public void onFail(String message) {
                        Toasty.showError(message);
                        binding.swipeLayout.setRefreshing(false);
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
            mPropertyDetailsAdapter.setNewData(data);
            if (size == 0) {
                mPropertyDetailsAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvPropertyDetails);
            }
        } else {
            if (size > 0) {
                mPropertyDetailsAdapter.addData(data);
            }
        }
    }


    @OnClick({R.id.filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter:
                /*new XPopup.Builder(this)
                        .maxHeight((int) (XPopupUtils.getWindowHeight(this) * .85f))
                        .asBottomList(App.getInstance().getString(R.string.please_choose), filterTypeList,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        if (position == 0) {
                                            isFlilterResult = false;
                                            filterTypeSelect = "1,2,3,4,5,6";
                                        } else {
                                            isFlilterResult = true;
                                            if (position == 5) {
                                                filterTypeSelect = "6";
                                            } else if (position == 6) {
                                                filterTypeSelect = "5";
                                            } else {
                                                filterTypeSelect = String.valueOf(position);
                                            }
                                        }
                                        refresh();
                                    }
                                })
                        .show();*/
                showPop();
                break;
        }
    }

    //筛选框
    private void showPop() {
        if (filterPopup == null) {
            filterPopup = (FinancePropertyFilterPopup) new XPopup.Builder(this)
                    .atView(binding.propertyDetailsTitle.titleLeftImg)
                    .asCustom(new FinancePropertyFilterPopup(this, mainTypeList, subTypeList, new OnFinanceFilterListener() {
                        @Override
                        public void onSelect(String filterType, String filterSubType, String minLimit, String maxLimit, String startTime, String endTime) {
                            filterTypeSelect = filterType;
                            filterSubTypeSelect = filterSubType;
                            minLimitSelect = minLimit;
                            maxLimitSelect = maxLimit;
                            startTimeSelect = startTime;
                            endTimeSelect = endTime;
                            refresh();
                        }
                    }));
        }
        filterPopup.toggle();
    }
}
