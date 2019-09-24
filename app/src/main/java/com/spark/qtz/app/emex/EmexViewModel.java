package com.spark.qtz.app.emex;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.spark.quantizationclient.MemberInfoClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class EmexViewModel extends BaseViewModel {
    public EmexViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> searchInfo = new ObservableField<>();

    private OnRequestListener onRequestListener;
    public boolean isVisible2User = false;
    public boolean isOnPause = true;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
    }

    //清空搜索
    public BindingCommand closeOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            searchInfo.set("");
        }
    });


    public void loadPageDateFilter(int page, OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
        MemberInfoClient.getInstance().getList(page, searchInfo.get());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        //事件拦截
        if ((!isVisible2User || isOnPause)
                && (!eventBean.getOrigin().equals(EvKey.logout_success_401))
                && (!eventBean.getOrigin().equals(EvKey.memberListUrl)))
            return;
        switch (eventBean.getOrigin()) {
            //用户列表
            case EvKey.memberListUrl:
                if (eventBean.isStatue()) {
                    onRequestListener.onSuccess(eventBean.getObject());
                } else {
                    onRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //check成功重新加载数据
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
