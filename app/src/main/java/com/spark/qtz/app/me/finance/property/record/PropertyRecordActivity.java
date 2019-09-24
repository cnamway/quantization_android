package com.spark.qtz.app.me.finance.property.record;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.spark.acclient.pojo.CoinTransDetailsResult;
import com.spark.qtz.R;
import com.spark.qtz.app.me.finance.property.adapter.PropertyDetailsAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityPropertyRecordBinding;
import com.spark.qtz.ui.RvLoadMoreView;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.BR;
import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_PROPERTY_RECORD)
public class PropertyRecordActivity extends BaseActivity<ActivityPropertyRecordBinding, PropertyRecordViewModel> {
    @Autowired
    int recordType; //0 - 充币 1 - 提币 2 - 划转
    @Autowired
    int coinType;  //0 - 币币 1 - 法币 2 - 合约

    private TitleBean mTitleModel;
    private int pageIndex = 1;
    private PropertyDetailsAdapter mPropertyDetailsAdapter;
    private List<CoinTransDetailsResult.DataBean.RecordsBean> mRecordsBeanList = new ArrayList<>();
    private static final int PAGE_SIZE = 3;
    private String selectCoinName = "";
    private String recordTypeStr, coinTypeStr;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_property_record;
    }

    @Override
    public int initVariableId() {
        return BR.propertyRecordViewModel;
    }

    @Override
    public void initView() {

        LogUtils.e("coinType", coinType);
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.propertyRecordDetailsTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        mTitleModel = new TitleBean();
        switch (recordType) {
            case 0:
                mTitleModel.setTitleName(getString(R.string.charge_record));
                recordTypeStr = "1";
                break;
            case 1:
                mTitleModel.setTitleName(getString(R.string.mention_record));
                recordTypeStr = "2";
                break;
            case 2:
                mTitleModel.setTitleName(getString(R.string.transfer_record));
                recordTypeStr = "3,4";
                break;
        }
        switch (coinType) {
            case 0:
                coinTypeStr = "SPOT";
                break;
            case 1:
                coinTypeStr = "OTC";
                break;
            case 2:
                coinTypeStr = "CFD";
                break;
        }
        mTitleModel.setShowRightImg(true);
        mTitleModel.setShowTitleLine(true);
        binding.propertyRecordDetailsTitle.titleRightImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_filter));
        binding.propertyRecordDetailsTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.propertyRecordDetailsTitle.titleRootLeft, binding.propertyRecordDetailsTitle.titleRootRight);

        mPropertyDetailsAdapter = new PropertyDetailsAdapter(mRecordsBeanList);
        binding.rvPropertyOrder.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPropertyOrder.setAdapter(mPropertyDetailsAdapter);
        mPropertyDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
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
        mPropertyDetailsAdapter.setLoadMoreView(new RvLoadMoreView());
        mPropertyDetailsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, binding.rvPropertyOrder);
    }

    @Override
    protected void onTitleRightClick() {
        viewModel.findCoinList(this);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.coinName.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                selectCoinName = s;
                refresh();
            }
        });
    }

    @Override
    public void initData() {
        refresh();
    }

    private void refresh() {
        binding.swipeLayout.setRefreshing(true);
        pageIndex = 1;
        viewModel.findPropertyRecord(pageIndex, selectCoinName, coinTypeStr, recordTypeStr, new OnRequestListener<CoinTransDetailsResult>() {
            @Override
            public void onSuccess(CoinTransDetailsResult coinTransDetailsResult) {
                mRecordsBeanList.clear();
                mRecordsBeanList.addAll(coinTransDetailsResult.getData().getRecords());
                setData(true, coinTransDetailsResult.getData().getRecords());
                mPropertyDetailsAdapter.setEnableLoadMore(true);
                binding.swipeLayout.setRefreshing(false);
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                mPropertyDetailsAdapter.setEnableLoadMore(true);
                binding.swipeLayout.setRefreshing(false);
            }
        });
    }

    private void loadMore() {
        viewModel.findPropertyRecord(pageIndex, selectCoinName, coinTypeStr, recordTypeStr, new OnRequestListener<CoinTransDetailsResult>() {
            @Override
            public void onSuccess(CoinTransDetailsResult coinTransDetailsResult) {
                boolean isRefresh = pageIndex == 1;
                mRecordsBeanList.addAll(coinTransDetailsResult.getData().getRecords());
                setData(isRefresh, coinTransDetailsResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                binding.swipeLayout.setRefreshing(false);
            }
        });
    }

    private void setData(boolean isRefresh, List data) {
        pageIndex++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mPropertyDetailsAdapter.setNewData(data);
            if (size == 0) {
                mPropertyDetailsAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvPropertyOrder);
            }
        } else {
            if (size > 0) {
                mPropertyDetailsAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mPropertyDetailsAdapter.loadMoreEnd(isRefresh);
        } else {
            mPropertyDetailsAdapter.loadMoreComplete();
        }
    }
}
