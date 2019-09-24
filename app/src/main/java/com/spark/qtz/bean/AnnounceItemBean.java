package com.spark.qtz.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AnnounceItemBean implements Parcelable {
    private String title;
    private String time;

    public AnnounceItemBean() {
    }

    protected AnnounceItemBean(Parcel in) {
        title = in.readString();
        time = in.readString();
    }

    public static final Creator<AnnounceItemBean> CREATOR = new Creator<AnnounceItemBean>() {
        @Override
        public AnnounceItemBean createFromParcel(Parcel in) {
            return new AnnounceItemBean(in);
        }

        @Override
        public AnnounceItemBean[] newArray(int size) {
            return new AnnounceItemBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(time);
    }
}
