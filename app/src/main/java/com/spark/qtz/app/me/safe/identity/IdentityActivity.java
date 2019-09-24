package com.spark.qtz.app.me.safe.identity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.safe.identity.idcard.IdAuthFragment;
import com.spark.qtz.app.me.safe.identity.passport.PassPortAuthFragment;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityIdentityBinding;
import com.spark.qtz.ui.adapter.SlideTabPagerAdapter;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.ucclient.pojo.AuthErrorResult;
import com.spark.ucclient.pojo.AuthInfoEntity;

import java.util.ArrayList;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-18
 * 描    述：
 * 修订历史：
 * ================================================
 */
//@Route(path = ARouterPath.ACTIVITY_ME_AUTHENTICATION)
public class IdentityActivity extends BaseActivity<ActivityIdentityBinding, IdentityViewModel> {
    private TitleBean mTitleModel;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private SlideTabPagerAdapter mAdapter;
    private AuthInfoEntity mAuthInfoEntity;
    private AuthErrorResult mAuthErrorResult;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_identity;
    }

    @Override
    public int initVariableId() {
        return BR.identityViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.authenticationTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.authenticationTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.authenticationTitle.titleRootLeft);

    }

    @Override
    public void initData() {
        binding.root.showLoading();
        mFragments.clear();
        //是否身份认证过
        if (App.getInstance().getCurrentUser().getCertifiedType() == null) {
            mTitles = getResources().getStringArray(R.array.auth_tab);
            mFragments.add(new IdAuthFragment());
            mFragments.add(new PassPortAuthFragment());
            initAdapter();
            if (!binding.root.isContentCurrentState())
                binding.root.showContent();
        } else {
            viewModel.initAuthInfo(new OnRequestListener<AuthInfoEntity>() {
                @Override
                public void onSuccess(AuthInfoEntity authInfoEntity) {
                    mAuthInfoEntity = authInfoEntity;
                    //审核未通过
                    if (authInfoEntity.getData().getAuditStatus() == 2) {
                        viewModel.initAuthErrorInfo(new OnRequestListener<AuthErrorResult>() {
                            @Override
                            public void onSuccess(AuthErrorResult authErrorResult) {
                                mAuthErrorResult = authErrorResult;
                                mTitles = getResources().getStringArray(R.array.auth_tab);
                                if (App.getInstance().getCurrentUser().getCertifiedType().equals("0")) {
                                    mFragments.add(IdAuthFragment.newInstance(mAuthInfoEntity, mAuthErrorResult));
                                    mFragments.add(new PassPortAuthFragment());
                                } else {
                                    mFragments.add(new IdAuthFragment());
                                    mFragments.add(PassPortAuthFragment.newInstance(mAuthInfoEntity, mAuthErrorResult));
                                }
                                initAdapter();
                                if (!binding.root.isContentCurrentState())
                                    binding.root.showContent();
                            }

                            @Override
                            public void onFail(String message) {
                                binding.root.showError(R.drawable.svg_no_data, App.getInstance().getApplicationContext().getString(R.string.str_http_error), message, App.getInstance().getApplicationContext().getString(R.string.retry), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        initData();
                                    }
                                });
                            }
                        });
                    } else {
                        if (App.getInstance().getCurrentUser().getCertifiedType().equals("0")) {
                            mTitles = getResources().getStringArray(R.array.auth_id_tab);
                            mFragments.add(IdAuthFragment.newInstance(mAuthInfoEntity, mAuthErrorResult));
                        } else {
                            mTitles = getResources().getStringArray(R.array.auth_passport_tab);
                            mFragments.add(PassPortAuthFragment.newInstance(mAuthInfoEntity, mAuthErrorResult));
                        }
                        initAdapter();
                        if (!binding.root.isContentCurrentState()) binding.root.showContent();
                    }
                }

                @Override
                public void onFail(String message) {
                    binding.root.showError(R.drawable.svg_no_data, App.getInstance().getApplicationContext().getString(R.string.str_http_error), message, App.getInstance().getApplicationContext().getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
                }
            });
        }
    }

    private void initAdapter(){
        mAdapter = new SlideTabPagerAdapter(getSupportFragmentManager(),
                mFragments, mTitles);
        binding.vp.setAdapter(mAdapter);
        binding.tab.setViewPager(binding.vp);
    }
}
