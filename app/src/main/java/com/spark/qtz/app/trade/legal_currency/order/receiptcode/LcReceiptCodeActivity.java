package com.spark.qtz.app.trade.legal_currency.order.receiptcode;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.ActivityLcReceiptCodeBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;

import java.util.List;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.utils.ImageUtils;
import me.spark.mvvm.utils.PermissionConstants;
import me.spark.mvvm.utils.PermissionUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/18
 * 描    述：
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_TRADE_LC_ORCER_RECEIPT_CODE)
public class LcReceiptCodeActivity extends BaseActivity<ActivityLcReceiptCodeBinding, LcReceiptCodeViewModel> {
    @Autowired
    String codeUrl;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_lc_receipt_code;
    }

    @Override
    public int initVariableId() {
        return BR.lcReceiptCodeViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.commission_bg));
        binding.title.setText(App.getInstance().getApplicationContext().getString(R.string.str_qr_code));
        viewModel.imgCode.set(codeUrl);
    }

    @OnClick({R.id.title_right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right_tv:
                PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE).callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        boolean isSaveSUccess = ImageUtils.saveImageToGallery(LcReceiptCodeActivity.this, ImageUtils.getBitmap(binding.imgReceiptCode.getDrawable()));
                        if (isSaveSUccess) {
                            Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.save_success));
                        } else {
                            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.save_failed));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Toasty.showError(getString(R.string.camera_permission));
                    }
                }).request();
                break;
        }
    }
}
