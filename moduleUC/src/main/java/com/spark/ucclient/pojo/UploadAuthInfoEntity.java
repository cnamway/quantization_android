package com.spark.ucclient.pojo;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/16
 * 描    述：
 * 修订历史：
 * ================================================
 */
@SuppressLint("ParcelCreator")
public class UploadAuthInfoEntity implements Parcelable {

    /**
     * certifiedType : 0
     * idCardNumber : string
     * identityCardImgFront : string
     * identityCardImgInHand : string
     * identityCardImgReverse : string
     * realName : string
     */

    private long certifiedType;
    private String idCardNumber;
    private String identityCardImgFront;
    private String identityCardImgInHand;
    private String identityCardImgReverse;
    private String realName;

    public long getCertifiedType() {
        return certifiedType;
    }

    public void setCertifiedType(long certifiedType) {
        this.certifiedType = certifiedType;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
