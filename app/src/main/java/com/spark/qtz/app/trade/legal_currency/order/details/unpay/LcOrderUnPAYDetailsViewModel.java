package com.spark.qtz.app.trade.legal_currency.order.details.unpay;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.api.pojo.PayTypeBean;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.popup.LcPayPopup;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.AppUtils;
import com.spark.otcclient.AdvertiseScanClient;
import com.spark.otcclient.LcTradeClient;
import com.spark.otcclient.pojo.FindMerchantDetailsResult;
import com.spark.otcclient.pojo.LcOrderResult;
import com.spark.otcclient.pojo.OrderDetailsResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

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
import me.spark.mvvm.utils.LogUtils;
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
public class LcOrderUnPAYDetailsViewModel extends BaseViewModel {
    public LcOrderUnPAYDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    private Context mContext;

    public ObservableField<String> titleRightTV = new ObservableField<>();
    public ObservableField<String> money = new ObservableField<>();
    public ObservableField<String> money2 = new ObservableField<>();
    public ObservableField<String> nameShort = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<String> number = new ObservableField<>();
    public ObservableField<String> createTime = new ObservableField<>();
    public ObservableField<String> orderSn = new ObservableField<>();
    public ObservableField<String> referenceSn = new ObservableField<>();
    public ObservableField<Boolean> isBuyOrSell = new ObservableField<>();
    public ObservableField<Boolean> isWeChatPay = new ObservableField<>(false);
    public ObservableField<String> weChatAddr = new ObservableField<>();
    public ObservableField<String> weChatName = new ObservableField<>();
    public ObservableField<Boolean> isAliPay = new ObservableField<>(false);
    public ObservableField<String> aliPayAddr = new ObservableField<>();
    public ObservableField<String> aliPayName = new ObservableField<>();
    public ObservableField<Boolean> isBankPay = new ObservableField<>(false);
    public ObservableField<String> bankPayAddr = new ObservableField<>();
    public ObservableField<String> bankPayName = new ObservableField<>();
    public ObservableField<String> bankPayOpenBank = new ObservableField<>();
    public ObservableField<String> bankPayBranch = new ObservableField<>();
    public ObservableField<String> dealNumber = new ObservableField<>();
    public ObservableField<Boolean> isPayPalPay = new ObservableField<>(false);
    public ObservableField<String> payPalAddr = new ObservableField<>();
    public ObservableField<String> payPalName = new ObservableField<>();

    private LcOrderResult.DataBean.RecordsBean mRecordsBean;
    private PayTypeBean mPayTypeBean;
    private OnRequestListener mOnRequestListener;
    private String weChatCodeUrl, aliPayCodeUrl;
    private int tick_flush = 5;//每隔tick_flush秒钟加载一次数据
    private boolean isGoToNext = false;//是否跳转到下一界面

