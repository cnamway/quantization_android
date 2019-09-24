package com.spark.qtz.app.quotes.viewpager;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
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
import com.spark.qtz.databinding.FragmentQuotesReceiveBinding;
import com.spark.modulespot.B2BWsClient;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.wsclient.pojo.B2BThumbBean;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.SPUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QuotesReceiveFragment extends BaseFragment<FragmentQuotesReceiveBinding, QuotesReceiveViewModel> {
    private static final String TYPE = "type";
    private List<AllThumbResult.DataBean> mThumbList = new ArrayList<>();
    private QuotesThumbAdapter mQuotesThumbAdapter;
    private AllThumbResult firstAllThumbResult;

    public static QuotesReceiveFragment newInstance(String quotesType) {
        QuotesReceiveFragment quotesVPFragment = new QuotesReceiveFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, quotesType);
        quotesVPFragment.setArguments(bundle);
        return quotesVPFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_quotes_receive;
    }

    @Override
    public void initView() {
        viewModel.initType(getArguments().getString(TYPE));
    }

    @Override
    public int initVariableId() {
        return BR.quotesReceiveViewModel;
    }

    @Override
    public void loadLazyData() {
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
        if (!TextUtils.isEmpty(Constant.b2bKlinePushJson)) {
            firstAllThumbResult = App.gson.fromJson(Constant.b2bKlinePushJson, AllThumbResult.class);
            initFirstDate();
        } else {
            getFirstHttpData();
        }
    }

    private void getFirstHttpData() {
        if (!binding.thumbRoot.isLoadingCurrentState())
            binding.thumbRoot.showLoading();
        viewModel.getFirstHttpDate(new OnRequestListener<AllThumbResult>() {
            @Override
            public void onSuccess(AllThumbResult allThumbResult) {
                firstAllThumbResult = allThumbResult;
                initFirstDate();
            }

            @Override
            public void onFail(String message) {
                binding.thumbRoot.showError(R.drawable.svg_no_network, getString(R.string.network_abnormal), message, getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFirstHttpData();
                    }
                });
            }
        });
    }

    private void initFirstDate() {
        if (!binding.thumbRoot.isLoadingCurrentState())
            binding.thumbRoot.showLoading();
        mThumbList.clear();
        switch (viewModel.coinType) {
            case 0:
                if (App.getInstance().isAppLogin()) {
                    for (String favor : SPUtils.getInstance().getFavorFindList()) {
                        for (AllThumbResult.DataBean dataBean : firstAllThumbResult.getData()) {
                            if (dataBean.getSymbol().equals(favor)) {
                                mThumbList.add(dataBean);
                            }
                        }
                    }
                }
                break;
            default:
                for (AllThumbResult.DataBean dataBean : firstAllThumbResult.getData()) {
                    if (dataBean.getSymbol().endsWith(getArguments().getString(TYPE))) {
                        mThumbList.add(dataBean);
                    }
                }
                break;
        }
        mQuotesThumbAdapter.notifyDataSetChanged();
        if (mThumbList.isEmpty()) {
            mQuotesThumbAdapter.setEmptyView(R.layout.view_rv_empty, binding.thumbRv);
        }
        if (!binding.thumbRoot.isContentCurrentState())
            binding.thumbRoot.showContent();
        viewModel.isFirstHttpLoadSuccess = true;
        B2BWsClient.getInstance().monitorB2BKlinePush();
    }


    @Override
    public void initViewObservable() {
        viewModel.uc.httpBean.observe(this, new Observer<List<AllThumbResult.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<AllThumbResult.DataBean> dataBeans) {
                if (!isVisibleToUser) return;
                LogUtils.e("http降级", "http load.....");
                mThumbList.clear();
                mThumbList.addAll(dataBeans);
                mQuotesThumbAdapter.notifyDataSetChanged();
                if (mThumbList.isEmpty()) {
                    mQuotesThumbAdapter.setEmptyView(R.layout.view_rv_empty, binding.thumbRv);
                }
                if (!binding.thumbRoot.isContentCurrentState())
                    binding.thumbRoot.showContent();
            }
        });

        viewModel.uc.wsBean.observe(this, new Observer<B2BThumbBean>() {
            @Override
            public void onChanged(@Nullable B2BThumbBean b2BThumbBean) {
                if (!isVisibleToUser || null == firstAllThumbResult) return;
                LogUtils.e("http降级", "ws load.....");
                switch (viewModel.coinType) {
                    case 0:
                        mThumbList.clear();
                        if (App.getInstance().isAppLogin()) {
                            for (String favor : SPUtils.getInstance().getFavorFindList()) {
                                for (AllThumbResult.DataBean dataBean : firstAllThumbResult.getData()) {
                                    if (dataBean.getSymbol().equals(favor)) {
                                        for (B2BThumbBean.DateBean thumbBean : b2BThumbBean.getDate()) {
                                            if (thumbBean.getSymbol().equals(favor)){
                                                dataBean.setVolume(thumbBean.getVolume());
                                                dataBean.setHigh(thumbBean.getHigh());
                                                dataBean.setChg(thumbBean.getChg());
                                                dataBean.setCnyLegalAsset(thumbBean.getCnyLegalAsset());
                                                dataBean.setLow(thumbBean.getLow());
                                                dataBean.setClose(thumbBean.getClose());
                                                dataBean.setTurnover(thumbBean.getTurnover());
                                                mThumbList.add(dataBean);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        for (B2BThumbBean.DateBean dateBean : b2BThumbBean.getDate()) {
                            for (int i = 0; i < mThumbList.size(); i++) {
                                if (mThumbList.get(i).getSymbol().equals(dateBean.getSymbol())) {
                                    mThumbList.get(i).setVolume(dateBean.getVolume());
                                    mThumbList.get(i).setHigh(dateBean.getHigh());
                                    mThumbList.get(i).setChg(dateBean.getChg());
                                    mThumbList.get(i).setCnyLegalAsset(dateBean.getCnyLegalAsset());
                                    mThumbList.get(i).setLow(dateBean.getLow());
                                    mThumbList.get(i).setClose(dateBean.getClose());
                                    mThumbList.get(i).setTurnover(dateBean.getTurnover());
                                }
                            }
                        }
                        break;
                }
                mQuotesThumbAdapter.notifyDataSetChanged();

                if (mThumbList.isEmpty()) {
                    mQuotesThumbAdapter.setEmptyView(R.layout.view_rv_empty, binding.thumbRv);
                }
                if (!binding.thumbRoot.isContentCurrentState())
                    binding.thumbRoot.showContent();
            }
        });
        viewModel.uc.isLogin.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (!getArguments().getString(TYPE).equals(App.getInstance().getString(R.string.favorites)))
                    return;
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
