package com.spark.qtz.app.me.finance.property.record;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.qtz.R;
import com.spark.qtz.ui.toast.Toasty;

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
 * 创建日期：2019/6/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PropertyRecordViewModel extends BaseViewModel {
    public PropertyRecordViewModel(@NonNull Application application) {
        super(application);
    }

    private String[] mTitles;
    private Context mContext;
    private OnRequestListener onRequestListener;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<String> coinName = new SingleLiveEvent<>();
    }

    public void findPropertyRecord(int pageIndex, String coinName, String busiType, String orderType, OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
        FinanceClient.getInstance().getProperyOrder(pageIndex, coinName, busiType, orderType);
    }

    public void findCoinList(Context context) {
        this.mContext = context;
        if (mTitles != null) {
            new XPopup.Builder(mContext)
                    .maxHeight((int) (XPopupUtils.getWindowHeight(mContext) * .85f))
                    .asBottomList(mContext.getString(R.string.please_choose), mTitles,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    uc.coinName.setValue(position == 0 ? "" : text);
                                }
                            })
                    .show();
        } else {
            showDialog(mContext.getString(R.string.loading));
            FinanceClient.getInstance().getCoinSupport();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //otc钱包查询业务处理
            case EvKey.propertyDetails:
                if (eventBean.isStatue()) {
                    onRequestListener.onSuccess(eventBean.getObject());
                } else {
                    onRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //
            case EvKey.coinSupport:
                dismissDialog();
                if (eventBean.isStatue()) {
                    CoinSupportBean coinSupportBean = (CoinSupportBean) eventBean.getObject();
                    mTitles = new String[coinSupportBean.getData().size() + 1];
                    mTitles[0] = mContext.getString(R.string.all);
                    for (int i = 0; i < coinSupportBean.getData().size(); i++) {
                        mTitles[i + 1] = coinSupportBean.getData().get(i).getCoinName();
                    }
                    new XPopup.Builder(mContext)
                            .maxHeight((int) (XPopupUtils.getWindowHeight(mContext) * .85f))
                            .asBottomList(mContext.getString(R.string.please_choose), mTitles,
                                    new OnSelectListener() {
                                        @Override
                                        public void onSelect(int position, String text) {
                                            uc.coinName.setValue(position == 0 ? "" : text);
                                        }
                                    })
                            .show();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
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
