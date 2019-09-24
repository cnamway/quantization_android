package com.spark.qtz.app.invite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mylhyl.zxing.scanner.encode.QREncode;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.FragmentInviteBinding;
import com.spark.qtz.ui.toast.Toasty;

import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.ImageUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.PermissionConstants;
import me.spark.mvvm.utils.PermissionUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class InviteFragment extends BaseFragment<FragmentInviteBinding, InviteViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_invite;
    }

    @Override
    public int initVariableId() {
        return BR.inviteViewModel;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        viewModel.isVisible2User = !hidden;
    }

    @Override
    public void initView() {
        viewModel.initContext(getActivity());
    }

    @Override
    public void initData() {
        viewModel.loadDate();
    }

    @OnClick({R.id.invite_friend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invite_friend:
                if (!App.getInstance().isAppLogin()) {
                    ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                            .navigation();
                    return;
                }

                binding.websiteUrl.setText(BaseHost.HOST.substring(0, BaseHost.HOST.length() - 1));
                binding.inviteCode.setText(App.getInstance().getCurrentUser().getPromotionCode());
                final Bitmap mBitmap = new QREncode.Builder(getActivity())
                        .setColor(Color.BLACK)
                        .setContents(Constant.inviteUrl + Constant.inviteUrlSub + App.getInstance().getCurrentUser().getPromotionCode())
                        .setMargin(2)
                        .build().encodeAsBitmap();
                binding.qrCode.setImageBitmap(mBitmap);
                PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE).callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), ImageUtils.view2Bitmap(binding.posterRoot), null, null));

                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        shareIntent.setType("image/*");
                        getActivity().startActivity(Intent.createChooser(shareIntent, getActivity().getString(R.string.share_to)));
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        LogUtils.e(permissionsDenied);
                        Toasty.showError(getActivity().getString(R.string.camera_permission));
                    }
                }).request();
                break;
        }
    }
}
