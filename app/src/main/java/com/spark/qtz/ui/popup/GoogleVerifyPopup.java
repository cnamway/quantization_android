package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.toast.Toasty;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GoogleVerifyPopup extends BottomPopupView {
    @BindView(R.id.sms_code)
    EditText mSmsCode;
    @BindView(R.id.btn_ensure)
    TextView mBtnEnsure;
    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;

    private Context mContext;
    private OnEtContentListener mOnEtContentListener;

    public GoogleVerifyPopup(@NonNull Context context, OnEtContentListener mOnEtContentListener) {
        super(context);
        this.mContext = context;
        this.mOnEtContentListener = mOnEtContentListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_google_verify;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ensure,
            R.id.btn_cancel})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ensure:
                if (TextUtils.isEmpty(mSmsCode.getText().toString())) {
                    Toasty.showError(mContext.getString(R.string.verify_code_hint));
                    return;
                }
                dismiss();
                mOnEtContentListener.onCEtContentInput(mSmsCode.getText().toString().trim());
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

}

