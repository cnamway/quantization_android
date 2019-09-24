package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lxj.xpopup.core.AttachPopupView;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnPositionChooseListener;

import butterknife.BindView;
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
public class B2BMenuPopup extends AttachPopupView {
    @BindView(R.id.coin_in)
    LinearLayout mCoinIn;
    @BindView(R.id.coin_out)
    LinearLayout mCoinOut;
    @BindView(R.id.coin_trans)
    LinearLayout mCoinTrans;
    @BindView(R.id.isFavor)
    ImageView mIsFavor;
    @BindView(R.id.coin_favor)
    LinearLayout mCoinFavor;

    private OnPositionChooseListener mOnPositionChooseListener;
    private boolean isFavor;

    public B2BMenuPopup(@NonNull Context context, boolean isFavor, OnPositionChooseListener onPositionChooseListener) {
        super(context);
        this.mOnPositionChooseListener = onPositionChooseListener;
        this.isFavor = isFavor;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_b2b_menu;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);

        if (!isFavor) {
            mIsFavor.setImageDrawable(getResources().getDrawable(R.drawable.svg_collect_black_2));
        } else {
            mIsFavor.setImageDrawable(getResources().getDrawable(R.drawable.svg_collect_success_black));
        }
    }

    @OnClick({R.id.coin_in,
            R.id.coin_out,
            R.id.coin_trans,
            R.id.coin_favor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coin_in:
                mOnPositionChooseListener.onChoosePosition(0);
                break;
            case R.id.coin_out:
                mOnPositionChooseListener.onChoosePosition(1);
                break;
            case R.id.coin_trans:
                mOnPositionChooseListener.onChoosePosition(2);
                break;
            case R.id.coin_favor:
                mOnPositionChooseListener.onChoosePosition(3);
                break;
        }
        dismiss();
    }

}
