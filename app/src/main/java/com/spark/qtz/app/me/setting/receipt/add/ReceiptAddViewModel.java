package com.spark.qtz.app.me.setting.receipt.add;

import android.app.Application;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.qtz.base.ARouterPath;
import com.spark.otcclient.pojo.PayListBean;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ReceiptAddViewModel extends BaseViewModel {
    public ReceiptAddViewModel(@NonNull Application application) {
        super(application);
    }

    private PayListBean.DataBean wechatBean, aliBean, bankBean, paypalBean, otherBean;

    public BindingCommand aliPayOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_RECEIPT_BIND)
                    .withString("type", Constant.alipay)
                    .withParcelable("typeBean", aliBean)
                    .navigation();
            finish();
        }
    });

    public BindingCommand wechatPayOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            LogUtils.e(wechatBean == null);
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_RECEIPT_BIND)
                    .withString("type", Constant.wechat)
                    .withParcelable("typeBean", wechatBean)
                    .navigation();
            finish();
        }
    });

    public BindingCommand bankPayOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_RECEIPT_BIND)
                    .withString("type", Constant.card)
                    .withParcelable("typeBean", bankBean)
                    .navigation();
            finish();
        }
    });

    public BindingCommand paypalPayOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_RECEIPT_BIND)
                    .withString("type", Constant.PAYPAL)
                    .withParcelable("typeBean", paypalBean)
                    .navigation();
            finish();
        }
    });

    public BindingCommand otherPayOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_RECEIPT_BIND)
                    .withString("type", Constant.other)
                    .withParcelable("typeBean", otherBean)
                    .navigation();
            finish();
        }
    });
}
