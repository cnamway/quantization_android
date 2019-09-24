package com.spark.wsclient.base;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;

import com.spark.wsclient.WsService;

import me.spark.mvvm.utils.LogUtils;


/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/23
 * 描    述：WsService网络监听
 * 修订历史：
 * ================================================
 */
public class NetworkChangedReceiver extends BroadcastReceiver {

    private static final String LOGTAG = "NetworkChangedReceiver";

    private WsService mWsService;

    public NetworkChangedReceiver(WsService wsService) {
        this.mWsService = wsService;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager == null) return;
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.isConnected()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        LogUtils.i(LOGTAG, "网络连接发生变化，当前WiFi连接可用，正在尝试重连。");
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        LogUtils.i(LOGTAG, "网络连接发生变化，当前移动连接可用，正在尝试重连。");
                    }
                    if (mWsService != null) {
                        mWsService.reconnect();
                    }
                } else {
                    LogUtils.i(LOGTAG, "当前没有可用网络");
                }
            }
        }
    }
}