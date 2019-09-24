package com.spark.qtz.app.trade.contract.revoke;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.modulecfd.pojo.CfdRevokeResult;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.contract.adapter.CfdRevokeAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentCfdRevokeBinding;
import com.spark.qtz.ui.toast.Toasty;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdRevokeFragment extends BaseFragment<FragmentCfdRevokeBinding, CfdRevokeViewModel> {
    private static final String SYMBOL = "symbol";
    private int pageNo = 1;
    private String mSymbol;

    private CfdRevokeAdapter mCfdRevokeAdapter;
    private List<CfdRevokeResult.DataBean.RecordsBean> mDataBeanList = new ArrayList<>();

    public static CfdRevokeFragment newInstance(String quotesType) {
        CfdRevokeFragment cfdRevokeFragment = new CfdRevokeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SYMBOL, quotesType);
        cfdRevokeFragment.setArguments(bundle);
        return cfdRevokeFragment;
    }


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_cfd_revoke;
    }

    @Override
    public int initVariableId() {
        return BR.cfdRevokeViewModel;
    }

    @Override
    public void loadLazyData() {
        if (App.getInstance().isAppLogin()) {
            refresh();
        } else {
            initLogin();
        }
    }

    @Override
    public void initView() {
        mSymbol = getArguments().getString(SYMBOL);

        mCfdRevokeAdapter = new CfdRevokeAdapter(mDataBeanList);
        binding.rvCfdRevoke.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvCfdRevoke.setAdapter(mCfdRevokeAdapter);
        mCfdRevokeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_CFD_DETAILS)
                        .withInt("type", 3)
                        .withParcelable("revokeBean", mDataBeanList.get(position))
                        .navigation();
            }
        });
    }

    @Override
    public void initViewObservable() {
        //刷新
        viewModel.uc.mRefresh.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String symbol) {
                mSymbol = symbol;
                refresh();
            }
        });

        viewModel.uc.loadMore.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                loadMore();
            }
        });


        //登录状态
        viewModel.uc.mLoginStatue.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    refresh();
                } else {
                    initLogin();
                }
            }
        });

    }

    /**
     * 下拉刷新
     */
    private void refresh() {
        if (viewModel.isLoadDate) return;
        pageNo = 1;
        viewModel.loadRevokeData(mSymbol, pageNo, new OnRequestListener<CfdRevokeResult>() {
            @Override
            public void onSuccess(CfdRevokeResult cfdRevokeResult) {
                if (!binding.root.isContentCurrentState()) {
                    binding.root.showContent();
                }
                mDataBeanList.clear();
                mDataBeanList.addAll(cfdRevokeResult.getData().getRecords());
                setData(true, mDataBeanList);
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
            }
        });
    }

    /**
     * 上拉加载
     */
    private void loadMore() {
        if (viewModel.isLoadDate) return;
        viewModel.loadRevokeData(mSymbol, pageNo, new OnRequestListener<CfdRevokeResult>() {
            @Override
            public void onSuccess(CfdRevokeResult cfdRevokeResult) {
                boolean isRefresh = pageNo == 1;
                mDataBeanList.addAll(cfdRevokeResult.getData().getRecords());
                setData(isRefresh, cfdRevokeResult.getData().getRecords());
            }

            @Override
            public void onFail(String message) {
                mCfdRevokeAdapter.loadMoreFail();
            }
        });
    }

    /**
     * * @param isRefresh
     *
     * @param data
     */
    private void setData(boolean isRefresh, List data) {
        pageNo++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mCfdRevokeAdapter.setNewData(data);
            if (size == 0) {
                mCfdRevokeAdapter.setEmptyView(R.layout.view_rv_empty, binding.rvCfdRevoke);
            }
        } else {
            if (size > 0) {
                mCfdRevokeAdapter.addData(data);
            }
        }
        viewModel.isLoadDate = false;
    }

    private void initLogin() {
        binding.root.showError(R.drawable.svg_no_data, getString(R.string.no_login), getString(R.string.log2view), getString(R.string.login), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
            }
        });
    }
}
