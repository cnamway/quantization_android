package com.spark.qtz.app.invite;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.XPopup;
import com.spark.qtz.App;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.popup.InvitePopup;
import com.spark.qtz.ui.popup.InviteRulePopup;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.AppUtils;
import com.spark.ucclient.MemberClient;
import com.spark.ucclient.pojo.InviteFriendResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.utils.DfUtils;
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
public class InviteViewModel extends BaseViewModel {
    public InviteViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> inviteFriendNum = new ObservableField<>("");
    public ObservableField<String> inviteMoney = new ObservableField<>("");

    public boolean isVisible2User = false;
    public boolean isOnPause = true;
    private Context mContext;

    //复制分享链接
    public BindingCommand copyShareLinkCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!App.getInstance().isAppLogin()) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
                return;
            }
            AppUtils.copy2Clipboard(mContext, Constant.inviteUrl + Constant.inviteUrlSub + App.getInstance().getCurrentUser().getPromotionCode());
        }
    });

    //生成海报
    public BindingCommand posterCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!App.getInstance().isAppLogin()) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
                return;
            }
            new XPopup.Builder(mContext)
                    .asCustom(new InvitePopup(mContext))
                    .show();
        }
    });

    //查看规则
    public BindingCommand rulesCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            new XPopup.Builder(mContext)
                    .asCustom(new InviteRulePopup(mContext))
                    .show();
        }
    });

    public void initContext(Context context) {
        mContext = context;
    }

    public void loadDate() {
        if (!App.getInstance().isAppLogin()) {
            inviteFriendNum.set("--");
            inviteMoney.set("--");
            return;
        }
        MemberClient.getInstance().getInviteDetails();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.inviteDetails:
                if (eventBean.isStatue()) {
                    InviteFriendResult inviteFriendResult = (InviteFriendResult) eventBean.getObject();
                    inviteFriendNum.set(inviteFriendResult.getData().getPromotionAmount() + "");
                    inviteMoney.set(DfUtils.numberFormat(inviteFriendResult.getData().getTotalReardAmount(),4));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.loginStatue:
                loadDate();
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    loadDate();
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
