package com.spark.wsclient.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.spark.wsclient.impl.WsStatueListener;
import com.spark.wsclient.pojo.BytesResponse;
import com.spark.wsclient.pojo.ErrorResponse;
import com.spark.wsclient.pojo.ResponsePacket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/23
 * 描    述：WebSocket 线程， 负责 WebSocket 连接的建立，数据发送，监听数据等。
 * 修订历史：
 * ================================================
 */
public class WsThread extends Thread {

    private static final String TAG = "WsThread";

    /**
     * WebSocket 连接地址
     */
    private String connectUrl;

    private WebSocketClient mWebSocket;

    private WebSocketHandler mHandler;

    private WsStatueListener mWsStatueListener;

    private WsReconnectManager mReconnectManager;

    private int type;
    /**
     * 0-未连接
     * 1-正在连接
     * 2-已连接
     */
    private int connectStatus = 0;

    public WsThread(int type, String connectUrl) {
        this.type = type;
        this.connectUrl = connectUrl;
        mReconnectManager = new WsReconnectManager(this);
    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        mHandler = new WebSocketHandler();
        mHandler.sendEmptyMessage(MessageType.CONNECT);
        Looper.loop();
    }

    /**
     * 获取控制 WsThread 的 Handler
     */
    public Handler getHandler() {
        return mHandler;
    }

    public WebSocketClient getSocket() {
        return mWebSocket;
    }

    public void setSocketListener(WsStatueListener wsStatueListener) {
        this.mWsStatueListener = wsStatueListener;
    }

    /**
     * 获取连接状态
     */
    public int getConnectState() {
        return connectStatus;
    }

    public void reconnect() {
        mReconnectManager.performReconnect();
    }

    private class WebSocketHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageType.CONNECT:
                    connect();
                    break;
                case MessageType.DISCONNECT:
                    disconnect();
                    break;
                case MessageType.QUIT:
                    quit();
                    break;
                case MessageType.RECEIVE_MESSAGE:
                    if (mWsStatueListener != null && msg.obj instanceof ResponsePacket) {
                        mWsStatueListener.onMessageResponse(type, new BytesResponse((ResponsePacket) msg.obj));
                    }
                    break;
                case MessageType.SEND_MESSAGE:
                    if (msg.obj instanceof byte[]) {
                        byte[] message = (byte[]) msg.obj;
                        if (mWebSocket != null && connectStatus == 2) {
                            send(message);
                        } else if (mWsStatueListener != null) {
                            ErrorResponse errorResponse = new ErrorResponse();
                            errorResponse.setErrorCode(1);
                            errorResponse.setCause(new Throwable("WebSocket does not connect or closed!"));
                            errorResponse.setRequestText(Arrays.toString(message));
                            mWsStatueListener.onSendMessageError(type, errorResponse);
                            mReconnectManager.performReconnect();
                        }
                    }
                    break;
            }
        }

        private void connect() {
            if (connectStatus == 0) {
                connectStatus = 1;
                try {
                    if (mWebSocket == null) {
                        if (TextUtils.isEmpty(connectUrl)) {
                            throw new RuntimeException("WebSocket connect url is empty!");
                        }
                        mWebSocket = new WebSocketClient(new URI(connectUrl), new Draft_6455()) {

                            @Override
                            public void onOpen(ServerHandshake handShakeData) {
                                connectStatus = 2;
                                LogUtils.d(TAG, "WebSocket 连接成功");
                                if (mWsStatueListener != null) {
                                    mWsStatueListener.onConnected(type);
                                }
                            }

                            @Override
                            public void onMessage(String message) {

                            }

                            @Override
                            public void onMessage(ByteBuffer bytes) {
                                connectStatus = 2;
                                ResponsePacket responsePacket = new ResponsePacket(bytes);
                                Message msg = mHandler.obtainMessage();
                                msg.what = MessageType.RECEIVE_MESSAGE;
                                msg.obj = responsePacket;
                                mHandler.sendMessage(msg);
                            }


                            @Override
                            public void onClose(int code, String reason, boolean remote) {
                                connectStatus = 0;
                                LogUtils.d(TAG, "WebSocket 断开连接");
                                if (mWsStatueListener != null) {
                                    mWsStatueListener.onDisconnected(type);
                                }
                                mReconnectManager.performReconnect();
                            }

                            @Override
                            public void onError(Exception ex) {
//                                LogUtils.e(TAG, "WebSocketClient#onError(Exception)", ex);
                            }
                        };
                        LogUtils.d(TAG, "WebSocket 开始连接...");
                        mWebSocket.connect();
                    } else {
                        LogUtils.d(TAG, "WebSocket 开始重新连接...");
                        mWebSocket.reconnect();
                    }
                } catch (URISyntaxException e) {
                    connectStatus = 0;
                    LogUtils.e(TAG, "WebSocket 连接失败");
                    if (mWsStatueListener != null) {
                        mWsStatueListener.onConnectError(type, e);
                    }
                }
            }
        }

        private void disconnect() {
            if (connectStatus == 2) {
                LogUtils.d(TAG, "正在关闭WebSocket连接");
                if (mWebSocket != null) {
                    mWebSocket.close();
                }
                connectStatus = 0;
                LogUtils.d(TAG, "WebSocket连接已关闭");
            }
        }

        private void send(byte[] bytes) {
            if (mWebSocket != null && connectStatus == 2) {
                try {
                    mWebSocket.send(bytes);
                } catch (WebsocketNotConnectedException e) {
                    connectStatus = 0;
                    Log.e(TAG, "send()" + Arrays.toString(bytes), e);
                    Log.e(TAG, "连接已断开，数据发送失败：" + Arrays.toString(bytes), e);
                    if (mWsStatueListener != null) {
                        mWsStatueListener.onDisconnected(type);

                        ErrorResponse errorResponse = new ErrorResponse();
                        errorResponse.setErrorCode(1);
                        errorResponse.setCause(new Throwable("WebSocket does not connected or closed!"));
                        errorResponse.setRequestText(Arrays.toString(bytes));
                        mWsStatueListener.onSendMessageError(type, errorResponse);
                    }
                    mReconnectManager.performReconnect();
                }
            }
        }

        /**
         * 结束此线程并销毁资源
         */
        private void quit() {
            disconnect();
            mWebSocket = null;
            mReconnectManager.destroy();
            connectStatus = 0;
            Looper looper = Looper.myLooper();
            if (looper != null) {
                looper.quit();
            }
            WsThread.this.interrupt();
        }
    }
}
