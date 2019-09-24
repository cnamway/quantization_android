package com.spark.qtz.app.me.setting.coinaddress;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.acclient.CoinAddressClient;
import com.spark.acclient.pojo.CoinAddressListBean;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.toast.Toasty;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinAddressViewModel extends BaseViewModel {
    public CoinAddressViewModel(@NonNull Application application) {
        super(application);
    }

    public List<CoinAddressListBean.DataBean> mSelectList = new ArrayList<>();
    private OnRequestListener mOnRequestListener;

    public ObservableField<String> bottomText = new ObservableField<>(App.getInstance().getString(R.string.add));
    public ObservableField<String> titleText = new ObservableField<>(App.getInstance().getString(R.string.mentionAddress));

    //添加提币地址
    public BindingCommand coinAddressAddOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (mSelectList.isEmpty()) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_COINADDRESS_COINADDRESS_ADD)
                        .navigation();
            } else {
                deleteCoinAddress();
            }
        }
    });

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> deleteEvent = new SingleLiveEvent<>();
    }

    /**
     * 获取用户所有提币地址
     */
    public void getCoinAddressList(OnRequestListener onRequestListener) {
        this.mOnRequestListener = onRequestListener;
        CoinAddressClient.getInstance().getCoinAddressList();
    }

    /**
     * 删除选择的地址
     */
    private void deleteCoinAddress() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < mSelectList.size(); i++) {
            stringBuffer.append("," + mSelectList.get(i).getId());
        }
        CoinAddressClient.getInstance().deleteSelectCoinBatch(stringBuffer.toString().substring(1));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //查询用户的某个币种的提币地址信息
            case EvKey.coinAddressList:
                if (mOnRequestListener == null) return;
                if (eventBean.isStatue()) {
                    mOnRequestListener.onSuccess(eventBean.getObject());
                } else {
                    mOnRequestListener.onFail(eventBean.getMessage());
                }
                break;
            //批量删除
            case EvKey.coinAddressDeleteBatch:
                dismissDialog();
                if (eventBean.isStatue()) {
                    mSelectList.clear();
                    bottomText.set(App.getInstance().getString(R.string.add));
                    uc.deleteEvent.setValue(true);
                    Toasty.showSuccess(App.getInstance().getString(R.string.delete_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    uc.deleteEvent.setValue(true);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更新bottom文字
     *
     * @param selectList
     */
    public void observedBottomText(List<CoinAddressListBean.DataBean> selectList) {
        mSelectList.clear();
        mSelectList.addAll(selectList);
        titleText.set(mSelectList.isEmpty() ? App.getInstance().getString(R.string.mentionAddress) : App.getInstance().getString(R.string.mention_address_edit));
        bottomText.set(mSelectList.isEmpty() ? App.getInstance().getString(R.string.add) : App.getInstance().getString(R.string.delete) + mSelectList.size() + App.getInstance().getString(R.string.item));
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
