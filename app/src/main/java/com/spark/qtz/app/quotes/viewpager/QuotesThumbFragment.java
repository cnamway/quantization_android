package com.spark.qtz.app.quotes.viewpager;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.quotes.adapter.QuotesThumbAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentQuotesThumbBinding;
import com.spark.modulespot.pojo.AllThumbResult;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QuotesThumbFragment extends BaseFragment<FragmentQuotesThumbBinding, QuotesThumbViewModel> {
    private static final String TYPE = "type";
    private List<AllThumbResult.DataBean> mThumbList = new ArrayList<>();
    private QuotesThumbAdapter mQuotesThumbAdapter;

    public static QuotesThumbFragment newInstance(String quotesType) {
        QuotesThumbFragment quotesVPFragment = new QuotesThumbFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, quotesType);
        quotesVPFragment.setArguments(bundle);
        return quotesVPFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_quotes_thumb;
    }

    @Override
    public void initView() {
        viewModel.initType(getArguments().getString(TYPE));
    }

    @Override
    public int initVariableId() {
        return BR.quotesThumbViewModel;
    }

    @Override
    public void loadLazyData() {
        binding.thumbRoot.showLoading();

        mQuotesThumbAdapter = new QuotesThumbAdapter(mThumbList);
        binding.thumbRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.thumbRv.setAdapter(mQuotesThumbAdapter);
        mQuotesThumbAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_QUOTES_KLINE_HTTP)
                        .withParcelable("quotesThumbClick", mThumbList.get(position))
                        .navigation();
            }
        });

        if (getArguments().getString(TYPE).equals(App.getInstance().getString(R.string.favorites))
                && !App.getInstance().isAppLogin()) {
            binding.thumbRoot.showError(R.drawable.svg_no_data, getString(R.string.no_login), getString(R.string.log2view), getString(R.string.login), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                            .navigation();
                }
            });
        }
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.mAllThumbResultSingleLiveEvent.observe(this, new Observer<List<AllThumbResult.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<AllThumbResult.DataBean> dataBeans) {
                if (!isVisibleToUser) return;
                mThumbList.clear();
                mThumbList.addAll(dataBeans);
                mQuotesThumbAdapter.notifyDataSetChanged();
                if (mThumbList.isEmpty()) {
                    mQuotesThumbAdapter.setEmptyView(R.layout.view_rv_empty, binding.thumbRv);
                }
                if (binding.thumbRoot.isLoadingCurrentState() || binding.thumbRoot.isErrorCurrentState() || binding.thumbRoot.isEmptyCurrentState())
                    binding.thumbRoot.showContent();
            }
        });

        viewModel.uc.isLogin.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (!getArguments().getString(TYPE).equals(App.getInstance().getString(R.string.favorites))) return;
                if (aBoolean) {
                    binding.thumbRoot.showLoading();
                } else {
                    binding.thumbRoot.showError(R.drawable.svg_no_data, getString(R.string.no_login), getString(R.string.log2view), getString(R.string.login), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.thumbRoot.showLoading();
                            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                                    .navigation();
                        }
                    });
                }
            }
        });
    }
}
