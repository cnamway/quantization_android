package com.spark.wsclient.pojo;


import com.spark.wsclient.impl.Response;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/22
 * 描    述：数据接受bean
 * 修订历史：
 * ================================================
 */
public class BytesResponse implements Response<ResponsePacket> {
    private ResponsePacket mResponsePacket;

    public BytesResponse(ResponsePacket responsePacket) {
        this.mResponsePacket = responsePacket;
    }

    @Override
    public String getResponseText() {
        return null;
    }

    @Override
    public void setResponseText(String responseText) {

    }

    @Override
    public ResponsePacket getResponsePacket() {
        return mResponsePacket;
    }

    @Override
    public void setResponsePacket(ResponsePacket responsePacket) {
        this.mResponsePacket = responsePacket;
    }

    @Override
    public ResponsePacket getResponseEntity() {
        return null;
    }

    @Override
    public void setResponseEntity(ResponsePacket responseEntity) {

    }
}
