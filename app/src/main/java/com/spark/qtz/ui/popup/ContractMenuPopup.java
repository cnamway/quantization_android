package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.lxj.xpopup.core.AttachPopupView;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnPositionChooseListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ContractMenuPopup extends AttachPopupView {

    private OnPositionChooseListener mOnPositionChooseListener;

    public ContractMenuPopup(@NonNull Context context, OnPositionChooseListener onPositionChooseListener) {
        super(context);
        this.mOnPositionChooseListener = onPositionChooseListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_contact_menu;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.coin_trans})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coin_trans:
                mOnPositionChooseListener.onChoosePosition(0);
                break;
        }
        dismiss();
    }

}
