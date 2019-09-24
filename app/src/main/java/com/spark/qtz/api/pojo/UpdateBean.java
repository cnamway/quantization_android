package com.spark.qtz.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-08-01
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class UpdateBean implements Parcelable {

    /**
     * code : 200
     * data : {"updateRemark":"1,更新;2,test1;3,test2","updateTime":1557212752000,"version":"3.1.5","url":"https://freeoss123.com/upload/5d0b2ebe6b321d6ddc13b514/android/com.spark.coinpay.xjbipay_2.5.0_250.apk"}
     */

    private int code;
    private DataBean data;

    protected UpdateBean(Parcel in) {
        code = in.readInt();
    }

    public static final Creator<UpdateBean> CREATOR = new Creator<UpdateBean>() {
        @Override
        public UpdateBean createFromParcel(Parcel in) {
            return new UpdateBean(in);
        }

        @Override
        public UpdateBean[] newArray(int size) {
            return new UpdateBean[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
    }

    public static class DataBean {
        /**
         * updateRemark : 1,更新;2,test1;3,test2
         * updateTime : 1557212752000
         * version : 3.1.5
         * url : https://freeoss123.com/upload/5d0b2ebe6b321d6ddc13b514/android/com.spark.coinpay.xjbipay_2.5.0_250.apk
         */

        private String updateRemark;
        private long updateTime;
        private String version;
        private String url;

        public String getUpdateRemark() {
            return updateRemark;
        }

        public void setUpdateRemark(String updateRemark) {
            this.updateRemark = updateRemark;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
