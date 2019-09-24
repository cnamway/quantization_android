package com.spark.qtz.ui.popup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.core.BottomPopupView;
import com.mylhyl.zxing.scanner.encode.QREncode;
import com.spark.acclient.pojo.CoinAddressBean;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.popup.impl.OnCoinRechargeListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.ImageUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.PermissionConstants;
import me.spark.mvvm.utils.PermissionUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinRechargePopup extends BottomPopupView {
    @BindView(R.id.coin_recharge_name)
    TextView mCoinRechargeName;
    @BindView(R.id.coin_recharge_qr_code)
    ImageView mCoinRechargeQrCode;
    @BindView(R.id.coin_recharge_address)
    TextView mCoinRechargeAddress;
    @BindView(R.id.tip)
    TextView mTip;

    private Context mContext;
    private CoinAddressBean mCoinAddressBean;
    private CoinSupportBean.DataBean mCoinSupportBean;
    private OnCoinRechargeListener mOnCoinRechargeListener;
    private boolean isLllegal = false;

    public CoinRechargePopup(@NonNull Context context, CoinAddressBean mCoinAddressBean, CoinSupportBean.DataBean coinSupportBean, OnCoinRechargeListener mOnCoinRechargeListener) {
        super(context);
        this.mContext = context;
        this.mCoinAddressBean = mCoinAddressBean;
        this.mCoinSupportBean = coinSupportBean;
        this.mOnCoinRechargeListener = mOnCoinRechargeListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_coin_recharge;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (mCoinAddressBean.getData() == null) {
            mCoinRechargeQrCode.setImageDrawable(getResources().getDrawable(R.drawable.svg_no_data));
            isLllegal = true;
            return;
        }
        mCoinRechargeName.setText(mContext.getString(R.string.coin_recharge) + mCoinSupportBean.getCoinName());
        mCoinRechargeAddress.setText(mCoinAddressBean.getData().getAddress());
        final Bitmap bitmap = new QREncode.Builder(mContext)
                .setColor(Color.BLACK)
                .setContents(mCoinAddressBean.getData().getAddress())
                .setMargin(2)
                .build().encodeAsBitmap();
        mCoinRechargeQrCode.setImageBitmap(bitmap);

        //二维码按保存
        mCoinRechargeQrCode.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE).callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        boolean isSaveSUccess = ImageUtils.saveImageToGallery(mContext, bitmap);
                        if (isSaveSUccess) {
                            Toasty.showSuccess(mContext.getString(R.string.save_success));
                        } else {
                            Toasty.showError(mContext.getString(R.string.save_failed));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        LogUtils.e(permissionsDenied);
                        Toasty.showError(mContext.getString(R.string.camera_permission));
                    }
                }).request();
                return false;
            }
        });
        String tips = getResources().getString(R.string.coin_recharge_tips);
        tips = String.format(tips, mCoinSupportBean.getCoinName(), mCoinSupportBean.getCoinName(), mCoinSupportBean.getMinDepositAmount() + mCoinSupportBean.getCoinName(), mCoinSupportBean.getMinDepositAmount() + mCoinSupportBean.getCoinName());
        mTip.setText(tips);
    }

    @OnClick({R.id.coin_recharge_order,
            R.id.coin_recharge_cancel,
            R.id.coin_recharge_choose,
            R.id.coin_recharge_copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coin_recharge_order:
                dismiss();
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_RECORD)
                        .withInt("recordType", 0)
                        .navigation();
                break;
            case R.id.coin_recharge_cancel:
                dismiss();
                break;
            case R.id.coin_recharge_choose:
                mOnCoinRechargeListener.onClickChoose();
                dismiss();
                break;
            case R.id.coin_recharge_copy:
                if (isLllegal) {
                    Toasty.showError(mContext.getString(R.string.no_notes));
                    return;
                }
                AppUtils.copy2Clipboard(mContext, mCoinAddressBean.getData().getAddress());
                break;
            default:
                break;
        }
    }
}
