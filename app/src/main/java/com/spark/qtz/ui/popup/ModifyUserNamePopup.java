package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.toast.Toasty;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ModifyUserNamePopup extends BottomPopupView {
    @BindView(R.id.username)
    EditText mUserName;

    private OnEtContentListener mOnEtContentListener;

    public ModifyUserNamePopup(@NonNull Context context, OnEtContentListener mOnEtContentListener) {
        super(context);
        this.mOnEtContentListener = mOnEtContentListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_modify_username;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ensure, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ensure:
                if (StringUtils.isEmpty(mUserName.getText().toString().trim())) {
                    Toasty.showError(App.getInstance().getString(R.string.username_hint));
                } else {
                    dismiss();
                    mOnEtContentListener.onCEtContentInput(mUserName.getText().toString().trim());
                }
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
}
