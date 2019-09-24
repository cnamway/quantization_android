package com.spark.qtz.app.trade.currency.openorders;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.modulespot.pojo.AllThumbResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/13
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OpenOrdersViewModel extends BaseViewModel {
    public OpenOrdersViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean isLoadAllThumb = false;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> isLoadSuccess = new SingleLiveEvent<>();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //成交全币种缩略图
            case EvKey.klineThumbAll:
                synchronized (this) {
                    if (eventBean.isStatue()) {
                        if (!isLoadAllThumb) {
                            if (null == Constant.allThumbSymbol) {
                                Constant.allThumbSymbol = new String[((AllThumbResult) eventBean.getObject()).getData().size()];
                                for (int i = 0; i < ((AllThumbResult) eventBean.getObject()).getData().size(); i++) {
                                    Constant.allThumbSymbol[i] = ((AllThumbResult) eventBean.getObject()).getData().get(i).getSymbol();
                                }
                            }
                            isLoadAllThumb = true;
                            uc.isLoadSuccess.setValue(true);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Constant.isOpenOrdersVisiable = true;
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constant.isOpenOrdersVisiable = false;
        EventBusUtils.unRegister(this);
    }
}
