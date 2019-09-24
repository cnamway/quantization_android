package com.spark.qtz.ui.popup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.mylhyl.zxing.scanner.encode.QREncode;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
 * 创建日期：2019/5/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class InvitePopup extends BottomPopupView {

    @BindView(R.id.qr_code)
    ImageView mQrCode;
    @BindView(R.id.cancel)
    ImageView mCancel;
    @BindView(R.id.save_img)
    TextView mSaveImg;
    @BindView(R.id.copy)
    TextView mCopy;
    @BindView(R.id.poster_root)
    LinearLayout mPosterRoot;
    @BindView(R.id.invite_code)
    TextView mInviteCode;
    @BindView(R.id.website_url)
    TextView mWebsiteUrl;
    private Context mContext;
    private String inviteUrl;
    private Bitmap mBitmap;

    public InvitePopup(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_invite;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mWebsiteUrl.setText(BaseHost.HOST.substring(0, BaseHost.HOST.length() - 1));
        mInviteCode.setText(App.getInstance().getCurrentUser().getPromotionCode());
        inviteUrl = Constant.inviteUrl + Constant.inviteUrlSub + App.getInstance().getCurrentUser().getPromotionCode();

        mBitmap = new QREncode.Builder(mContext)
                .setColor(Color.BLACK)
                .setContents(inviteUrl)
                .setMargin(2)
                .build().encodeAsBitmap();
        mQrCode.setImageBitmap(mBitmap);
    }

    @OnClick({R.id.cancel,
            R.id.save_img,
            R.id.copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.save_img:
                final Bitmap saveImg = ImageUtils.view2Bitmap(mPosterRoot);
                PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE).callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        boolean isSaveSUccess = ImageUtils.saveImageToGallery(mContext, saveImg);
                        if (isSaveSUccess) {
                            Toasty.showSuccess(mContext.getString(R.string.save_success));
                        } else {
                            Toasty.showError(mContext.getString(R.string.save_failed));
                        }
                        dismiss();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        LogUtils.e(permissionsDenied);
                        Toasty.showError(mContext.getString(R.string.camera_permission));
                        dismiss();
                    }
                }).request();
                break;
            case R.id.copy:
                AppUtils.copy2Clipboard(mContext, inviteUrl);
                dismiss();
                break;
            default:
                break;
        }
    }
}
