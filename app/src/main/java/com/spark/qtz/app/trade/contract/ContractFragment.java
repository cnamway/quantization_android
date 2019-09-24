package com.spark.qtz.app.trade.contract;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.modulecfd.pojo.CfdAllThumbResult;
import com.example.modulecfd.pojo.CfdSymbolAllResult;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.spark.acclient.FinanceClient;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.contract.vp.commission.CommissionVPFragment;
import com.spark.qtz.app.trade.contract.vp.deal.DealVPFragment;
import com.spark.qtz.app.trade.contract.vp.position.PositionVPFragment;
import com.spark.qtz.app.trade.contract.vp.revoke.RevokeVPFragment;
import com.spark.qtz.app.trade.currency.adapter.MarketBuyAdapter;
import com.spark.qtz.app.trade.currency.adapter.MarketSellAdapter;
import com.spark.qtz.bean.TabEntity;
import com.spark.qtz.databinding.FragmentContractBinding;
import com.spark.qtz.ui.PointLengthFilter;
import com.spark.qtz.ui.popup.ContractBuyTypePopup;
import com.spark.qtz.ui.popup.ContractSymbolSelectPopup;
import com.spark.qtz.ui.popup.impl.OnPositionChooseListener;
import com.spark.qtz.ui.popup.impl.OnTypeChooseListener;
import com.spark.qtz.ui.tablayout.listener.CustomTabEntity;
import com.spark.qtz.ui.tablayout.listener.OnTabSelectListener;
import com.spark.modulespot.pojo.MarketSymbolResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ContractFragment extends BaseFragment<FragmentContractBinding, ContractViewModel> {
    private String[] mSeekBarTitles = {"10%", "20%", "50%", "100%"};
    private ContractSymbolSelectPopup mContractSymbolSelectPopup;
    private MarketSellAdapter mMarketSellAdapter;               //盘口 - 卖
    private MarketBuyAdapter mMarketBuyAdapter;                 //盘口 - 买
    private List<MarketSymbolResult.DataBean.AskBean> mMarketSellList = new ArrayList<>();
    private List<MarketSymbolResult.DataBean.BidBean> mMarketBuyList = new ArrayList<>();
    private final int defaultSize = 6;
    private PointLengthFilter mPriceFilter;                     //位数限制
    private String[] mTitles;
    private int[] mIconUnselectIds = {
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private int[] mIconSelectIds = {
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private PositionVPFragment mPositionVPFragment;
    private CommissionVPFragment mCommissionVPFragment;
    private DealVPFragment mDealVPFragment;
    private RevokeVPFragment mRevokeVPFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private int tabSelectPosition = 0;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_contract;
    }

    @Override
    public int initVariableId() {
        return BR.contractViewModel;
    }

    @Override
    public void initView() {
        binding.root.showLoading();
        mTitles = getResources().getStringArray(R.array.contract_tab);

        //SeekBar
        binding.seekBar.setTabData(mSeekBarTitles);
        binding.seekBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (viewModel.numberAvailableValue == 0) {
                    return;
                }
                switch (position) {
                    case 0:
                        binding.etNum.setText(DfUtils.numberFormat(Math.floor(viewModel.numberAvailableValue * 0.1), 0));
                        break;
                    case 1:
                        binding.etNum.setText(DfUtils.numberFormat(Math.floor(viewModel.numberAvailableValue * 0.2), 0));
                        break;
                    case 2:
                        binding.etNum.setText(DfUtils.numberFormat(Math.floor(viewModel.numberAvailableValue * 0.5), 0));
                        break;
                    case 3:
                        binding.etNum.setText(DfUtils.numberFormat(Math.floor(viewModel.numberAvailableValue), 0));
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        initTextChangedListener();

        //默认配置6个数据
        initMarketSymbolResultDefault();

        mMarketSellAdapter = new MarketSellAdapter(mMarketSellList);
        LinearLayoutManager layoutReverse = new LinearLayoutManager(getActivity());
        layoutReverse.setReverseLayout(true);
        binding.rvSell.setLayoutManager(layoutReverse);
        binding.rvSell.setAdapter(mMarketSellAdapter);
        mMarketSellAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mMarketSellList.get(position).getPrice() != 0) {
                    viewModel.updatePriceEt(MathUtils.getRundNumber(mMarketSellList.get(position).getPrice(), Constant.currencySymbolRate, null));
                }
            }
        });

        mMarketBuyAdapter = new MarketBuyAdapter(mMarketBuyList);
        binding.rvBuy.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvBuy.setAdapter(mMarketBuyAdapter);
        mMarketBuyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mMarketBuyList.get(position).getPrice() != 0) {
                    viewModel.updatePriceEt(MathUtils.getRundNumber(mMarketBuyList.get(position).getPrice(), Constant.currencySymbolRate, null));
                }
            }
        });


        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (App.getInstance().isAppLogin()) {
                    FinanceClient.getInstance().getCoinWallet("CFD");
                    if (viewModel.mCfdAllThumbResult != null) {
                        for (CfdAllThumbResult.DataBean bean : viewModel.mCfdAllThumbResult.getData()) {
                            if (bean.getScreenName().equals(viewModel.currentCfdSymbolAllResult.getScreenName())) {
                                EventBusUtils.postSuccessEvent(EvKey.cfdRefresh, BaseRequestCode.OK, viewModel.currentCfdSymbolAllResult.getScreenName(), bean.getClose());
                                break;
                            }
                        }
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });
        //vp
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconUnselectIds[i], mIconSelectIds[i]));
        }
        fragmentManager = getChildFragmentManager();
        binding.tab.setTabData(mTabEntities);
        binding.tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                tabSelectPosition = position;
                setSelection(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        //滑到底部加载更多
        binding.nestedScrollView.setOnScrollChangeListener(
                new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (!App.getInstance().isAppLogin()) return;
                        //判断是否滑到的底部
                        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                            EventBusUtils.postSuccessEvent(EvKey.contractSrollEnd, tabSelectPosition, "");
                        }
                    }

                });
    }

    private void setSelection(int index) {
        try {
            if (!App.getInstance().isAppLogin()) return;
            // 开启一个Fragment事务
            transaction = fragmentManager.beginTransaction();
            // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
            hideFragments(transaction);
            switch (index) {
                //仓位
                case 0:
                    if (mPositionVPFragment == null) {
                        mPositionVPFragment = PositionVPFragment.newInstance(viewModel.currentCfdSymbolAllResult.getScreenName(), viewModel.currentCfdSymbolAllResult.getSymbol(),0);
                        transaction.add(R.id.fragment_content, mPositionVPFragment);
                    } else {
                        transaction.show(mPositionVPFragment);
                    }
                    break;
                //当前委托
                case 1:
                    if (mCommissionVPFragment == null) {
                        mCommissionVPFragment = CommissionVPFragment.newInstance(viewModel.currentCfdSymbolAllResult.getScreenName(), viewModel.currentCfdSymbolAllResult.getSymbol(),0);
                        transaction.add(R.id.fragment_content, mCommissionVPFragment);
                    } else {
                        transaction.show(mCommissionVPFragment);
                    }
                    break;
                //已成交
                case 2:
                    if (mDealVPFragment == null) {
                        mDealVPFragment = DealVPFragment.newInstance(viewModel.currentCfdSymbolAllResult.getScreenName(), viewModel.currentCfdSymbolAllResult.getSymbol());
                        transaction.add(R.id.fragment_content, mDealVPFragment);
                    } else {
                        transaction.show(mDealVPFragment);
                    }
                    break;
                //已撤单
                case 3:
                    if (mRevokeVPFragment == null) {
                        mRevokeVPFragment = RevokeVPFragment.newInstance(viewModel.currentCfdSymbolAllResult.getScreenName(), viewModel.currentCfdSymbolAllResult.getSymbol());
                        transaction.add(R.id.fragment_content, mRevokeVPFragment);
                    } else {
                        transaction.show(mRevokeVPFragment);
                    }
                    break;
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mPositionVPFragment != null) {
            transaction.hide(mPositionVPFragment);
        }
        if (mCommissionVPFragment != null) {
            transaction.hide(mCommissionVPFragment);
        }
        if (mDealVPFragment != null) {
            transaction.hide(mDealVPFragment);
        }
        if (mRevokeVPFragment != null) {
            transaction.hide(mRevokeVPFragment);
        }
    }

    //重置盘口数据
    private void initMarketSymbolResultDefault() {
        mMarketSellList.clear();
        mMarketBuyList.clear();
        for (int i = 0; i < defaultSize; i++) {
            MarketSymbolResult.DataBean.AskBean askBean = new MarketSymbolResult.DataBean.AskBean();
            askBean.setType(1);
            mMarketSellList.add(i, askBean);
            MarketSymbolResult.DataBean.BidBean bidBean = new MarketSymbolResult.DataBean.BidBean();
            bidBean.setType(1);
            mMarketBuyList.add(i, bidBean);
        }
    }

    private void initTextChangedListener() {
        //数量监听
        binding.etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(s.toString())) {
                    viewModel.updateNumEt(s.toString());
                }
            }
        });

        //价格监听
        binding.etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(s.toString())) {
                    viewModel.updatePriceEt(s.toString());
                }
            }
        });
    }


    @Override
    public void loadLazyData() {
        if (!binding.root.isLoadingCurrentState()) binding.root.showLoading();
        viewModel.getCfdSymbolAll(getContext(), new OnRequestListener<CfdSymbolAllResult>() {
            @Override
            public void onSuccess(CfdSymbolAllResult cfdSymbolAllResult) {
                if (!binding.root.isContentCurrentState()) {
                    binding.root.showContent();
                }
                setSelection(tabSelectPosition);
            }

            @Override
            public void onFail(String message) {
                binding.root.showError(R.drawable.svg_no_data, "", message, getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadLazyData();
                    }
                });
            }
        });
    }

    @Override
    public void initViewObservable() {
        //币种切换
        viewModel.uc.mSymbolSwitchSingleLiveEvent.observe(this, new Observer<CfdSymbolAllResult.DataBean>() {
            @Override
            public void onChanged(@Nullable CfdSymbolAllResult.DataBean dataBean) {
                //重置盘口数据
                initMarketSymbolResultDefault();
                mMarketSellAdapter.notifyDataSetChanged();
                mMarketBuyAdapter.notifyDataSetChanged();
            }
        });

        //刷新轮询
        viewModel.uc.mCfdSymbolSingleLiveEvent.observe(this, new Observer<CfdAllThumbResult.DataBean>() {
            @Override
            public void onChanged(@Nullable CfdAllThumbResult.DataBean dataBean) {
                mPriceFilter = new PointLengthFilter(dataBean.getBaseCoinScreenScale());
                binding.etPrice.setFilters(new InputFilter[]{mPriceFilter});
            }
        });
        //盘口数据监听
        viewModel.uc.mMarketCfdSingleLiveEvent.observe(this, new Observer<MarketSymbolResult>() {
            @Override
            public void onChanged(@Nullable MarketSymbolResult marketSymbolResult) {
                int askSize = marketSymbolResult.getData().getAsk().size();
                for (int i = 0; i < defaultSize; i++) {
                    if (i < askSize) {
                        marketSymbolResult.getData().getAsk().get(i).setType(1);
                        mMarketSellList.set(i, marketSymbolResult.getData().getAsk().get(i));
                    } else {
                        MarketSymbolResult.DataBean.AskBean askBean = new MarketSymbolResult.DataBean.AskBean();
                        askBean.setType(1);
                        mMarketSellList.set(i, askBean);
                    }
                }

                int bidSize = marketSymbolResult.getData().getBid().size();
                for (int i = 0; i < defaultSize; i++) {
                    if (i < bidSize) {
                        marketSymbolResult.getData().getBid().get(i).setType(1);
                        mMarketBuyList.set(i, marketSymbolResult.getData().getBid().get(i));
                    } else {
                        MarketSymbolResult.DataBean.BidBean bidBean = new MarketSymbolResult.DataBean.BidBean();
                        bidBean.setType(1);
                        mMarketBuyList.set(i, bidBean);
                    }
                }

                mMarketSellAdapter.notifyDataSetChanged();
                mMarketBuyAdapter.notifyDataSetChanged();

            }
        });

        viewModel.uc.isLoginSingleLiveEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    setSelection(tabSelectPosition);
                }
            }
        });

        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean){
                    loadLazyData();
                }
            }
        });
    }

    @OnClick({R.id.symbol_select,
            R.id.contract_menu,
            R.id.buy_type,
            R.id.lever_select})
    public void OnClick(final View view) {
        switch (view.getId()) {
            //币种切换
            case R.id.symbol_select:
                if (mContractSymbolSelectPopup == null) {
                    mContractSymbolSelectPopup = (ContractSymbolSelectPopup) new XPopup.Builder(getActivity())
                            .atView(view)
                            .asCustom(new ContractSymbolSelectPopup(getActivity(), viewModel.allSymbol, new OnPositionChooseListener() {
                                @Override
                                public void onChoosePosition(int type) {
                                    viewModel.initLoadSymbol(viewModel.cfdSymbolAllResult.getData().get(type));
                                }
                            }));
                }
                mContractSymbolSelectPopup.toggle();
                break;
            //菜单
            case R.id.contract_menu:
//                new XPopup.Builder(getContext())
//                        .atView(view)
//                        .asCustom(new ContractMenuPopup(getContext(), new OnPositionChooseListener() {
//                            @Override
//                            public void onChoosePosition(int type) {
//                                viewModel.onMenuclick(type);
//                            }
//                        }))
//                        .show();
                break;
            //市价 - 限价
            case R.id.buy_type:
                new XPopup.Builder(getContext())
                        .atView(view)
                        .asCustom(new ContractBuyTypePopup(getContext(), new OnTypeChooseListener() {
                            @Override
                            public void onChooseType(int type, String content) {
                                if (!viewModel.buyTypeName.get().equals(content)) {
                                    viewModel.buyType.set(!viewModel.buyType.get());
                                }
                                viewModel.buyTypeName.set(content);
                            }
                        }))
                        .show();
                break;
            //杠杆选择
            case R.id.lever_select:
                new XPopup.Builder(getContext())
                        .maxHeight((int) (XPopupUtils.getWindowHeight(getContext()) * .85f))
                        .asBottomList(getString(R.string.select_multiplier), viewModel.allLever,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        viewModel.initLeverAmount(text);
                                    }
                                })
                        .show();
                break;
        }
    }


}
