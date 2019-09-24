package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.spark.casclient.CasClient;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.toast.Toasty;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import me.spark.mvvm.base.BaseApplication;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SmsVerifyPopup extends BottomPopupView {
    @BindView(R.id.sms_code)
    EditText mSmsCode;
    @BindView(R.id.get_sms_code)
    TextView mGetSmsCode;
    @BindView(R.id.countdownView)
    CountdownView mCountdownView;
    @BindView(R.id.ll_countdownView)
    LinearLayout mLlCountdownView;
    @BindView(R.id.btn_ensure)
    TextView mBtnEnsure;
    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;
    @BindView(R.id.sms_tip)
    TextView mSmsTip;


    private Context mContext;
    private OnEtContentListener mOnEtContentListener;
    private String phoneNum;
    private boolean isLogin = false;
    private int mLoginType;

    public SmsVerifyPopup(@NonNull Context context, String phoneNum, OnEtContentListener mOnEtContentListener) {
        super(context);
        this.mContext = context;
        this.phoneNum = phoneNum;
        this.mOnEtContentListener = mOnEtContentListener;
    }

    public SmsVerifyPopup(@NonNull Context context, int loginType, boolean isLogin, String phoneNum, OnEtContentListener mOnEtContentListener) {
        super(context);
        this.mContext = context;
        this.phoneNum = phoneNum;
        this.isLogin = isLogin;
        this.mLoginType = loginType;
        this.mOnEtContentListener = mOnEtContentListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_sms_verify;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (isLogin) {
            mSmsTip.setText((mLoginType == 0 ? mContext.getString(R.string.input_your_phone) : mContext.getString(R.string.input_your_email)) + phoneNum + mContext.getString(R.string.code_get));
        } else {
            mSmsTip.setText((BaseApplication.getInstance().getCurrentUser().getLogintype() == 0 ? mContext.getString(R.string.input_your_phone) : mContext.getString(R.string.input_your_email)) + phoneNum + mContext.getString(R.string.code_get));
        }
        mCountdownView.start(60 * 1000);
        mCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                mLlCountdownView.setVisibility(GONE);
                mGetSmsCode.setVisibility(VISIBLE);
            }
        });
    }


    @OnClick({R.id.get_sms_code,
            R.id.btn_ensure,
            R.id.btn_cancel})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.get_sms_code:
                if (isLogin) {
                    CasClient.getInstance().sendVertifyCode(BaseApplication.getInstance().getCurrentUser().getLogintype());
                    mLlCountdownView.setVisibility(VISIBLE);
                    mGetSmsCode.setVisibility(GONE);
                    mCountdownView.start(60 * 1000);
                    mCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                        @Override
                        public void onEnd(CountdownView cv) {
                            mLlCountdownView.setVisibility(GONE);
                            mGetSmsCode.setVisibility(VISIBLE);
                        }
                    });
                } else {

                }
                break;
            case R.id.btn_ensure:
                if (TextUtils.isEmpty(mSmsCode.getText().toString())) {
                    Toasty.showError(mContext.getString(R.string.verify_code_hint));
                    return;
                }
                dismiss();
                mOnEtContentListener.onCEtContentInput(mSmsCode.getText().toString());
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    public void updatePhone(String postPhone) {
        this.phoneNum = postPhone;
    }

    public void updateLoginType(int loginType) {
        this.mLoginType = loginType;
    }
}

