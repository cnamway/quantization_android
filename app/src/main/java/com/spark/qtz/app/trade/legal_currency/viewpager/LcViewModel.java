package com.spark.qtz.app.trade.legal_currency.viewpager;

import android.app.Application;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.acclient.FinanceClient;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.LcAdFilterBean;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.AdvertiseScanClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcViewModel extends BaseViewModel {
    public LcViewModel(@NonNull Application application) {
        super(application);
    }

    private String coinName;
    private OnRequestListener onRequestListener;
    private OnRequestListener onRequestListenerWallet;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Integer> tradeType = new SingleLiveEvent<>();
        public SingleLiveEvent<LcAdFilterBean> adFilter = new SingleLiveEvent<>();
    }

    /**
     * 按指定币种查询上架的广告
     *
     * @param pageIndex
     * @param coinName
     * @param tradeType
     */
    public void findPage(int pageIndex, String coinName, int tradeType, String payMode, String country, String minLimit, String maxLimit, OnRequestListener onRequestListener) {
        this.coinName = coinName;
        this.onRequestListener = onRequestListener;
        AdvertiseScanClient.getInstance().findPage(pageIndex, coinName, tradeType, payMode, country, minLimit, maxLimit);
    }

    /**
     * 查询可用余额
     */
    public void findData(OnRequestListener onRequestListener) {
        showDialog(App.getInstance().getString(R.string.loading));
        this.onRequestListenerWallet = onRequestListener;
        FinanceClient.getInstance().getLcWalletAvailable(coinName);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //查询所有的币种
            case EvKey.findPage:
                if (!eventBean.getMessage().equals(coinName)) return;
                if (eventBean.isStatue()) {
                    onRequestListener.onSuccess(eventBean.getObject());
                } else {
                    onRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //我要买 - 我要卖  切换
            case EvKey.lcTradeType:
                uc.tradeType.setValue(eventBean.getCode());
                break;
            //筛选
            case EvKey.lcAdFilterType:
                uc.adFilter.setValue((LcAdFilterBean) eventBean.getObject());
                break;
            //下单
            case EvKey.orderCreate:
                dismissDialog();
                if (!eventBean.getObject().equals(coinName)) return;
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getApplicationContext().getString(R.string.order_success));
                    ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_LC_ORDER)
                            .navigation();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //otc钱包查询业务处理
            case EvKey.coinWalletOtcAvailable:
                dismissDialog();
                if (!eventBean.getMessage().equals(coinName)) return;
                if (eventBean.isStatue()) {
                    onRequestListenerWallet.onSuccess(eventBean.getObject());
                } else {
                    onRequestListenerWallet.onFail(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                dismissDialog();
                break;
            default:
                break;
        }
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
