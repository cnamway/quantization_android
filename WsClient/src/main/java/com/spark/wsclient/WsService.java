package com.spark.wsclient;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.spark.wsclient.base.MessageType;
import com.spark.wsclient.base.NetworkChangedReceiver;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.base.WsHost;
import com.spark.wsclient.base.WsThread;
import com.spark.wsclient.impl.Response;
import com.spark.wsclient.impl.WsStatueListener;
import com.spark.wsclient.pojo.BytesResponse;
import com.spark.wsclient.pojo.ErrorResponse;
import com.spark.wsclient.pojo.ResponsePacket;
import com.spark.wsclient.utils.DataUtils;
import com.spark.wsclient.utils.HeartBeatUtils;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.service.AbsWorkService;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.ServiceOpenSuccessEvent;
import me.spark.mvvm.utils.StringUtils;
import me.spark.mvvm.utils.WebSocketRequest;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/20
 * 描    述：WebSocketService
 * 修订历史：
 * ================================================
 */
public class WsService extends AbsWorkService implements WsStatueListener {
    private static final String TAG = "WsService";
    //是否 任务完成, 不再需要服务运行?
    public static boolean sShouldStopService = false;

    private WsThread klineThread, bbTradeThread, cfdTradeThread, HandicapThread, ChatThread, sendThread;
    private NetworkChangedReceiver networkChangedReceiver;

    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        return null;
    }

    @Override
    public void startWork(Intent intent, int flags, int startId) {
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
    }

    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent, Void alwaysNull) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {
        LogUtils.e(TAG, "onServiceKilled");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusUtils.register(this);
        //K线模块
        if (klineThread == null) {
            klineThread = new WsThread(WsConstant.CODE_KLINE, WsHost.klineWssUrl);
            klineThread.setSocketListener(this);
            klineThread.start();
        }
        //盘口模块
        if (HandicapThread == null) {
            HandicapThread = new WsThread(WsConstant.CODE_MARKET, WsHost.handicapWssUrl);
            HandicapThread.setSocketListener(this);
            HandicapThread.start();
        }
        //BB交易模块
        if (bbTradeThread == null) {
            bbTradeThread = new WsThread(WsConstant.CODE_BB_TRADE, WsHost.bbTradeWssUrl);
            bbTradeThread.setSocketListener(this);
            bbTradeThread.start();
        }
        //CFD交易模块
        if (cfdTradeThread == null) {
            cfdTradeThread = new WsThread(WsConstant.CODE_CFD_TRADE, WsHost.cfdTradeWssUrl);
            cfdTradeThread.setSocketListener(this);
            cfdTradeThread.start();
        }

        //聊天模块
        if (ChatThread == null) {
            ChatThread = new WsThread(WsConstant.CODE_CHAT, WsHost.chatWssUrl);
            ChatThread.setSocketListener(this);
            ChatThread.start();
        }

        //绑定监听网络变化广播
        networkChangedReceiver = new NetworkChangedReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(networkChangedReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "------onDestroy----");
        //停止心跳
        HeartBeatUtils.getInstance().stopAllHeartBeat();
        EventBusUtils.unRegister(this);
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(WebSocketRequest webSocketRequest) {
        if (null != webSocketRequest.getBody()) {
            LogUtils.d(TAG, webSocketRequest.getCode() + "---" + webSocketRequest.getCmd() + "---" + new String(webSocketRequest.getBody()));
        }
        senBytes(webSocketRequest.getCode(), DataUtils.initBytes(webSocketRequest.getCmd(), webSocketRequest.getBody()));
    }


    /**
     * 发送数据
     *
     * @param type
     * @param bytes
     */
    public void senBytes(int type, byte[] bytes) {
        switch (type) {
            case WsConstant.CODE_KLINE:
                sendThread = klineThread;
                break;
            case WsConstant.CODE_MARKET:
                sendThread = HandicapThread;
                break;
            case WsConstant.CODE_BB_TRADE:
                sendThread = bbTradeThread;
                break;
            case WsConstant.CODE_CFD_TRADE:
                sendThread = cfdTradeThread;
                break;
            case WsConstant.CODE_CHAT:
                sendThread = ChatThread;
                break;
        }
        if (sendThread.getHandler() == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(3);
            errorResponse.setCause(new Throwable("WebSocket does not initialization!"));
            errorResponse.setRequestText(Arrays.toString(bytes));
            onSendMessageError(type, errorResponse);
        } else {
            Message message = sendThread.getHandler().obtainMessage();
            message.obj = bytes;
            message.what = MessageType.SEND_MESSAGE;
            sendThread.getHandler().sendMessage(message);
        }
    }

    /**
     * 重连
     */
    public void reconnect() {
        LogUtils.d(TAG, "reconnect");
        if (klineThread.getHandler() == null) {
            onConnectError(WsConstant.CODE_KLINE, new Throwable("WebSocket dose not ready"));
        } else {
            klineThread.getHandler().sendEmptyMessage(MessageType.CONNECT);
        }

        if (HandicapThread.getHandler() == null) {
            onConnectError(WsConstant.CODE_MARKET, new Throwable("WebSocket dose not ready"));
        } else {
            HandicapThread.getHandler().sendEmptyMessage(MessageType.CONNECT);
        }

        if (bbTradeThread.getHandler() == null) {
            onConnectError(WsConstant.CODE_BB_TRADE, new Throwable("WebSocket dose not ready"));
        } else {
            bbTradeThread.getHandler().sendEmptyMessage(MessageType.CONNECT);
        }

        if (cfdTradeThread.getHandler() == null) {
            onConnectError(WsConstant.CODE_CFD_TRADE, new Throwable("WebSocket dose not ready"));
        } else {
            cfdTradeThread.getHandler().sendEmptyMessage(MessageType.CONNECT);
        }

        if (ChatThread.getHandler() == null) {
            onConnectError(WsConstant.CODE_CHAT, new Throwable("WebSocket dose not ready"));
        } else {
            ChatThread.getHandler().sendEmptyMessage(MessageType.CONNECT);
        }
    }

    @Override
    public void onConnected(int type) {
        switch (type) {
            //K线
            case WsConstant.CODE_KLINE:
                LogUtils.e(TAG, "K线WebSocket连接成功！");
                HeartBeatUtils.getInstance().startKlineHeartBeat();
                if (Constant.isHttpAndWs) {
                    //BB 行情
                    senBytes(WsConstant.CODE_KLINE, DataUtils.initBytes(WsCMD.SUBSCRIBE_THUMB, BaseApplication.gson.toJson(WsUtils.setSubscribeThumbSPOTJsonMap()).getBytes()));
                    //BB CfD
                    senBytes(WsConstant.CODE_KLINE, DataUtils.initBytes(WsCMD.SUBSCRIBE_CFD_THUMB, BaseApplication.gson.toJson(WsUtils.setSubscribeThumbCFDJsonMap()).getBytes()));
                }
                break;
            //盘口
            case WsConstant.CODE_MARKET:
                LogUtils.e(TAG, "盘口WebSocket连接成功！");
                HeartBeatUtils.getInstance().startHandicapHeartBeat();
                if (Constant.isHttpAndWs) {
                    if (!TextUtils.isEmpty(Constant.lastCfdMarketPushSymbol)) {
                        senBytes(WsConstant.CODE_MARKET, DataUtils.initBytes(WsCMD.SUBSCRIBE_CFD_TRADE_PLATE, BaseApplication.gson.toJson(WsUtils.setCFDMarketJsonMap(Constant.lastCfdMarketPushSymbol)).getBytes()));
                    }
                    if (!TextUtils.isEmpty(Constant.lastCfdMarketPushSymbol)) {
                        senBytes(WsConstant.CODE_MARKET, DataUtils.initBytes(WsCMD.SUBSCRIBE_SPOT_TRADE_PLATE, BaseApplication.gson.toJson(WsUtils.setCFDMarketJsonMap(Constant.lastB2BMarketPushSymbol)).getBytes()));
                    }
                }
                break;
            //BB交易
            case WsConstant.CODE_BB_TRADE:
                LogUtils.e(TAG, "BB交易WebSocket连接成功！");
                HeartBeatUtils.getInstance().startBBTradeHeartBeat();
                //登录BB交易ws
                if (StringUtils.isEmpty(SPUtils.getInstance().getSpotCookie())) return;
                senBytes(WsConstant.CODE_BB_TRADE, DataUtils.initBytes(WsCMD.JSONLOGIN, BaseApplication.gson.toJson(WsUtils.setLoginJsonMap(SPUtils.getInstance().getSpotCookie())).getBytes()));
                break;
            //CFD交易
            case WsConstant.CODE_CFD_TRADE:
                LogUtils.e(TAG, "CFD交易WebSocket连接成功！");
                HeartBeatUtils.getInstance().startCfdTradeHeartBeat();
                //登录CFD交易ws
                if (StringUtils.isEmpty(SPUtils.getInstance().getSpotCookie())) return;
                senBytes(WsConstant.CODE_CFD_TRADE, DataUtils.initBytes(WsCMD.JSONLOGIN, BaseApplication.gson.toJson(WsUtils.setLoginJsonMap(SPUtils.getInstance().getCfdCookie())).getBytes()));
                break;
            //聊天
            case WsConstant.CODE_CHAT:
                LogUtils.e(TAG, "聊天WebSocket连接成功！");
//                HeartBeatUtils.getInstance().startChatHeartBeat();
                //聊天WebSocket连接成功后发送订阅聊天通知
                EventBusUtils.postEvent(new ServiceOpenSuccessEvent());
                break;
        }
    }

    @Override
    public void onConnectError(int type, Throwable cause) {
        LogUtils.e(TAG, type + "-->onConnectError");
    }

    @Override
    public void onDisconnected(int type) {
        LogUtils.e(TAG, type + "-->onDisconnected");
    }

    @Override
    public void onMessageResponse(int type, Response message) {
        synchronized (this) {
            BytesResponse bytesResponse = (BytesResponse) message;
            ResponsePacket responsePacket = bytesResponse.getResponsePacket();
            if (responsePacket.getCode() == 200 || responsePacket.getCmd() == WsCMD.JSONLOGIN) {
                LogUtils.e("onMessageResponse", responsePacket.getCmd() + "--" + new String(responsePacket.getBody()));
                EventBusUtils.postEvent(new WebSocketResponse(responsePacket.getCode(), type, responsePacket.getCmd(),
                        new String(responsePacket.getBody())));
                if (responsePacket.getCmd() == WsCMD.PUSH_CHAT) {
                    startAlarm(getApplicationContext());
                }
            } else {
                LogUtils.e("onMessageResponse", new String(responsePacket.getBody()) + "----" + responsePacket.getCmd());
            }
        }
    }

    @Override
    public void onSendMessageError(int type, ErrorResponse error) {
        LogUtils.e(TAG, "onSendMessageError" + error.toString());
    }

    /**
     * 消息提示音
     *
     * @param context
     */
    private static void startAlarm(Context context) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (notification == null) return;
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }

}
