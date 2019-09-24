package com.spark.qtz.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/17
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PayTypeBean implements Parcelable {

    private List<PayTypeBeanBean> payTypeBean;

    protected PayTypeBean(Parcel in) {
    }

    public static final Creator<PayTypeBean> CREATOR = new Creator<PayTypeBean>() {
        @Override
        public PayTypeBean createFromParcel(Parcel in) {
            return new PayTypeBean(in);
        }

        @Override
        public PayTypeBean[] newArray(int size) {
            return new PayTypeBean[size];
        }
    };

    public List<PayTypeBeanBean> getPayTypeBean() {
        return payTypeBean;
    }

    public void setPayTypeBean(List<PayTypeBeanBean> payTypeBean) {
        this.payTypeBean = payTypeBean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class PayTypeBeanBean {
        /**
         * createTime : 1559785143000
         * id : 56
         * memberId : 75
         * payAddress : 19965199676
         * payType : alipay
         * qrCodeUrl : http://nbtconline.oss-ap-northeast-1.aliyuncs.com/2019/06/13/b6a79a03-6fef-45d5-8b99-19746e3ef0a9.jpg
         * status : 1
         * updateTime : 1559785143000
         */

        private long createTime;
        private int id;
        private int memberId;
        private String payAddress;
        private String payType;
        private String qrCodeUrl;
        private int status;
        private long updateTime;
        private String realName;
        private String bank;
        private String branch;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getPayAddress() {
            return payAddress;
        }

        public void setPayAddress(String payAddress) {
            this.payAddress = payAddress;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }
    }
}
