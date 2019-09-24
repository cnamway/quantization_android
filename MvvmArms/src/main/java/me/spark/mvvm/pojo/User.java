package me.spark.mvvm.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    //接口返回
    private String id;
    private String username;
    private String mobilePhone;
    private String email;
    private String country;
    private String area;
    private int status;
    private String avatar;
    private long registrationTime;
    private int inviterId;
    private String promotionCode;
    private String realName;
    private String certifiedType;
    private String idCardNumber;
    private int realNameStatus;
    private int googleAuthStatus;
    private int fundsVerified;
    private int transactionStatus;
    private String inviteCode;
    private int logintype;
    private String areaCode;

    public User() {
    }


    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        mobilePhone = in.readString();
        email = in.readString();
        country = in.readString();
        area = in.readString();
        status = in.readInt();
        avatar = in.readString();
        registrationTime = in.readLong();
        inviterId = in.readInt();
        promotionCode = in.readString();
        realName = in.readString();
        certifiedType = in.readString();
        idCardNumber = in.readString();
        realNameStatus = in.readInt();
        googleAuthStatus = in.readInt();
        fundsVerified = in.readInt();
        transactionStatus = in.readInt();
        inviteCode = in.readString();
        logintype = in.readInt();
        areaCode = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(long registrationTime) {
        this.registrationTime = registrationTime;
    }

    public int getInviterId() {
        return inviterId;
    }

    public void setInviterId(int inviterId) {
        this.inviterId = inviterId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCertifiedType() {
        return certifiedType;
    }

    public void setCertifiedType(String certifiedType) {
        this.certifiedType = certifiedType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public int getRealNameStatus() {
        return realNameStatus;
    }

    public void setRealNameStatus(int realNameStatus) {
        this.realNameStatus = realNameStatus;
    }

    public int getGoogleAuthStatus() {
        return googleAuthStatus;
    }

    public void setGoogleAuthStatus(int googleAuthStatus) {
        this.googleAuthStatus = googleAuthStatus;
    }

    public int getFundsVerified() {
        return fundsVerified;
    }

    public void setFundsVerified(int fundsVerified) {
        this.fundsVerified = fundsVerified;
    }

    public int getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(int transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getLogintype() {
        return logintype;
    }

    public void setLogintype(int logintype) {
        this.logintype = logintype;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(mobilePhone);
        dest.writeString(email);
        dest.writeString(country);
        dest.writeString(area);
        dest.writeInt(status);
        dest.writeString(avatar);
        dest.writeLong(registrationTime);
        dest.writeInt(inviterId);
        dest.writeString(promotionCode);
        dest.writeString(realName);
        dest.writeString(certifiedType);
        dest.writeString(idCardNumber);
        dest.writeInt(realNameStatus);
        dest.writeInt(googleAuthStatus);
        dest.writeInt(fundsVerified);
        dest.writeInt(transactionStatus);
        dest.writeString(inviteCode);
        dest.writeInt(logintype);
        dest.writeString(areaCode);
    }
}
