package com.spark.qtz;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.spark.wsclient.WsService;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.http.cookie.PersistentCookieStore;
import me.spark.mvvm.service.DaemonEnv;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/15
 * 描    述：APP
 * 修订历史：
 * ================================================
 */
public class App extends BaseApplication {
    private PersistentCookieStore persistentCookieStore;
    private CookieManager cookieManager;

    @Override
    public void onCreate() {
        super.onCreate();
        persistentCookieStore = new PersistentCookieStore(this);
        cookieManager = new CookieManager(persistentCookieStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
        if (Constant.isDebug) {
            //LeakCanary
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();
        }
        //ARouter初始化
        ARouter.init(this);
        //Log初始化
        initLog();
        //Http初始化
        initEasyHttp();
        DaemonEnv.initialize(App.getInstance(), WsService.class, 3 * 60 * 1000);
    }

    //初始化日志工具类
    private void initLog() {
        LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(Constant.isDebug)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(Constant.isDebug)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(true)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.W)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
                .setStackOffset(0)// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setSaveDays(30)// 设置日志可保留天数，默认为 -1 表示无限时长
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new LogUtils.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList list) {
                        return "LogUtils Formatter ArrayList { " + list.toString() + " }";
                    }
                });
        LogUtils.d(config.toString());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
