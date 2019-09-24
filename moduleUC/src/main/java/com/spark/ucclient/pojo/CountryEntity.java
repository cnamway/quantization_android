package com.spark.ucclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/8
 * 描    述：
 * 修订历史：
 * ================================================
 */

public class CountryEntity implements Parcelable {

    /**
     * zhName : 中国
     * areaCode : 86
     * enName : China
     * language : 中文
     * localCurrency : CNY
     * sort : 0
     * sysLanguage : null
     */

    private String zhName;
    private String areaCode;
    private String enName;
    private String language;
    private String localCurrency;
    private int sort;
    private Object sysLanguage;

    protected CountryEntity(Parcel in) {
        zhName = in.readString();
        areaCode = in.readString();
        enName = in.readString();
        language = in.readString();
        localCurrency = in.readString();
        sort = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(zhName);
        dest.writeString(areaCode);
        dest.writeString(enName);
        dest.writeString(language);
        dest.writeString(localCurrency);
        dest.writeInt(sort);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountryEntity> CREATOR = new Creator<CountryEntity>() {
        @Override
        public CountryEntity createFromParcel(Parcel in) {
            return new CountryEntity(in);
        }

        @Override
        public CountryEntity[] newArray(int size) {
            return new CountryEntity[size];
        }
    };

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocalCurrency() {
        return localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getSysLanguage() {
        return sysLanguage;
    }

    public void setSysLanguage(Object sysLanguage) {
        this.sysLanguage = sysLanguage;
    }
}
