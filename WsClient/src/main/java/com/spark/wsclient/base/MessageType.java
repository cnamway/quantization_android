package com.spark.wsclient.base;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/23
 * 描    述：消息类型
 * 修订历史：
 * ================================================
 */
public class MessageType {
    public static final int CONNECT = 0;            //连接Socket
    public static final int DISCONNECT = 1;         //断开连接，主动关闭或被动关闭
    public static final int QUIT = 2;               //结束线程
    public static final int SEND_MESSAGE = 3;       //通过Socket连接发送数据
    public static final int RECEIVE_MESSAGE = 4;    //通过Socket获取到数据

}
