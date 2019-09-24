package com.spark.wsclient.impl;


import com.spark.wsclient.pojo.ResponsePacket;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/23
 * 描    述：WebSocket 响应数据接口
 * 修订历史：
 * ================================================
 */
public interface Response<T> {

    /**
     * 获取响应的文本数据
     */
    String getResponseText();

    /**
     * 设置响应的文本数据
     */
    void setResponseText(String responseText);

    /**
     * 获取响应的bytes数据
     */
    ResponsePacket getResponsePacket();

    /**
     * 设置响应的bytes数据
     */
    void setResponsePacket(ResponsePacket responsePacket);

    /**
     * 获取该数据的实体，可能为空，具体看实现类
     */
    T getResponseEntity();

    /**
     * 设置数据实体
     */
    void setResponseEntity(T responseEntity);
}
