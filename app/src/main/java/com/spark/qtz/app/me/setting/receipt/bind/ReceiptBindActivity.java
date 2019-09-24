package com.spark.qtz.app.me.setting.receipt.bind;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityReceiptBindBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.PhotoSelectUtils;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.otcclient.PayControlClient;
import com.spark.otcclient.pojo.PayListBean;

import java.io.File;
import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.PermissionConstants;
import me.spark.mvvm.utils.PermissionUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_ME_RECEIPT_BIND)
public class ReceiptBindActivity extends BaseActivity<ActivityReceiptBindBinding, ReceiptBindViewModel> {
    @Autowired(name = "typeBean")
    PayListBean.DataBean typeBean;
    @Autowired
    String type;

    private TitleBean mTitleModel;
    private PhotoSelectUtils mPhotoSelectUtils;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_receipt_bind;
    }

    @Override
    public int initVariableId() {
        return BR.receiptBindViewModel;
    }

    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.receiptBindTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        binding.receiptBindTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.receiptBindTitle.titleRootLeft);

        viewModel.initType(this, type, typeBean);
    }

    @OnClick({R.id.receipt_bind_qrcode})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.receipt_bind_qrcode:
                chooseQrCod(view);
                break;
        }
    }

    private void chooseQrCod(final View view) {
        mPhotoSelectUtils = new PhotoSelectUtils(this, new PhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                showDialog(App.getInstance().getString(R.string.str_uploading));
                PayControlClient.getInstance().uploadQRCode(outputFile, view.getWidth(), view.getHeight());
            }
        }, true);

        new XPopup.Builder(this)
                .asBottomList(App.getInstance().getString(R.string.please_choose), new String[]{App.getInstance().getString(R.string.take_photo), App.getInstance().getString(R.string.album)},
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
        mPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }
}
