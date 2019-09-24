package com.spark.ucclient.pojo;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/16
 * 描    述：实名认证详情
 * 修订历史：
 * ================================================
 */
@SuppressLint("ParcelCreator")
public class AuthInfoEntity implements Parcelable{

    /**
     * cid : string
     * code : 0
     * count : 0
     * data : {"auditStatus":0,"certifiedType":0,"createTime":"2019-05-16T09:04:40.716Z","id":0,"idCardNumber":"string","identityCardImgFront":"string","identityCardImgInHand":"string","identityCardImgReverse":"string","memberId":0,"realName":"string","rejectReason":"string","updateTime":"2019-05-16T09:04:40.716Z"}
     * message : string
     * responseString : string
     * url : string
     */

    private String cid;
    private int code;
    private int count;
    private DataBean data;
    private String message;
    private String responseString;
    private String url;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
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
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static class DataBean {
        /**
         * auditStatus : 审核状态 0-未认证 1待审核 2-审核不通过 3-已认证
         * certifiedType : 证件类型 0:身份证 1护照
         * createTime : 2019-05-16T09:04:40.716Z
         * id : 0
         * idCardNumber : string
         * identityCardImgFront : string
         * identityCardImgInHand : string
         * identityCardImgReverse : string
         * memberId : 0
         * realName : string
         * rejectReason : 驳回理由
         * updateTime : 2019-05-16T09:04:40.716Z
         */

        private int auditStatus;
        private int certifiedType;
        private String createTime;
        private int id;
        private String idCardNumber;
        private String identityCardImgFront;
        private String identityCardImgInHand;
        private String identityCardImgReverse;
        private int memberId;
        private String realName;
        private String rejectReason;
        private String updateTime;

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public int getCertifiedType() {
            return certifiedType;
        }

        public void setCertifiedType(int certifiedType) {
            this.certifiedType = certifiedType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdCardNumber() {
            return idCardNumber;
        }

        public void setIdCardNumber(String idCardNumber) {
            this.idCardNumber = idCardNumber;
        }

        public String getIdentityCardImgFront() {
            return identityCardImgFront;
        }

        public void setIdentityCardImgFront(String identityCardImgFront) {
            this.identityCardImgFront = identityCardImgFront;
        }

        public String getIdentityCardImgInHand() {
            return identityCardImgInHand;
        }

        public void setIdentityCardImgInHand(String identityCardImgInHand) {
            this.identityCardImgInHand = identityCardImgInHand;
        }

        public String getIdentityCardImgReverse() {
            return identityCardImgReverse;
        }

        public void setIdentityCardImgReverse(String identityCardImgReverse) {
            this.identityCardImgReverse = identityCardImgReverse;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
