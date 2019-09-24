package com.spark.qtz.app.me.safe.authentication.idcard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.databinding.FragmentIdCardBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.PhotoSelectUtils;
import com.spark.ucclient.SecurityClient;

import java.io.File;
import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.PermissionConstants;
import me.spark.mvvm.utils.PermissionUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：身份验证 - 身份证
 * 修订历史：
 * ================================================
 */
public class IdCardFragment extends BaseFragment<FragmentIdCardBinding, IdCardViewModel> {
    private PhotoSelectUtils mPhotoSelectUtils;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_id_card;
    }

    @Override
    public int initVariableId() {
        return BR.idCardViewModel;
    }

    @OnClick({R.id.img_auth_up,
            R.id.img_auth_down,
            R.id.img_auth_handheld})
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

        new XPopup.Builder(getActivity())
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
        mPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }
}
