package com.spark.wsclient.pojo;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/22
 * 描    述： * Netty和客户端交互的数据包定义
 * 包通用信息的声明，包含长度，序列ID，指令码，内容
 * 长度：默认为包头长度，setBody时重置为包头和body的长度之和
 * 序列id:对于请求，该值为客户端的唯一id，对于响应，该值为生成的唯一编码
 * 请求标识:客户端请求唯一标识，精准定位客户端当前连接的所有请求
 * 指令码：需要对方做的具体动作
 * 修订历史：
 * ================================================
 */
public abstract class Packet {
    protected final static int MIN_LENGTH = 18;
    /**
     * 数据包长度 4byte
     */
    private int length;
    /**
     * 客户端序列号 8byte
     */
    private long sequenceId;
    /**
     * 客户端请求标识 4byte
     */
    private int requestId;
    /**
     * 请求指令 2byte
     */
    private short cmd;
    /**
     * 请求内容
     */
    private byte[] body;

    public abstract int getHeaderLength();

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        if (length == 0) return getHeaderLength();
        return length;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
        if (this.body == null) {
            this.length = getHeaderLength();
        } else {
            this.length = getHeaderLength() + body.length;
        }
    }
}
