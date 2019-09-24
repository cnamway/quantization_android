package com.spark.qtz.app.trade.legal_currency.order.receiptcode;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcReceiptCodeViewModel extends BaseViewModel {
    public LcReceiptCodeViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> imgCode = new ObservableField<>("");

    public BindingCommand finishClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
}
