package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.core.AttachPopupView;
import com.spark.acclient.FinanceClient;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.ucclient.MemberClient;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LegalCurrencyMenuPopup extends AttachPopupView {

    public LegalCurrencyMenuPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_legal_currency_menu;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.trans,
            R.id.order,
            R.id.receipt_type,
            R.id.pwd_update,
            R.id.ad_create})
    public void OnClick(View view) {
        if (!App.getInstance().isAppLogin()) {
            dismiss();
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                    .navigation();
            return;
        }
        switch (view.getId()) {
            //转换
            case R.id.trans:
                dismiss();
                FinanceClient.getInstance().getCoinSupport();
                break;
            //订单记录
            case R.id.order:
                dismiss();
                ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_LC_ORDER)
                        .navigation();
                break;
            //收款方式
            case R.id.receipt_type:
                dismiss();
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_RECEIPT)
                        .navigation();
                break;
            //修改密码
            case R.id.pwd_update:
                dismiss();
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_TRADE_PASS)
                        .withInt("isFirst", App.getInstance().getCurrentUser().getFundsVerified())
                        .navigation();
                break;
            //发布广告
            case R.id.ad_create:
                dismiss();
                MemberClient.getInstance().userNameChangeCheck();
                break;

        }
    }
}
