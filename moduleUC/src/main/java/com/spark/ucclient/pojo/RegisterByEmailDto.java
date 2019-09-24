package com.spark.ucclient.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class RegisterByEmailDto implements Parcelable {
    private String country;
    private String email;
    private String password;
    private String promotion;
    private String tid;
    private String username;

    protected RegisterByEmailDto(Parcel in) {
        country = in.readString();
        email = in.readString();
        password = in.readString();
        promotion = in.readString();
        tid = in.readString();
        username = in.readString();
    }

    public RegisterByEmailDto() {
    }

    public static final Creator<RegisterByEmailDto> CREATOR = new Creator<RegisterByEmailDto>() {
        @Override
        public RegisterByEmailDto createFromParcel(Parcel in) {
            return new RegisterByEmailDto(in);
        }

        @Override
        public RegisterByEmailDto[] newArray(int size) {
            return new RegisterByEmailDto[size];
        }
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


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
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(promotion);
        dest.writeString(tid);
        dest.writeString(username);
    }
}
