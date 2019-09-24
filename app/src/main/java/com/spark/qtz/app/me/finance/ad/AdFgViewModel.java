package com.spark.qtz.app.me.finance.ad;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.otcclient.AdvertiseScanClient;
import com.spark.otcclient.pojo.AdSelfUpFindResult;

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
 * 创建日期：2019/5/31
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AdFgViewModel extends BaseViewModel {
    private int type;
    private OnRequestListener onRequestListener;

    public AdFgViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<AdSelfUpFindResult> mAdSelfUpFindResultSingleLiveEvent = new SingleLiveEvent<>();
        //0 - 上架 1- 下架
        public SingleLiveEvent<Integer> mAdUpdate = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
    }

    public void initAdType(int type) {
        this.type = type;
        switch (type) {
            case 0:
                AdvertiseScanClient.getInstance().selfAdvertiseUpFind();
                break;
            case 1:
                AdvertiseScanClient.getInstance().selfAdvertiseDownFind(1);
                break;
        }
    }

    /**
     * 下架广告
     *
     * @param type
     * @param pageIndex
     */
    public void loadAdDown(int type, int pageIndex, OnRequestListener onRequestListener) {
        this.type = type;
        this.onRequestListener = onRequestListener;
        AdvertiseScanClient.getInstance().selfAdvertiseDownFind(pageIndex);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //上架中
            case EvKey.adSelfFindUp:
                if (eventBean.isStatue()) {
                    uc.mAdSelfUpFindResultSingleLiveEvent.setValue((AdSelfUpFindResult) eventBean.getObject());
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //已下架
            case EvKey.adSelfFindDown:
                if (eventBean.isStatue()) {
                    if (onRequestListener != null)
                        onRequestListener.onSuccess(eventBean.getObject());
                } else {
                    if (onRequestListener != null)
                        onRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //上架
            case EvKey.adOnShelves:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getString(R.string.successfully_on_the_shelves));
                    uc.isRefresh.setValue(true);
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //下架
            case EvKey.adOffShelves:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getString(R.string.successfully_removed));
                    uc.isRefresh.setValue(true);
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //删除
            case EvKey.adDelete:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getString(R.string.delete_success));
                    uc.isRefresh.setValue(true);
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