    public BindingCommand finishClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            stopFlush();
            finish();
        }
    });

    public BindingCommand submitOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            new XPopup.Builder(mContext)
                    .asCustom(new LcPayPopup(mContext, mRecordsBean, mPayTypeBean))
                    .show();
        }
    });
    public BindingCommand weChatOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_LC_ORCER_RECEIPT_CODE)
                    .withString("codeUrl", weChatCodeUrl)
                    .navigation();
        }
    });

    public BindingCommand alPayOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_LC_ORCER_RECEIPT_CODE)
                    .withString("codeUrl", aliPayCodeUrl)
                    .navigation();
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

    public BindingCommand cardOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            AppUtils.copy2Clipboard(Utils.getContext(), bankPayAddr.get());
        }
    });

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

    public BindingCommand titleRightOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (mRecordsBean.getOrderType()) {
                //取消订单
                case "0":
                    new XPopup.Builder(mContext)
                            .asConfirm(mContext.getString(R.string.tips), mContext.getString(R.string.str_confirm_cancel),
                                    mContext.getString(R.string.cancel), mContext.getString(R.string.ensure),
                                    new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            LcTradeClient.getInstance().orderCancel(mRecordsBean.getOrderSn());
                                        }
                                    }, null, false)
                            .show();
                    break;
            }
        }
    });

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
    }

    public void initContext(Context context) {
        this.mContext = context;
    }

    public void initViewDate(LcOrderResult.DataBean.RecordsBean orderDetailsBean, OnRequestListener onRequestListener) {
        LogUtils.e("orderDetailsBean", orderDetailsBean.toString());
        this.mRecordsBean = orderDetailsBean;
        this.mOnRequestListener = onRequestListener;
        LcTradeClient.getInstance().orderDetails(true, orderDetailsBean.getOrderSn());
        handler_timeCurrent.sendEmptyMessageDelayed(1, tick_flush * 1000);//tick_flush秒钟刷新一次
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.lcOrderPayMent:
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.str_pay_success));
                    if (!isGoToNext) {
                        isGoToNext = true;
                        ARouter.getInstance()
                                .build(ARouterPath.ACTIVITY_TRADE_LC_PAIY_DETAILS_ORDER)
                                .withParcelable("orderDetails", mRecordsBean)
                                .navigation();
                    }

                    stopFlush();
                    finish();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.lcOrderTypeDetails:
                if (eventBean.isStatue()) {
                    OrderDetailsResult orderDetailsResult = (OrderDetailsResult) eventBean.getObject();
                    //status订单状态 0-已取消 1-未付款 2-已付款 3-已完成 4-申诉中
                    int status = orderDetailsResult.getData().getStatus();
                    if (status == 1) {
                        initDate(orderDetailsResult);
                    } else if (status == 2) {
                        if (!isGoToNext) {
                            isGoToNext = true;
                            ARouter.getInstance()
                                    .build(ARouterPath.ACTIVITY_TRADE_LC_PAIY_DETAILS_ORDER)
                                    .withParcelable("orderDetails", mRecordsBean)
                                    .navigation();
                        }

                        stopFlush();
                        finish();
                    } else {
                        if (!isGoToNext) {
                            isGoToNext = true;
                            ARouter.getInstance()
                                    .build(ARouterPath.ACTIVITY_TRADE_LC_DETAILS_ORDER)
                                    .withParcelable("orderDetails", mRecordsBean)
                                    .navigation();
                        }

                        stopFlush();
                        finish();
                    }
                } else {
                    if (eventBean.getCode() == 30540) {
                        if (!isGoToNext) {
                            isGoToNext = true;
                            ARouter.getInstance()
                                    .build(ARouterPath.ACTIVITY_TRADE_LC_DETAILS_ORDER)
                                    .withParcelable("orderDetails", mRecordsBean)
                                    .navigation();
                        }

                        stopFlush();
                        finish();
                    } else {
                        mOnRequestListener.onFail(eventBean.getMessage());
                    }
                }
                break;
            case EvKey.findMerchantDetails:
                if (eventBean.isStatue()) {
                    FindMerchantDetailsResult findMerchantDetailsResult = (FindMerchantDetailsResult) eventBean.getObject();
                    dealNumber.set(App.getInstance().getString(R.string.str_deal) + (findMerchantDetailsResult.getData().getTotalSuccessBuyOrder() + findMerchantDetailsResult.getData().getTotalSuccessSellOrder()) +
                            App.getInstance().getString(R.string.hands));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.lcOrderCancel:
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.str_cancel_success));
                    stopFlush();
                    finish();
                } else {
                    Toasty.showError(eventBean.getMessage());
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

    private Handler handler_timeCurrent = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startFlush();
                    handler_timeCurrent.sendEmptyMessageDelayed(1, tick_flush * 1000);//tick_flush秒钟刷新一次
                    break;
            }

        }
    };

    /**
     * 开始刷新
     */
    private void startFlush() {
        stopFlush();
        LcTradeClient.getInstance().orderDetails(true, mRecordsBean.getOrderSn());
    }

    /**
     * 停止刷新
     */
    private void stopFlush() {
        handler_timeCurrent.removeCallbacksAndMessages(null);
    }

    private void initDate(OrderDetailsResult orderDetailsResult) {
        titleRightTV.set(mRecordsBean.getOrderType().equals("0") ? App.getInstance().getString(R.string.str_cancel_order) : "");
        mPayTypeBean = App.gson.fromJson("{\"payTypeBean\":" + orderDetailsResult.getData().getPayData() + "}", PayTypeBean.class);
        for (PayTypeBean.PayTypeBeanBean payTypeBeanBean : mPayTypeBean.getPayTypeBean()) {
            if (payTypeBeanBean.getPayType().contains(Constant.wechat)) {
                isWeChatPay.set(true);
                weChatCodeUrl = payTypeBeanBean.getQrCodeUrl();
                weChatAddr.set(payTypeBeanBean.getPayAddress());
                weChatName.set(payTypeBeanBean.getRealName());
            } else if (payTypeBeanBean.getPayType().contains(Constant.alipay)) {
                isAliPay.set(true);
                aliPayCodeUrl = payTypeBeanBean.getQrCodeUrl();
                aliPayAddr.set(payTypeBeanBean.getPayAddress());
                aliPayName.set(payTypeBeanBean.getRealName());
            } else if (payTypeBeanBean.getPayType().contains(Constant.card)) {
                isBankPay.set(true);
                bankPayAddr.set(payTypeBeanBean.getPayAddress());
                bankPayName.set(payTypeBeanBean.getRealName());
                bankPayOpenBank.set(payTypeBeanBean.getBank());
                bankPayBranch.set(payTypeBeanBean.getBranch());
            } else if (payTypeBeanBean.getPayType().contains(Constant.PAYPAL)) {
                isPayPalPay.set(true);
                payPalAddr.set(payTypeBeanBean.getPayAddress());
                payPalName.set(payTypeBeanBean.getRealName());
            }
        }
        //0 - 买入 1 - 卖出
        isBuyOrSell.set(mRecordsBean.initTypeColor());
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
        AdvertiseScanClient.getInstance().findMerchantDetails(mRecordsBean.getMemberId());
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
        stopFlush();
    }
}
