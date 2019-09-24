package com.example.modulecfd.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-08-01
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdOrderQueryBean implements Parcelable {
    public String symbol;
    public int pageNo;
    public int pageSize;

    public CfdOrderQueryBean() {
    }

    protected CfdOrderQueryBean(Parcel in) {
        symbol = in.readString();
        pageNo = in.readInt();
        pageSize = in.readInt();
    }

    public static final Creator<CfdOrderQueryBean> CREATOR = new Creator<CfdOrderQueryBean>() {
        @Override
        public CfdOrderQueryBean createFromParcel(Parcel in) {
            return new CfdOrderQueryBean(in);
        }

        @Override
        public CfdOrderQueryBean[] newArray(int size) {
            return new CfdOrderQueryBean[size];
        }
    };

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(symbol);
        parcel.writeInt(pageNo);
        parcel.writeInt(pageSize);
    }
}
