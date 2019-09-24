package com.spark.qtz.app.trade.legal_currency.order.details;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.qtz.App;
import com.spark.qtz.api.pojo.PayTypeBean;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.util.AppUtils;
import com.spark.otcclient.LcTradeClient;
import com.spark.otcclient.pojo.LcOrderResult;
import com.spark.otcclient.pojo.OrderDetailsResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.DateUtils;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.Utils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/12
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcOrderDetailsViewModel extends BaseViewModel {
    public LcOrderDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> money = new ObservableField<>();
    public ObservableField<String> money2 = new ObservableField<>();
    public ObservableField<String> nameShort = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<String> number = new ObservableField<>();
    public ObservableField<String> createTime = new ObservableField<>();
    public ObservableField<String> orderSn = new ObservableField<>();
    public ObservableField<String> referenceSn = new ObservableField<>();

    public ObservableField<Boolean> isAppeal = new ObservableField<>(true);
    public ObservableField<Boolean> isCancel = new ObservableField<>(true);
    public ObservableField<Boolean> isWeChatPay = new ObservableField<>(false);
    public ObservableField<Boolean> isAliPay = new ObservableField<>(false);
    public ObservableField<Boolean> isBankPay = new ObservableField<>(false);
    public ObservableField<Boolean> isPayPalPay = new ObservableField<>(false);

    private LcOrderResult.DataBean.RecordsBean mRecordsBean;
    private PayTypeBean mPayTypeBean;
    private OnRequestListener mOnRequestListener;

    public BindingCommand orderSnOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            AppUtils.copy2Clipboard(Utils.getContext(), orderSn.get());
        }
    });
    public BindingCommand referenceSnOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            AppUtils.copy2Clipboard(Utils.getContext(), referenceSn.get());
        }
    });

    public BindingCommand finishClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public BindingCommand chatOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance()
                    .build(ARouterPath.ACTIVITY_TRADE_LC_CHAT)
                    .withParcelable("orderDetails", mRecordsBean)
                    .navigation();
        }
    });

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
    }

    public void initViewDate(LcOrderResult.DataBean.RecordsBean orderDetailsBean, OnRequestListener onRequestListener) {
        this.mRecordsBean = orderDetailsBean;
        this.mOnRequestListener = onRequestListener;
        LcTradeClient.getInstance().orderDetails(orderDetailsBean.getStatus() == 4, orderDetailsBean.getOrderSn());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.lcOrderPayMent:
                if (eventBean.isStatue()) {

                } else {

                }
                break;
            case EvKey.lcOrderTypeDetails:
                if (eventBean.isStatue()) {
                    OrderDetailsResult orderDetailsResult = (OrderDetailsResult) eventBean.getObject();
                    initDate(orderDetailsResult);
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    uc.isRefresh.setValue(true);
                }
                break;
            default:
                break;
        }
    }


    private void initDate(OrderDetailsResult orderDetailsResult) {
        isAppeal.set(mRecordsBean.getStatus() == 4);
        isCancel.set(mRecordsBean.getStatus() == 0);
        mPayTypeBean = App.gson.fromJson("{\"payTypeBean\":" + orderDetailsResult.getData().getPayData() + "}", PayTypeBean.class);
        for (PayTypeBean.PayTypeBeanBean payTypeBeanBean : mPayTypeBean.getPayTypeBean()) {
            if (payTypeBeanBean.getPayType().contains(Constant.wechat)) {
                isWeChatPay.set(true);
            } else if (payTypeBeanBean.getPayType().contains(Constant.alipay)) {
                isAliPay.set(true);
            } else if (payTypeBeanBean.getPayType().contains(Constant.card)) {
                isBankPay.set(true);
            } else if (payTypeBeanBean.getPayType().contains(Constant.PAYPAL)) {
                isPayPalPay.set(true);
            }
        }
        money.set("￥ " + DfUtils.formatNum(String.valueOf(mRecordsBean.getMoney())));
        money2.set(DfUtils.numberFormat(mRecordsBean.getMoney(), mRecordsBean.getMoney() == 0 ? 0 : 8) + " CNY");
        nameShort.set(mRecordsBean.getTradeToUsername().substring(0, 1));
        name.set(mRecordsBean.getTradeToUsername());
        price.set(DfUtils.numberFormat(mRecordsBean.getPrice(), mRecordsBean.getPrice() == 0 ? 0 : 8) + " CNY");
        number.set(DfUtils.numberFormat(mRecordsBean.getNumber(), mRecordsBean.getNumber() == 0 ? 0 : 8) + " " + mRecordsBean.getCoinName());
        createTime.set(DateUtils.formatDate("yyyy.MM.dd HH:mm", mRecordsBean.getCreateTime()));
        orderSn.set(mRecordsBean.getOrderSn());
        referenceSn.set(mRecordsBean.getPayRefer());
        mOnRequestListener.onSuccess(orderDetailsResult);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }
}
