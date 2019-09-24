package com.spark.qtz.app.quotes.viewpager;

import android.app.Application;
import android.support.annotation.NonNull;

import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.modulespot.B2BWsClient;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.pojo.B2BThumbBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QuotesReceiveViewModel extends BaseViewModel {
    private String type;
    private List<AllThumbResult.DataBean> mThumbList = new ArrayList<>();
    public int coinType = 0;
    private OnRequestListener mOnRequestListener;
    public boolean isFirstHttpLoadSuccess = false;

    public QuotesReceiveViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //http
        public SingleLiveEvent<List<AllThumbResult.DataBean>> httpBean = new SingleLiveEvent<>();
        //ws
        public SingleLiveEvent<B2BThumbBean> wsBean = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> isLogin = new SingleLiveEvent<>();
    }

    public void getFirstHttpDate(OnRequestListener onRequestListener) {
        isFirstHttpLoadSuccess = false;
        this.mOnRequestListener = onRequestListener;
        B2BWsClient.getInstance().getB2BKlinePush();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //成交全币种缩略图
            case EvKey.klineThumbAll:
                synchronized (this) {
                    if (eventBean.isStatue()) {
                        if (isFirstHttpLoadSuccess) {
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
                                        uc.httpBean.setValue(mThumbList);
                                    }
                                    break;
                                default:
                                    for (AllThumbResult.DataBean dataBean : ((AllThumbResult) eventBean.getObject()).getData()) {
                                        if (dataBean.getSymbol().endsWith(type)) {
                                            mThumbList.add(dataBean);
                                        }
                                    }
                                    uc.httpBean.setValue(mThumbList);
                                    break;
                            }
                        } else {
                            if (null == mOnRequestListener) return;
                            Constant.b2bKlinePushJson = App.gson.toJson(eventBean.getObject());
                            mOnRequestListener.onSuccess(eventBean.getObject());
                        }
                    } else {
                        if (null == mOnRequestListener) return;
                        if (!isFirstHttpLoadSuccess) {
                            mOnRequestListener.onFail(eventBean.getMessage());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //缩略图推送
            case WsCMD.PUSH_THUMB:
                Constant.lastB2BKlinePushTime = System.currentTimeMillis();
                if (!isFirstHttpLoadSuccess) return;
                try {
                    String json = "{\"date\":" + webSocketResponse.getResponse() + "}";
                    B2BThumbBean b2BThumbBean = App.gson.fromJson(json, B2BThumbBean.class);
                    uc.wsBean.setValue(b2BThumbBean);
                } catch (Exception e) {
                    LogUtils.e("PUSH_THUMB", e.toString());
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
