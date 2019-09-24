package com.spark.qtz.app.emex;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.spark.acclient.pojo.CoinTransDetailsResult;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.emex.adapter.IndexAccountAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.FragmentEmexBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.quantizationclient.pojo.IndexAccountResult;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.StringUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/18
 * 描    述：账户监控
 * 修订历史：
 * ================================================
 */
public class EmexFragment extends BaseFragment<FragmentEmexBinding, EmexViewModel> {
    private TitleBean mTitleModel;
    private int pageIndex = 1;
    private IndexAccountAdapter mAdapter;
    private List<IndexAccountResult.DataBean.RecordsBean> mRecordsBeanList = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_emex;
    }

    @Override
    public int initVariableId() {
        return BR.emexViewModel;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        viewModel.isVisible2User = !hidden;
    }

    @Override
    public void initView() {
        super.initView();
        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleNameLeft(getResources().getString(R.string.str_index_1));
        mTitleModel.setShowLeftImg(false);
        binding.title.setViewTitle(mTitleModel);

        mAdapter = new IndexAccountAdapter(mRecordsBeanList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_EMEX_ACCOUNT_DETAILS)
                        //.withParcelable("dataBean", mRecordsBeanList.get(position))
                        .navigation();
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

        initEdit();
    }

    /**
     * 回车键改为搜索
     */
    private void initEdit() {
        binding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    String searchContext = binding.etSearch.getText().toString().trim();
                    if (StringUtils.isNotEmpty(searchContext)) {
                        refresh();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    initData();
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        refresh();
    }

    /**
     * 下拉刷新
     */
    private void refresh() {
        binding.swipeLayout.setRefreshing(true);

        pageIndex = 1;
        viewModel.loadPageDateFilter(pageIndex, new OnRequestListener<IndexAccountResult>() {
            @Override
            public void onSuccess(IndexAccountResult result) {
                mRecordsBeanList.clear();
                mRecordsBeanList.addAll(result.getData().getRecords());
                mAdapter.notifyDataSetChanged();
                setData(true, result.getData().getRecords());
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
        viewModel.loadPageDateFilter(pageIndex, new OnRequestListener<CoinTransDetailsResult>() {
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
            mAdapter.setNewData(data);
            if (size == 0) {
                mAdapter.setEmptyView(R.layout.view_rv_empty, binding.recyclerView);
            }
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
    }

}
