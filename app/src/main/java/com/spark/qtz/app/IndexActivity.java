package com.spark.qtz.app;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.api.pojo.UpdateBean;
import com.spark.qtz.app.emex.EmexFragment;
import com.spark.qtz.app.me.MeFragment;
import com.spark.qtz.app.quotes.QuotesFragment;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.ActivityIndexBinding;
import com.spark.qtz.util.AppUtils;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.wsclient.WsService;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.service.DaemonEnv;
import me.spark.mvvm.service.IntentWrapper;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/15
 * 描    述：Index
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_URL_INDEX)
public class IndexActivity extends BaseActivity<ActivityIndexBinding, IndexViewModel> {
    //账户监控
    private EmexFragment mEmexFragment;
    //运营统计
    private QuotesFragment mQuotesFragment;
    //个人中心
    private MeFragment mMeFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_index;
    }

    @Override
    public int initVariableId() {
        return BR.indexViewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.indexUC.clickPosition.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                setSelection(integer);
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, getColor(R.color.bg_main));
    }

    @Override
    public void initData() {
        //开启服务
        //DaemonEnv.startServiceMayBind(WsService.class);
        fragmentManager = getSupportFragmentManager();
        setSelection(0);
        checkVersion();
    }

    /**
     * 检测版本号
     */
    private void checkVersion() {
        try {
            if (StringUtils.isEmpty(Constant.updateInfoJson)) return;
            UpdateBean mUpdateBean = BaseApplication.gson.fromJson(Constant.updateInfoJson, UpdateBean.class);
            if (StringUtils.formatVersionCode(mUpdateBean.getData().getVersion()) >
                    StringUtils.formatVersionCode(AppUtils.getContextVersionName())) {
                AppUtils.updateApp(mUpdateBean);
            }
        } catch (Exception e) {
            LogUtils.e("checkVersion", e.toString());
        }
    }

    private void setSelection(int index) {
        try {
            // 每次选中之前先清楚掉上次的选中状态
            clearSelection();
            // 开启一个Fragment事务
            transaction = fragmentManager.beginTransaction();
            // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
            hideFragments(transaction);
            switch (index) {
                //Emex
                case 0:
                    binding.llTabEmex.setSelected(true);
                    if (mEmexFragment == null) {
                        mEmexFragment = new EmexFragment();
                        transaction.add(R.id.fragment_content, mEmexFragment);
                    } else {
                        transaction.show(mEmexFragment);
                    }
                    Constant.isIndexVisiable = true;
                    break;
                //行情
                case 1:
                    binding.llTabQuotes.setSelected(true);
                    if (mQuotesFragment == null) {
                        mQuotesFragment = new QuotesFragment();
                        transaction.add(R.id.fragment_content, mQuotesFragment);
                    } else {
                        transaction.show(mQuotesFragment);
                    }
                    Constant.isQoutesVisiable = true;
                    break;
                //我的
                case 2:
                    binding.llTabMe.setSelected(true);
                    if (mMeFragment == null) {
                        mMeFragment = new MeFragment();
                        transaction.add(R.id.fragment_content, mMeFragment);
                    } else {
                        transaction.show(mMeFragment);
                    }
                    break;
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mEmexFragment != null) {
            transaction.hide(mEmexFragment);
        }
        if (mQuotesFragment != null) {
            transaction.hide(mQuotesFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }

        Constant.isIndexVisiable = false;
        Constant.isQoutesVisiable = false;
        Constant.isTradeVisiable = false;
    }

    private void clearSelection() {
        binding.llTabEmex.setSelected(false);
        binding.llTabQuotes.setSelected(false);
        binding.llTabMe.setSelected(false);
    }

    public void onBackPressed() {
        IntentWrapper.onBackPressed(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constant.isHomeVisiable = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constant.isHomeVisiable = false;
    }
}
