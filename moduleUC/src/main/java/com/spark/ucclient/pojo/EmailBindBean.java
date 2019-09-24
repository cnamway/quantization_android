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
public class EmailBindBean implements Parcelable {

    /**
     * email : string
     * password : string
     */

    private String email;
    private String password;

    public EmailBindBean() {
    }

    protected EmailBindBean(Parcel in) {
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<EmailBindBean> CREATOR = new Creator<EmailBindBean>() {
        @Override
        public EmailBindBean createFromParcel(Parcel in) {
            return new EmailBindBean(in);
        }

        @Override
        public EmailBindBean[] newArray(int size) {
            return new EmailBindBean[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        parcel.writeString(email);
        parcel.writeString(password);
    }
}
