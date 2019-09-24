package com.spark.ucclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-23
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PhoneBindBean implements Parcelable {

    /**
     * email : string
     * password : string
     */

    private String phoneNo;
    private String password;

    public PhoneBindBean() {
    }

    protected PhoneBindBean(Parcel in) {
        phoneNo = in.readString();
        password = in.readString();
    }

    public static final Creator<PhoneBindBean> CREATOR = new Creator<PhoneBindBean>() {
        @Override
        public PhoneBindBean createFromParcel(Parcel in) {
            return new PhoneBindBean(in);
        }

        @Override
        public PhoneBindBean[] newArray(int size) {
            return new PhoneBindBean[size];
        }
    };

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(phoneNo);
        parcel.writeString(password);
    }
}
