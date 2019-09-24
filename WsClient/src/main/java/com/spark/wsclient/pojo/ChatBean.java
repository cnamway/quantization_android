package com.spark.wsclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ChatBean implements Parcelable{
    private Type type;
    private String fromAvatar;
    private String content;
    private String messageType;
    private String nameFrom;
    private String nameTo;
    private String orderId;
    private Integer partitionKey;
    private String sendTime;
    private String uidFrom;
    private String uidTo;

    public ChatBean() {
    }

    protected ChatBean(Parcel in) {
        fromAvatar = in.readString();
        content = in.readString();
        messageType = in.readString();
        nameFrom = in.readString();
        nameTo = in.readString();
        orderId = in.readString();
        if (in.readByte() == 0) {
            partitionKey = null;
        } else {
            partitionKey = in.readInt();
        }
        sendTime = in.readString();
        uidFrom = in.readString();
        uidTo = in.readString();
    }

    public static final Creator<ChatBean> CREATOR = new Creator<ChatBean>() {
        @Override
        public ChatBean createFromParcel(Parcel in) {
            return new ChatBean(in);
        }

        @Override
        public ChatBean[] newArray(int size) {
            return new ChatBean[size];
        }
    };

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getNameFrom() {
        return nameFrom;
    }

    public void setNameFrom(String nameFrom) {
        this.nameFrom = nameFrom;
    }

    public String getNameTo() {
        return nameTo;
    }

    public void setNameTo(String nameTo) {
        this.nameTo = nameTo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(Integer partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getUidFrom() {
        return uidFrom;
    }

    public void setUidFrom(String uidFrom) {
        this.uidFrom = uidFrom;
    }

    public String getUidTo() {
        return uidTo;
    }

    public void setUidTo(String uidTo) {
        this.uidTo = uidTo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fromAvatar);
        dest.writeString(content);
        dest.writeString(messageType);
        dest.writeString(nameFrom);
        dest.writeString(nameTo);
        dest.writeString(orderId);
        if (partitionKey == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(partitionKey);
        }
        dest.writeString(sendTime);
        dest.writeString(uidFrom);
        dest.writeString(uidTo);
    }

    public enum Type {
        LEFT, RIGHT
    }

    @Override
    public String toString() {
        return "ChatBean{" +
                "type=" + type +
                ", fromAvatar='" + fromAvatar + '\'' +
                ", content='" + content + '\'' +
                ", messageType='" + messageType + '\'' +
                ", nameFrom='" + nameFrom + '\'' +
                ", nameTo='" + nameTo + '\'' +
                ", orderId='" + orderId + '\'' +
                ", partitionKey=" + partitionKey +
                ", sendTime='" + sendTime + '\'' +
                ", uidFrom='" + uidFrom + '\'' +
                ", uidTo='" + uidTo + '\'' +
                '}';
    }
}
