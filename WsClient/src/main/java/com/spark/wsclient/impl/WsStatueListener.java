package com.spark.wsclient.impl;


import com.spark.wsclient.pojo.ErrorResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/23
 * 描    述：WebSocket监听器
 * 修订历史：
 * ================================================
 */
public interface WsStatueListener {

    /**
     * 连接成功
     *
     * @param type ws类型
     */
    void onConnected(int type);

    /**
     * 连接失败
     *
     * @param cause 失败原因
     */
    void onConnectError(int type, Throwable cause);

    /**
     * 连接断开
     */
    void onDisconnected(int type);

    /**
     * 接收到消息
     */
    void onMessageResponse(int type, Response message);

    /**
     * 消息发送失败或接受到错误消息等等
     */
    void onSendMessageError(int type, ErrorResponse error);
}
