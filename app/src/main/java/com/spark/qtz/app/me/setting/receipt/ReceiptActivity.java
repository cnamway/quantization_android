package com.spark.qtz.app.me.setting.receipt;

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
import com.spark.qtz.app.me.setting.receipt.adapter.PayWayAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityReceiptBinding;
import com.spark.qtz.ui.popup.TradePwdPopup;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.otcclient.PayControlClient;
import com.spark.otcclient.pojo.PayListBean;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：收款设置
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_RECEIPT)
public class ReceiptActivity extends BaseActivity<ActivityReceiptBinding, ReceiptViewModel> {
    private TitleBean mTitleModel;
    private List<PayListBean.DataBean> mDatas = new ArrayList<>();
    private PayWayAdapter mAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_receipt;
    }

    @Override
    public int initVariableId() {
        return BR.receiptViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.receiptTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setShowRightImg(true);
        binding.receiptTitle.titleRightImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_add));
        binding.receiptTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.receiptTitle.titleRootLeft, binding.receiptTitle.titleRootRight);

        //下拉刷新
        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        mAdapter = new PayWayAdapter(mDatas);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PayListBean.DataBean dataBean = (PayListBean.DataBean) adapter.getItem(position);
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_RECEIPT_BIND)
                        .withString("type", dataBean.getPayType())
                        .withParcelable("typeBean", dataBean)
                        .navigation();
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivStatus:
                        PayListBean.DataBean orderInTransit = (PayListBean.DataBean) adapter.getItem(position);
                        if (orderInTransit.getStatus() == 1) {
                            PayControlClient.getInstance().payTypeUpdate(orderInTransit.getId(), 0);
                        } else {
                            PayControlClient.getInstance().payTypeUpdate(orderInTransit.getId(), 1);
                        }
                        break;
                }
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                PayListBean.DataBean payWaySetting = (PayListBean.DataBean) adapter.getItem(position);
                showDialog(payWaySetting);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
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
        ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_RECEIPT_ADD)
                .navigation();
    }

    private void refresh() {
        binding.swipeLayout.setRefreshing(true);
        viewModel.getList(new OnRequestListener<PayListBean>() {
            @Override
            public void onSuccess(PayListBean coinAddressListBean) {
                binding.swipeLayout.setRefreshing(false);
                mDatas.clear();
                mDatas.addAll(coinAddressListBean.getData());
                if (!mDatas.isEmpty()) {
                    mAdapter.update();
                } else {
                    mAdapter.setEmptyView(R.layout.view_rv_empty, binding.recyclerView);
                }
            }

            @Override
            public void onFail(String message) {
                Toasty.showError(message);
                binding.swipeLayout.setRefreshing(false);
                mAdapter.setEmptyView(R.layout.view_rv_empty, binding.recyclerView);
            }
        });
    }

    /**
     * 提示框-删除
     */
    private void showDialog(final PayListBean.DataBean payWaySetting) {
        new XPopup.Builder(this)
                .autoOpenSoftInput(true)
                .asCustom(new TradePwdPopup(this, App.getInstance().getString(R.string.str_confirm_delete), new OnEtContentListener() {
                    @Override
                    public void onCEtContentInput(String content) {
                        showKeyboard(false);
                        showDialog(App.getInstance().getString(R.string.str_deleteing));
                        PayControlClient.getInstance().payTypeDelete(payWaySetting.getId(), content);
                    }
                }))
                .show();
    }
}
