package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-24
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AuthMerchantResult implements Parcelable {

    /**
     * cid : string
     * code : 0
     * count : {}
     * data : {"advanceAuthStaus":false,"avgReleaseTime":0,"certifiedBusinessApplyTime":"2019-06-24T07:52:07.973Z","certifiedBusinessStatus":0,"coin":"string","emailAuthStatus":false,"firstOtcTransactionTime":"2019-06-24T07:52:07.973Z","margin":0.21,"memberId":0,"merchantType":0,"phoneAuthStatus":false,"rangeTimeOrder":0,"rangeTimeSuccessOrder":0,"realName":"string","realnameAuthStatus":false,"reason":"string","registTime":"2019-06-24T07:52:07.974Z","totalSuccessBuyOrder":0,"totalSuccessSellOrder":0}
     * message : string
     * responseString : string
     * url : string
     */

    private String cid;
    private int code;
    private CountBean count;
    private DataBean data;
    private String message;
    private String responseString;
    private String url;

    protected AuthMerchantResult(Parcel in) {
        cid = in.readString();
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
        url = in.readString();
    }

    public static final Creator<AuthMerchantResult> CREATOR = new Creator<AuthMerchantResult>() {
        @Override
        public AuthMerchantResult createFromParcel(Parcel in) {
            return new AuthMerchantResult(in);
        }

        @Override
        public AuthMerchantResult[] newArray(int size) {
            return new AuthMerchantResult[size];
        }
    };

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cid);
        parcel.writeInt(code);
        parcel.writeString(message);
        parcel.writeString(responseString);
        parcel.writeString(url);
    }

    public static class CountBean {
    }

    public static class DataBean {
        /**
         * advanceAuthStaus : false
         * avgReleaseTime : 0
         * certifiedBusinessApplyTime : 2019-06-24T07:52:07.973Z
         * certifiedBusinessStatus : 0
         * coin : string
         * emailAuthStatus : false
         * firstOtcTransactionTime : 2019-06-24T07:52:07.973Z
         * margin : 0.21
         * memberId : 0
         * merchantType : 0
         * phoneAuthStatus : false
         * rangeTimeOrder : 0
         * rangeTimeSuccessOrder : 0
         * realName : string
         * realnameAuthStatus : false
         * reason : string
         * registTime : 2019-06-24T07:52:07.974Z
         * totalSuccessBuyOrder : 0
         * totalSuccessSellOrder : 0
         */

        private boolean advanceAuthStaus;
        private int avgReleaseTime;
        private String certifiedBusinessApplyTime;
        private int certifiedBusinessStatus;
        private String coin;
        private boolean emailAuthStatus;
        private String firstOtcTransactionTime;
        private double margin;
        private int memberId;
        private int merchantType;
        private boolean phoneAuthStatus;
        private int rangeTimeOrder;
        private int rangeTimeSuccessOrder;
        private String realName;
        private boolean realnameAuthStatus;
        private String reason;
        private String registTime;
        private int totalSuccessBuyOrder;
        private int totalSuccessSellOrder;

        public boolean isAdvanceAuthStaus() {
            return advanceAuthStaus;
        }

        public void setAdvanceAuthStaus(boolean advanceAuthStaus) {
            this.advanceAuthStaus = advanceAuthStaus;
        }

        public int getAvgReleaseTime() {
            return avgReleaseTime;
        }

        public void setAvgReleaseTime(int avgReleaseTime) {
            this.avgReleaseTime = avgReleaseTime;
        }

        public String getCertifiedBusinessApplyTime() {
            return certifiedBusinessApplyTime;
        }

        public void setCertifiedBusinessApplyTime(String certifiedBusinessApplyTime) {
            this.certifiedBusinessApplyTime = certifiedBusinessApplyTime;
        }

        public int getCertifiedBusinessStatus() {
            return certifiedBusinessStatus;
        }

        public void setCertifiedBusinessStatus(int certifiedBusinessStatus) {
            this.certifiedBusinessStatus = certifiedBusinessStatus;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public boolean isEmailAuthStatus() {
            return emailAuthStatus;
        }

        public void setEmailAuthStatus(boolean emailAuthStatus) {
            this.emailAuthStatus = emailAuthStatus;
        }

        public String getFirstOtcTransactionTime() {
            return firstOtcTransactionTime;
        }

        public void setFirstOtcTransactionTime(String firstOtcTransactionTime) {
            this.firstOtcTransactionTime = firstOtcTransactionTime;
        }

        public double getMargin() {
            return margin;
        }

        public void setMargin(double margin) {
            this.margin = margin;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(int merchantType) {
            this.merchantType = merchantType;
        }

        public boolean isPhoneAuthStatus() {
            return phoneAuthStatus;
        }

        public void setPhoneAuthStatus(boolean phoneAuthStatus) {
            this.phoneAuthStatus = phoneAuthStatus;
        }

        public int getRangeTimeOrder() {
            return rangeTimeOrder;
        }

        public void setRangeTimeOrder(int rangeTimeOrder) {
            this.rangeTimeOrder = rangeTimeOrder;
        }

        public int getRangeTimeSuccessOrder() {
            return rangeTimeSuccessOrder;
        }

        public void setRangeTimeSuccessOrder(int rangeTimeSuccessOrder) {
            this.rangeTimeSuccessOrder = rangeTimeSuccessOrder;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public boolean isRealnameAuthStatus() {
            return realnameAuthStatus;
        }

        public void setRealnameAuthStatus(boolean realnameAuthStatus) {
            this.realnameAuthStatus = realnameAuthStatus;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getRegistTime() {
            return registTime;
        }

        public void setRegistTime(String registTime) {
            this.registTime = registTime;
        }

        public int getTotalSuccessBuyOrder() {
            return totalSuccessBuyOrder;
        }

        public void setTotalSuccessBuyOrder(int totalSuccessBuyOrder) {
            this.totalSuccessBuyOrder = totalSuccessBuyOrder;
        }

        public int getTotalSuccessSellOrder() {
            return totalSuccessSellOrder;
        }

        public void setTotalSuccessSellOrder(int totalSuccessSellOrder) {
            this.totalSuccessSellOrder = totalSuccessSellOrder;
        }
    }
}
