package com.spark.wsclient.base;

import android.os.Handler;

import org.java_websocket.client.WebSocketClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/23
 * 描    述：负责 WebSocket 重连
 * 修订历史：
 * ================================================
 */
public class WsReconnectManager {

    private static final String TAG = "WsReconnectManager";

    private WsThread mWsThread;

    /**
     * 是否正在重连
     */
    private volatile boolean retrying;
    private volatile boolean destroyed;
    private final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    WsReconnectManager(WsThread wsThread) {
        this.mWsThread = wsThread;
        retrying = false;
        destroyed = false;
    }

    /**
     * 开始重新连接，连接方式为每个500ms连接一次，持续十五次。
     */
    synchronized void performReconnect() {
        if (retrying) {
            LogUtils.d(TAG, "正在重连，请勿重复调用。");
        } else {
            retry();
        }
    }

    /**
     * 开始重连
     */
    private synchronized void retry() {
        if (!retrying) {
            retrying = true;
            synchronized (singleThreadPool) {
                singleThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        retrying = true;
                        for (int i = 0; i < 20; i++) {
                            if (destroyed) {
                                retrying = false;
                                return;
                            }
                            Handler handler = mWsThread.getHandler();
                            WebSocketClient webSocketClient = mWsThread.getSocket();
                            if (handler != null && webSocketClient != null) {
                                if (mWsThread.getConnectState() == 2) {
                                    break;
                                } else if (mWsThread.getConnectState() == 1) {
                                    continue;
                                } else {
                                    handler.sendEmptyMessage(MessageType.CONNECT);
                                }
                            } else {
                                break;
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                LogUtils.e(TAG, " WebSocket重连retry()", e);
                                if (destroyed = true) {
                                    retrying = false;
                                    return;
                                } else {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                        retrying = false;
                    }
                });
            }
        }
    }

    /**
     * 销毁资源，并停止重连
     */
    void destroy() {
        destroyed = true;
        if (singleThreadPool != null) {
            singleThreadPool.shutdownNow();
        }
        mWsThread = null;
    }
}
