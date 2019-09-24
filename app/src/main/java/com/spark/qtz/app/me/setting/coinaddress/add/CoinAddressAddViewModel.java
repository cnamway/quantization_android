package com.spark.qtz.app.me.setting.coinaddress.add;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.spark.acclient.CaptchaAcClient;
import com.spark.acclient.CoinAddressClient;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.SmsVerifyPopup;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.toast.Toasty;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinAddressAddViewModel extends BaseViewModel {
    public CoinAddressAddViewModel(@NonNull Application application) {
        super(application);
    }

    private Context mContext;

    //币种
    public ObservableField<String> mSelectCoinName = new ObservableField<>("");
    //地址
    public ObservableField<String> mCoinAddress = new ObservableField<>("");
    //备注
    public ObservableField<String> mCoinRemark = new ObservableField<>("");
    private User mUser;
    public int type = 0; //0 - 手机注册 1 - 邮箱注册
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<CoinSupportBean> mCoinSupportBeanSingleLiveEvent = new SingleLiveEvent<>();
    }

    //选择币种
    public BindingCommand coinAddressCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getCoinSupport();
        }
    });

    //提交
    public BindingCommand coinAddressSubmitCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            coinAddressSubmit();
        }
    });


    public void initContext(Context context) {
        this.mContext = context;
        mUser = App.getInstance().getCurrentUser();
        if (mUser != null) {
            if (StringUtils.isNotEmpty(mUser.getMobilePhone())) {
                type = 0;
            } else {
                type = 1;
            }
        }
    }

    /**
     * 查询平台支持到币种信息
     */
    public void getCoinSupport() {
        showDialog(App.getInstance().getString(R.string.loading));
        FinanceClient.getInstance().getCoinSupport();
    }

    private void coinAddressSubmit() {
        if (StringUtils.isEmpty(mSelectCoinName.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.coin_type_hint));
            return;
        }
        if (StringUtils.isEmpty(mCoinAddress.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.address_hint));
            return;
        }
        if (StringUtils.isEmpty(mCoinRemark.get())) {
            Toasty.showError(App.getInstance().getApplicationContext().getString(R.string.remark_hint));
            return;
        }

        showDialog(App.getInstance().getString(R.string.loading));
        CaptchaAcClient.getInstance().sendVertifyCode(type);
    }

    /**
     * 查询指定币种的钱包地址
     *
     * @param coinAddress
     */
    public void setSelectCoinAddress(String coinAddress) {
        mSelectCoinName.set(coinAddress);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            //查询平台支持到币种信息
            case EvKey.coinSupport:
                dismissDialog();
                if (eventBean.isStatue()) {
                    uc.mCoinSupportBeanSingleLiveEvent.setValue((CoinSupportBean) eventBean.getObject());
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //获取短信验证码
            case EvKey.acPhoneCaptcha:
                dismissDialog();
                if (eventBean.isStatue()) {
                    new XPopup.Builder(mContext)
                            .asCustom(new SmsVerifyPopup(mContext, App.getInstance().getCurrentUser().getMobilePhone(), new OnEtContentListener() {
                                @Override
                                public void onCEtContentInput(String content) {
                                    showDialog(App.getInstance().getString(R.string.loading));
                                    CoinAddressClient.getInstance().addCoinAddress(mCoinAddress.get(), mSelectCoinName.get(), mCoinRemark.get(), content);

                                }
                            })).show();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //添加币种
            case EvKey.coinAddressAdd:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(mContext.getString(R.string.add_success));
                    finish();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                   dismissDialog();
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
