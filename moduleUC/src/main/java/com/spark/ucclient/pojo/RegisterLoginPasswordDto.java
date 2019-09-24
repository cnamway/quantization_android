package com.spark.ucclient.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class RegisterLoginPasswordDto implements Parcelable {
    private String mobilePhone;
    private String newPassword;

    protected RegisterLoginPasswordDto(Parcel in) {
        mobilePhone = in.readString();
        newPassword = in.readString();
    }

    public RegisterLoginPasswordDto() {
    }

    public static final Creator<RegisterLoginPasswordDto> CREATOR = new Creator<RegisterLoginPasswordDto>() {
        @Override
        public RegisterLoginPasswordDto createFromParcel(Parcel in) {
            return new RegisterLoginPasswordDto(in);
        }

        @Override
        public RegisterLoginPasswordDto[] newArray(int size) {
            return new RegisterLoginPasswordDto[size];
        }
    };

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobilePhone);
        dest.writeString(newPassword);
    }
}
