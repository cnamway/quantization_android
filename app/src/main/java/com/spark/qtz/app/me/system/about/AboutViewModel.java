package com.spark.qtz.app.me.system.about;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.api.AppClient;
import com.spark.qtz.api.AppHost;
import com.spark.qtz.api.pojo.ArticleListBean;
import com.spark.qtz.api.pojo.UpdateBean;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.AppUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
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
public class AboutViewModel extends BaseViewModel {

    public AboutViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> officeWebSite = new ObservableField<>(BaseHost.HOST.substring(0, BaseHost.HOST.length() - 1));
    public ObservableField<String> versionName = new ObservableField<>(App.getInstance().getString(R.string.version) + AppUtils.getContextVersionName());
    public ObservableField<String> updateVersion = new ObservableField<>(AppUtils.getContextVersionName());
    private String aboutAppUrl, rivacyUrl, clauseUrl, helpCenterUrl;

    //关于App
    public BindingCommand aboutAppClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_EMEX_WEBDETAILS)
                    .withString("title", App.getInstance().getString(R.string.app_about))
                    .withString("link", StringUtils.isEmpty(aboutAppUrl) ? "" : aboutAppUrl)
                    .navigation();
        }
    });

    //隐私政策
    public BindingCommand rivacyClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_EMEX_WEBDETAILS)
                    .withString("title", App.getInstance().getString(R.string.register_rivacy))
                    .withString("link", StringUtils.isEmpty(rivacyUrl) ? "" : rivacyUrl)
                    .navigation();
        }
    });

    //用户条款
    public BindingCommand clauseClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_EMEX_WEBDETAILS)
                    .withString("title", App.getInstance().getString(R.string.register_clause))
                    .withString("link", StringUtils.isEmpty(clauseUrl) ? "" : clauseUrl)
                    .navigation();
        }
    });
    //官方网站
    public BindingCommand officeWebSiteClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_EMEX_WEBDETAILS)
                    .withString("title", App.getInstance().getString(R.string.official_website))
                    .withString("link", BaseHost.HOST)
                    .navigation();
        }
    });

    //联系客服
    public BindingCommand contactCustomerClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_EMEX_WEBDETAILS)
                    .withString("title", App.getInstance().getString(R.string.contact_customer_service))
                    .withString("link", AppHost.contactCustomerUrl)
                    .navigation();
        }
    });

    //帮助中心
    public BindingCommand helpCenterClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            LogUtils.e("helpCenterClickCommand", helpCenterUrl);
            ARouter.getInstance().build(ARouterPath.ACTIVITY_EMEX_WEBDETAILS)
                    .withString("title", App.getInstance().getString(R.string.help_center))
                    .withString("link", StringUtils.isEmpty(helpCenterUrl) ? "" : helpCenterUrl)
                    .navigation();
        }
    });

    //版本更新
    public BindingCommand versionUpdateClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            try {
                UpdateBean mUpdateBean = BaseApplication.gson.fromJson(Constant.updateInfoJson, UpdateBean.class);
                if (StringUtils.formatVersionCode(mUpdateBean.getData().getVersion()) >
                        StringUtils.formatVersionCode(AppUtils.getContextVersionName())) {
                    AppUtils.updateApp(mUpdateBean);
                } else {
                    Toasty.showSuccess(App.getInstance().getString(R.string.already_the_latest_version));
                }
            } catch (Exception e) {
                LogUtils.e("checkVersion", e.toString());
            }
        }
    });

    public void getArticleList() {
        AppClient.getInstance().getArticleList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        switch (eventBean.getOrigin()) {
            case EvKey.articleList:
                if (eventBean.isStatue()) {
                    ArticleListBean mArticleListBean = (ArticleListBean) eventBean.getObject();
                    for (int i = 0; i < mArticleListBean.getData().size(); i++) {
                        if (mArticleListBean.getData().get(i).getName().contains("关于")) {
                            aboutAppUrl = mArticleListBean.getData().get(i).getRedirectUrl();
                        }
                        if (mArticleListBean.getData().get(i).getName().contains("法律")
                                || mArticleListBean.getData().get(i).getName().contains("政策")) {
                            rivacyUrl = mArticleListBean.getData().get(i).getRedirectUrl();
                        }
                        if (mArticleListBean.getData().get(i).getName().contains("用户")) {
                            clauseUrl = mArticleListBean.getData().get(i).getRedirectUrl();
                        }
                        if (mArticleListBean.getData().get(i).getName().contains("帮助")) {
                            helpCenterUrl = mArticleListBean.getData().get(i).getRedirectUrl();
                        }
                    }
                } else {
                    Toasty.showError(App.getInstance().getString(R.string.network_abnormal));
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
