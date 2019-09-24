package com.spark.qtz.app.me.safe.authentication;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.me.safe.authentication.idcard.IdCardFragment;
import com.spark.qtz.app.me.safe.authentication.passport.PassPortFragment;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityAuthenticationBinding;
import com.spark.qtz.ui.adapter.SlideTabPagerAdapter;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.PhotoSelectUtils;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.ucclient.SecurityClient;
import com.spark.ucclient.pojo.AuthInfoEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.PermissionConstants;
import me.spark.mvvm.utils.PermissionUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：身份认证
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_ME_AUTHENTICATION)
public class AuthenticationActivity extends BaseActivity<ActivityAuthenticationBinding, AuthenticationViewModel> {
    private TitleBean mTitleModel;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private SlideTabPagerAdapter mAdapter;
    private PhotoSelectUtils mPhotoSelectUtils;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_authentication;
    }

    @Override
    public int initVariableId() {
        return BR.authenticationViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.authenticationTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        mTitles = getResources().getStringArray(R.array.auth_tab);
        //TitleSet
        mTitleModel = new TitleBean();

        //是否身份认证过
        if (App.getInstance().getCurrentUser().getCertifiedType() == null) {
            mFragments.add(new IdCardFragment());
            mFragments.add(new PassPortFragment());

            mAdapter = new SlideTabPagerAdapter(getSupportFragmentManager(),
                    mFragments, mTitles);
            binding.authenticationVp.setAdapter(mAdapter);
            binding.authenticationTab.setViewPager(binding.authenticationVp);
            binding.llNoAuth.setVisibility(View.VISIBLE);
        } else {
            binding.llAuthed.showLoading();
            binding.llBack.setVisibility(App.getInstance().getCurrentUser().getCertifiedType().equals("0") ? View.VISIBLE : View.GONE);
            mTitleModel.setTitleName(App.getInstance().getCurrentUser().getCertifiedType().equals("0") ?
                    getString(R.string.id_card) : getString(R.string.passport_num));
            viewModel.initAuthInfo(new OnRequestListener<AuthInfoEntity>() {
                @Override
                public void onSuccess(AuthInfoEntity authInfoEntity) {
                    binding.llAuthed.setVisibility(View.VISIBLE);
                    binding.llAuthed.showContent();
                }

                @Override
                public void onFail(String message) {
                    Toasty.showError(message);
                }
            });
        }
        binding.authenticationTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.authenticationTitle.titleRootLeft);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isSubmitErrorEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.imgAuthUp.setClickable(aBoolean);
                binding.imgAuthDown.setClickable(aBoolean);
                binding.imgAuthHandheld.setClickable(aBoolean);
            }
        });

        viewModel.uc.isRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    initView();
                }
            }
        });
    }

    @OnClick({R.id.img_auth_up,
            R.id.img_auth_down,
            R.id.img_auth_handheld,
            R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_auth_up:
                selectPic(0, view);
                break;
            case R.id.img_auth_down:
                selectPic(1, view);
                break;
            case R.id.img_auth_handheld:
                selectPic(2, view);
                break;
            case R.id.submit:
                viewModel.upload();
                break;
        }
    }

    /**
     * 选择图片
     *
     * @param type 0 - 正面   1 - 反面  2 - 手持
     */
    private void selectPic(final int type, final View view) {
        mPhotoSelectUtils = new PhotoSelectUtils(this, new PhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                showDialog(getString(R.string.loading));
                SecurityClient.getInstance().uploadIdCardPic(0, outputFile, type, view.getWidth(), view.getHeight());
            }
        }, true);

        new XPopup.Builder(this)
                .asBottomList(getString(R.string.please_choose), new String[]{getString(R.string.take_photo), getString(R.string.album)},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                choosePicOrigin(position);
                            }
                        })
                .show();
    }

    /**
     * 选择图片来源
     *
     * @param position 0-拍照  1-图库
     */
    private void choosePicOrigin(final int position) {
        switch (position) {
            case 0:
                PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE).callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        mPhotoSelectUtils.takePhoto();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        LogUtils.e(permissionsDenied);
                        Toasty.showError(getString(R.string.camera_permission));
                    }
                }).request();
                break;
            case 1:
                PermissionUtils.permission(PermissionConstants.STORAGE).callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        mPhotoSelectUtils.selectPhoto();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Toasty.showError(getString(R.string.storage_permission));
                    }
                }).request();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        if (null != mPhotoSelectUtils)
            mPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }
}
