package com.spark.qtz.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.spark.qtz.R;
import com.spark.qtz.ui.VerificationCodeInput;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import me.spark.mvvm.ui.dialog.anim.BounceEnter;
import me.spark.mvvm.ui.dialog.anim.BounceExit;
import me.spark.mvvm.ui.dialog.base.BaseDialog;
import me.spark.mvvm.ui.dialog.impl.OnCodeListener;
import me.spark.mvvm.ui.dialog.impl.OnSmsSendAgainListener;
import me.spark.mvvm.ui.dialog.utils.CornerUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/11
 * 描    述：短信验证码Dialog
 * 修订历史：
 * ================================================
 */
public class VerifyPhoneDialog extends BaseDialog<VerifyPhoneDialog> {
    @BindView(R.id.tvTimeTXT)
    TextView tvTimeTXT;
    @BindView(R.id.tvSend)
    TextView tvSend;
    @BindView(R.id.phoneInput)
    VerificationCodeInput phoneInput;
    @BindView(R.id.smsTimer)
    CountdownView smsTimer;

    private OnSmsSendAgainListener mOnSmsSendAgainListener;
    private OnCodeListener mOnCodeListener;

    public VerifyPhoneDialog(Context context,
                             OnSmsSendAgainListener mOnSmsSendAgainListener,
                             OnCodeListener mOnCodeListener) {
        super(context, true);
        this.mOnSmsSendAgainListener = mOnSmsSendAgainListener;
        this.mOnCodeListener = mOnCodeListener;
    }

    @Override
    public View onCreateView() {
        widthScale(0.8f);
        View inflate = View.inflate(mContext, R.layout.dialog_verify_phone, null);
        ButterKnife.bind(this, inflate);

        initView();
        //进出动画
        showAnim(new BounceEnter());
        dismissAnim(new BounceExit());
        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));
        return inflate;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        smsTimer.stop();
    }

    private void initView() {
        phoneInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                mOnCodeListener.onReceive(content);
            }
        });

        smsTimer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                smsTimer.setVisibility(View.GONE);
                tvTimeTXT.setVisibility(View.GONE);
                tvSend.setTextColor(Color.BLACK);
                tvSend.setEnabled(true);
            }
        });
    }

    @Override
    public void setUiBeforShow() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @OnClick({R.id.tvSend, R.id.ivCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSend:
                if (mOnSmsSendAgainListener != null) {
                    mOnSmsSendAgainListener.onClick(this);
                }
                break;
            case R.id.ivCancel:
                dismiss();
                break;
        }
    }

    public void startTimer() {
        smsTimer.setVisibility(View.VISIBLE);
        tvTimeTXT.setVisibility(View.VISIBLE);
        smsTimer.start(60 * 1000);
        tvSend.setTextColor(mContext.getResources().getColor(R.color.gray_70));
        tvSend.setEnabled(false);
    }


}
