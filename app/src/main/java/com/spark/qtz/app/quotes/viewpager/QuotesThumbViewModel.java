package com.spark.qtz.app.quotes.viewpager;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.modulespot.pojo.AllThumbResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.SPUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QuotesThumbViewModel extends BaseViewModel {
    private String type;
    private List<AllThumbResult.DataBean> mThumbList = new ArrayList<>();
    private int coinType = 0;

    public QuotesThumbViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<AllThumbResult.DataBean>> mAllThumbResultSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> isLogin = new SingleLiveEvent<>();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //成交全币种缩略图
            case EvKey.klineThumbAll:
                synchronized (this) {
                    if (eventBean.isStatue()) {
                        mThumbList.clear();
                        switch (coinType) {
                            case 0:
                                if (App.getInstance().isAppLogin()) {
                                    for (String favor : SPUtils.getInstance().getFavorFindList()) {
                                        for (AllThumbResult.DataBean dataBean : ((AllThumbResult) eventBean.getObject()).getData()) {
                                            if (dataBean.getSymbol().equals(favor)) {
                                                mThumbList.add(dataBean);
                                            }
                                        }
                                    }
                                    uc.mAllThumbResultSingleLiveEvent.setValue(mThumbList);
                                }
                                break;
                            default:
                                for (AllThumbResult.DataBean dataBean : ((AllThumbResult) eventBean.getObject()).getData()) {
                                    if (dataBean.getSymbol().endsWith(type)) {
                                        mThumbList.add(dataBean);
                                    }
                                }
                                uc.mAllThumbResultSingleLiveEvent.setValue(mThumbList);
                                break;
                        }
                    }
                }
                break;
            //退出登录后隐藏自选
            case EvKey.loginStatue:
                if (eventBean.isStatue()) {
                    uc.isLogin.setValue(App.getInstance().isAppLogin());
                }
                break;
            default:
                break;
        }
    }

    public void initType(String type) {
        this.type = type;
        coinType = App.getInstance().getString(R.string.favorites).equals(type) ? 0 : 1;
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
