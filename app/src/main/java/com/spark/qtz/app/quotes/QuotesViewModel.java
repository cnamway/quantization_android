package com.spark.qtz.app.quotes;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.modulespot.SpotCoinClient;
import com.spark.modulespot.pojo.SpotCoinResult;
import com.spark.wsclient.pojo.B2BThumbBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.SpanUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QuotesViewModel extends BaseViewModel {
    public QuotesViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> dateTime = new ObservableField<>("");
    public ObservableField<String> ids = new ObservableField<>("");

    private OnRequestListener onRequestListenerCoinAll;
    private Context mContext;
    private SpotCoinResult mSpotCoinResult;
    public boolean isVisible2User = false;
    public boolean isOnPause = true;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<B2BThumbBean.DateBean>> closeValue = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> clickPosition = new SingleLiveEvent<>();
    }

    //总用户
    public BindingCommand emexOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.clickPosition.setValue(0);
        }
    });
    //单用户
    public BindingCommand quotesOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.clickPosition.setValue(1);
        }
    });

    public void getSpotCoinAll(Context context, OnRequestListener onRequestListener) {
        this.onRequestListenerCoinAll = onRequestListener;
        this.mContext = context;
        SpotCoinClient.getInstance().getSpotCoinAll();


    }

    //初始化表数据
    public void initChat() {
        String data = "[{\"volume\":0,\"symbol\":\"ETH/EUR\",\"high\":0,\"chg\":0,\"low\":0,\"close\":365.22,\"turnover\":0},{\"volume\":0,\"symbol\":\"BTC/CNY\",\"high\":0,\"chg\":0,\"low\":0,\"close\":1201.88,\"turnover\":0},{\"volume\":0,\"symbol\":\"USDT/KES\",\"high\":0,\"chg\":0,\"low\":0,\"close\":113.84,\"turnover\":0},{\"volume\":0,\"symbol\":\"USDT/GHS\",\"high\":0,\"chg\":0,\"low\":0,\"close\":155.66,\"turnover\":0},{\"volume\":0,\"symbol\":\"BTC/GHS\",\"high\":0,\"chg\":0,\"low\":0,\"close\":642.52,\"turnover\":0},{\"volume\":0,\"symbol\":\"USDT/NGN\",\"high\":0,\"chg\":0,\"low\":0,\"close\":999.99,\"turnover\":0},{\"volume\":0,\"symbol\":\"ETH/KES\",\"high\":0,\"chg\":0,\"low\":0,\"close\":654,\"turnover\":0},{\"volume\":0,\"symbol\":\"BTC/NGN\",\"high\":0,\"chg\":0,\"low\":0,\"close\":56,\"turnover\":0},{\"volume\":0,\"symbol\":\"ETH/NGN\",\"high\":0,\"chg\":0,\"low\":0,\"close\":256,\"turnover\":0},{\"volume\":0,\"symbol\":\"BTC/KES\",\"high\":0,\"chg\":0,\"low\":0,\"close\":1027.32,\"turnover\":0},{\"volume\":0,\"symbol\":\"USDT/USD\",\"high\":0,\"chg\":0,\"low\":0,\"close\":15462.54,\"turnover\":0},{\"volume\":0,\"symbol\":\"ETH/GHS\",\"high\":0,\"chg\":0,\"low\":0,\"close\":4575,\"turnover\":0},{\"volume\":0,\"symbol\":\"USDT/EUR\",\"high\":0,\"chg\":0,\"low\":0,\"close\":155,\"turnover\":0},{\"volume\":0,\"symbol\":\"ETH/CNY\",\"high\":0,\"chg\":0,\"low\":0,\"close\":422,\"turnover\":0},{\"volume\":0,\"symbol\":\"BTC/EUR\",\"high\":0,\"chg\":0,\"low\":0,\"close\":4221,\"turnover\":0},{\"volume\":0,\"symbol\":\"USDT/CNY\",\"high\":0,\"chg\":0,\"low\":0,\"close\":7.17,\"turnover\":0},{\"volume\":0,\"symbol\":\"BTC/USD\",\"high\":0,\"chg\":0,\"low\":0,\"close\":621,\"turnover\":0},{\"volume\":0,\"symbol\":\"ETH/USD\",\"high\":0,\"chg\":0,\"low\":0,\"close\":651.23,\"turnover\":0}]";
        String json = "{\"date\":" + data + "}";
        B2BThumbBean b2BThumbBean = App.gson.fromJson(json, B2BThumbBean.class);
        uc.closeValue.setValue(b2BThumbBean.getDate());
//        for (B2BThumbBean.DateBean dateBean : b2BThumbBean.getDate()) {
//                updateChart(dateBean);
//        }
    }

//    private void updateChart(B2BThumbBean.DateBean dateBean) {
//        uc.closeValue.setValue(DfUtils.numberFormatDown(dateBean.getClose(), 2));
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        if ((!isVisible2User || isOnPause) && (!eventBean.getOrigin().equals(EvKey.loginStatue)))
            return;
        switch (eventBean.getOrigin()) {
            //查询所有的币种
            case EvKey.spotCoinAll:
                if (eventBean.isStatue()) {
                    Constant.coinPairThumbBeanList.clear();
                    Constant.coinPairThumbBeanList.add(App.getInstance().getString(R.string.favorites));
                    Constant.spotJson = App.gson.toJson(eventBean.getObject());
                    for (SpotCoinResult.DataBean dataBean : ((SpotCoinResult) eventBean.getObject()).getData()) {
                        if (!Constant.coinPairThumbBeanList.contains(dataBean.getBaseSymbol()))
                            Constant.coinPairThumbBeanList.add(dataBean.getBaseSymbol());
                    }
                    onRequestListenerCoinAll.onSuccess(eventBean.getObject());

                } else {
                    onRequestListenerCoinAll.onFail(eventBean.getMessage());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isVisible2User = true;
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        isOnPause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isOnPause = true;
    }

}
