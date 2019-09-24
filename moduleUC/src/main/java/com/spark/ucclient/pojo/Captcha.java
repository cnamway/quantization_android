package com.spark.ucclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/12
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class Captcha implements Parcelable {

    /**
     * geetest_challenge : 22cfba9308b3700cb91bee7e0b2e1e1fgm
     * geetest_validate : 3134c743fdb5938b0bbbfe58987d3408
     * geetest_seccode : 3134c743fdb5938b0bbbfe58987d3408|jordan
     */

    private String geetest_challenge;
    private String geetest_validate;
    private String geetest_seccode;

    protected Captcha(Parcel in) {
        geetest_challenge = in.readString();
        geetest_validate = in.readString();
        geetest_seccode = in.readString();
    }

    public static final Creator<Captcha> CREATOR = new Creator<Captcha>() {
        @Override
        public Captcha createFromParcel(Parcel in) {
            return new Captcha(in);
        }

        @Override
        public Captcha[] newArray(int size) {
            return new Captcha[size];
        }
    };

    public String getGeetest_challenge() {
        return geetest_challenge;
    }

    public void setGeetest_challenge(String geetest_challenge) {
        this.geetest_challenge = geetest_challenge;
    }

    public String getGeetest_validate() {
        return geetest_validate;
    }

    public void setGeetest_validate(String geetest_validate) {
        this.geetest_validate = geetest_validate;
    }

    public String getGeetest_seccode() {
        return geetest_seccode;
    }

    public void setGeetest_seccode(String geetest_seccode) {
        this.geetest_seccode = geetest_seccode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(geetest_challenge);
        dest.writeString(geetest_validate);
        dest.writeString(geetest_seccode);
    }
}
