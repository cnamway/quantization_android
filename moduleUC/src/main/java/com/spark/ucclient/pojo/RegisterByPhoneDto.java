package com.spark.ucclient.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class RegisterByPhoneDto implements Parcelable {
    private String country;
    private String mobilePhone;
    private String password;
    private String promotion;
    private String tid;
    private String username;

    protected RegisterByPhoneDto(Parcel in) {
        country = in.readString();
        mobilePhone = in.readString();
        password = in.readString();
        promotion = in.readString();
        tid = in.readString();
        username = in.readString();
    }

    public RegisterByPhoneDto() {
    }

    public static final Creator<RegisterByPhoneDto> CREATOR = new Creator<RegisterByPhoneDto>() {
        @Override
        public RegisterByPhoneDto createFromParcel(Parcel in) {
            return new RegisterByPhoneDto(in);
        }

        @Override
        public RegisterByPhoneDto[] newArray(int size) {
            return new RegisterByPhoneDto[size];
        }
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(mobilePhone);
        dest.writeString(password);
        dest.writeString(promotion);
        dest.writeString(tid);
        dest.writeString(username);
    }
}
