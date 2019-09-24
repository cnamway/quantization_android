package com.spark.ucclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-26
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AuthErrorResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"id":"93","username":"u1561512177863","createTime":"2019-06-26 09:23:00","mobilePhone":"8618212345678","phoneVerified":true,"email":null,"emailVerified":false,"avatar":null,"realName":null,"idCard":null,"realNameRejectReason":"而非我反胃粉色的防守打法胜多负少","realVerified":0,"fundsVerified":false,"accountVerified":false,"transactions":0,"firstTransactionTime":null,"businessVerified":false,"memberRatingId":null,"memberRatingName":null,"memberRatingAbbreviation":null,"memberRatingLogo":null}
     * count : null
     * responseString : 200~SUCCESS
     * url : null
     * cid : null
     */

    private int code;
    private String message;
    private DataBean data;
    private Object count;
    private String responseString;
    private Object url;
    private Object cid;

    protected AuthErrorResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<AuthErrorResult> CREATOR = new Creator<AuthErrorResult>() {
        @Override
        public AuthErrorResult createFromParcel(Parcel in) {
            return new AuthErrorResult(in);
        }

        @Override
        public AuthErrorResult[] newArray(int size) {
            return new AuthErrorResult[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Object getCid() {
        return cid;
    }

    public void setCid(Object cid) {
        this.cid = cid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
        parcel.writeString(message);
        parcel.writeString(responseString);
    }

    public static class DataBean {
        /**
         * id : 93
         * username : u1561512177863
         * createTime : 2019-06-26 09:23:00
         * mobilePhone : 8618212345678
         * phoneVerified : true
         * email : null
         * emailVerified : false
         * avatar : null
         * realName : null
         * idCard : null
         * realNameRejectReason : 而非我反胃粉色的防守打法胜多负少
         * realVerified : 0
         * fundsVerified : false
         * accountVerified : false
         * transactions : 0
         * firstTransactionTime : null
         * businessVerified : false
         * memberRatingId : null
         * memberRatingName : null
         * memberRatingAbbreviation : null
         * memberRatingLogo : null
         */

        private String id;
        private String username;
        private String createTime;
        private String mobilePhone;
        private boolean phoneVerified;
        private Object email;
        private boolean emailVerified;
        private Object avatar;
        private Object realName;
        private Object idCard;
        private String realNameRejectReason;
        private int realVerified;
        private boolean fundsVerified;
        private boolean accountVerified;
        private int transactions;
        private Object firstTransactionTime;
        private boolean businessVerified;
        private Object memberRatingId;
        private Object memberRatingName;
        private Object memberRatingAbbreviation;
        private Object memberRatingLogo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public boolean isPhoneVerified() {
            return phoneVerified;
        }

        public void setPhoneVerified(boolean phoneVerified) {
            this.phoneVerified = phoneVerified;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public boolean isEmailVerified() {
            return emailVerified;
        }

        public void setEmailVerified(boolean emailVerified) {
            this.emailVerified = emailVerified;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
            this.realName = realName;
        }

        public Object getIdCard() {
            return idCard;
        }

        public void setIdCard(Object idCard) {
            this.idCard = idCard;
        }

        public String getRealNameRejectReason() {
            return realNameRejectReason;
        }

        public void setRealNameRejectReason(String realNameRejectReason) {
            this.realNameRejectReason = realNameRejectReason;
        }

        public int getRealVerified() {
            return realVerified;
        }

        public void setRealVerified(int realVerified) {
            this.realVerified = realVerified;
        }

        public boolean isFundsVerified() {
            return fundsVerified;
        }

        public void setFundsVerified(boolean fundsVerified) {
            this.fundsVerified = fundsVerified;
        }

        public boolean isAccountVerified() {
            return accountVerified;
        }

        public void setAccountVerified(boolean accountVerified) {
            this.accountVerified = accountVerified;
        }

        public int getTransactions() {
            return transactions;
        }

        public void setTransactions(int transactions) {
            this.transactions = transactions;
        }

        public Object getFirstTransactionTime() {
            return firstTransactionTime;
        }

        public void setFirstTransactionTime(Object firstTransactionTime) {
            this.firstTransactionTime = firstTransactionTime;
        }

        public boolean isBusinessVerified() {
            return businessVerified;
        }

        public void setBusinessVerified(boolean businessVerified) {
            this.businessVerified = businessVerified;
        }

        public Object getMemberRatingId() {
            return memberRatingId;
        }

        public void setMemberRatingId(Object memberRatingId) {
            this.memberRatingId = memberRatingId;
        }

        public Object getMemberRatingName() {
            return memberRatingName;
        }

        public void setMemberRatingName(Object memberRatingName) {
            this.memberRatingName = memberRatingName;
        }

        public Object getMemberRatingAbbreviation() {
            return memberRatingAbbreviation;
        }

        public void setMemberRatingAbbreviation(Object memberRatingAbbreviation) {
            this.memberRatingAbbreviation = memberRatingAbbreviation;
        }

        public Object getMemberRatingLogo() {
            return memberRatingLogo;
        }

        public void setMemberRatingLogo(Object memberRatingLogo) {
            this.memberRatingLogo = memberRatingLogo;
        }
    }
}
