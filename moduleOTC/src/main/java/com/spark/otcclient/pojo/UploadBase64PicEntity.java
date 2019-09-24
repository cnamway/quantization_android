package com.spark.otcclient.pojo;

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
public class UploadBase64PicEntity implements Parcelable{

    /**
     * base64Data : string
     * oss : false
     */

    private String base64Data;
    private boolean oss;

    protected UploadBase64PicEntity(Parcel in) {
        base64Data = in.readString();
        oss = in.readByte() != 0;
    }

    public UploadBase64PicEntity() {
    }

    public static final Creator<UploadBase64PicEntity> CREATOR = new Creator<UploadBase64PicEntity>() {
        @Override
        public UploadBase64PicEntity createFromParcel(Parcel in) {
            return new UploadBase64PicEntity(in);
        }

        @Override
        public UploadBase64PicEntity[] newArray(int size) {
            return new UploadBase64PicEntity[size];
        }
    };

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public boolean isOss() {
        return oss;
    }

    public void setOss(boolean oss) {
        this.oss = oss;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(base64Data);
        dest.writeByte((byte) (oss ? 1 : 0));
    }
}
