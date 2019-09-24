package com.spark.wsclient.pojo;

import java.nio.ByteBuffer;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/22
 * 描    述：响应数据包
 * 修订历史：
 * ================================================
 */
public class ResponsePacket extends Packet {
    protected final static int HEADER_LENGTH = Packet.MIN_LENGTH + 4;
    /**
     * 响应码。4字节
     */
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int getHeaderLength() {
        return HEADER_LENGTH;
    }

    public ResponsePacket(ByteBuffer byteBuffer){
        int len= byteBuffer.getInt();
        this.setSequenceId(byteBuffer.getLong());
        this.setCmd(byteBuffer.getShort());
        this.setCode(byteBuffer.getInt());
        this.setRequestId(byteBuffer.getInt());
        byte[] body = new byte[len-HEADER_LENGTH];
        byteBuffer.get(body);
        this.setBody(body);

    }
}
