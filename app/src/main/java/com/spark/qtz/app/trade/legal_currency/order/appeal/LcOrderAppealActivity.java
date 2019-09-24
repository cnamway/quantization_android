package com.spark.qtz.app.trade.legal_currency.order.appeal;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.spark.qtz.databinding.ActivityLcOrderAppealBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.PhotoSelectUtils;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.otcclient.LcTradeClient;
import com.spark.otcclient.pojo.LcOrderResult;
import com.spark.otcclient.pojo.OrderAppealBean;

import java.io.File;
import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.PermissionConstants;
import me.spark.mvvm.utils.PermissionUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/18
 * 描    述：
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_TRADE_LC_ORCER_APPEAL)
public class LcOrderAppealActivity extends BaseActivity<ActivityLcOrderAppealBinding, LcOrderAppealViewModel> {
    @Autowired(name = "orderDetails")
    LcOrderResult.DataBean.RecordsBean orderDetailsBean;

    private TitleBean mTitleModel;
    private PhotoSelectUtils mPhotoSelectUtils;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_lc_order_appeal;
    }

    @Override
    public int initVariableId() {
        return BR.lcOrderAppealViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.orderTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleName(App.getInstance().getString(R.string.str_order_appeal));
        mTitleModel.setShowRightTV(true);
        mTitleModel.setRightTV(App.getInstance().getString(R.string.commit));
        binding.orderTitle.titleLeftImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_cancel));
        binding.orderTitle.titleRightTv.setTextColor(ContextCompat.getColor(this, R.color.base_dark));
        binding.orderTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.orderTitle.titleRootLeft, binding.orderTitle.titleRootRight);
    }

    @Override
    protected void onTitleRightClick() {
        if (StringUtils.isEmpty(viewModel.appealContent.get())) {
            Toasty.showError(App.getInstance().getString(R.string.str_desc));
            return;
        }
        if (StringUtils.isEmpty(viewModel.imgOne.get())
                && StringUtils.isEmpty(viewModel.imgTwo.get())
                && StringUtils.isEmpty(viewModel.imgThree.get())) {
            Toasty.showError(App.getInstance().getString(R.string.str_at_least_one));
            return;
        }

        OrderAppealBean orderAppealBean = new OrderAppealBean();
        orderAppealBean.setOrderId(orderDetailsBean.getOrderSn());
        orderAppealBean.setRemark(viewModel.appealContent.get());
        orderAppealBean.setPicturesOne(viewModel.imgOne.get());
        orderAppealBean.setPicturesTwo(viewModel.imgTwo.get());
        orderAppealBean.setPicturesThree(viewModel.imgThree.get());

        showDialog(App.getInstance().getString(R.string.loading));
        LcTradeClient.getInstance().orderAppeal(orderAppealBean);
    }

    @OnClick({R.id.pic_one,
            R.id.pic_two,
            R.id.pic_three})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pic_one:
                selectPic(0, view);
                break;
            case R.id.pic_two:
                selectPic(1, view);
                break;
            case R.id.pic_three:
                selectPic(2, view);
                break;
        }
    }


    /**
     * 选择图片
     *
     * @param type
     */
    private void selectPic(final int type, final View view) {
        mPhotoSelectUtils = new PhotoSelectUtils(this, new PhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                showDialog(App.getInstance().getString(R.string.str_uploading));
                LcTradeClient.getInstance().uploadIdCardPic(outputFile, type, view.getWidth(), view.getHeight());
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
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }
}
