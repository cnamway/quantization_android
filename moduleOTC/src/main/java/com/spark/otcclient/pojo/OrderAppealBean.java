package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OrderAppealBean implements Parcelable{

    /**
     * orderId :
     * picturesOne : string
     * picturesThree : string
     * picturesTwo : string
     * remark : string
     */

    private String orderId;
    private String picturesOne;
    private String picturesThree;
    private String picturesTwo;
    private String remark;

    public OrderAppealBean() {
    }

    protected OrderAppealBean(Parcel in) {
        orderId = in.readString();
        picturesOne = in.readString();
        picturesThree = in.readString();
        picturesTwo = in.readString();
        remark = in.readString();
    }

    public static final Creator<OrderAppealBean> CREATOR = new Creator<OrderAppealBean>() {
        @Override
        public OrderAppealBean createFromParcel(Parcel in) {
            return new OrderAppealBean(in);
        }

        @Override
        public OrderAppealBean[] newArray(int size) {
            return new OrderAppealBean[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPicturesOne() {
        return picturesOne;
    }

    public void setPicturesOne(String picturesOne) {
        this.picturesOne = picturesOne;
    }

    public String getPicturesThree() {
        return picturesThree;
    }

    public void setPicturesThree(String picturesThree) {
        this.picturesThree = picturesThree;
    }

    public String getPicturesTwo() {
        return picturesTwo;
    }

    public void setPicturesTwo(String picturesTwo) {
        this.picturesTwo = picturesTwo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(picturesOne);
        dest.writeString(picturesThree);
        dest.writeString(picturesTwo);
        dest.writeString(remark);
    }
}
