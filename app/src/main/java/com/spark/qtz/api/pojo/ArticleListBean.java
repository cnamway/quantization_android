package com.spark.qtz.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ArticleListBean implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : [{"id":15,"parentTypeName":"17","name":"费率说明","sysLanguage":"CN","redirectUrl":""}]
     * count : null
     * responseString : 200~SUCCESS
     * url : null
     * cid : null
     */

    private int code;
    private String message;
    private Object count;
    private String responseString;
    private Object url;
    private Object cid;
    private List<DataBean> data;

    protected ArticleListBean(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<ArticleListBean> CREATOR = new Creator<ArticleListBean>() {
        @Override
        public ArticleListBean createFromParcel(Parcel in) {
            return new ArticleListBean(in);
        }

        @Override
        public ArticleListBean[] newArray(int size) {
            return new ArticleListBean[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Object getCid() {
        return cid;
    }

    public void setCid(Object cid) {
        this.cid = cid;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
        parcel.writeString(message);
        parcel.writeString(responseString);
    }

    public static class DataBean {
        /**
         * id : 15
         * parentTypeName : 17
         * name : 费率说明
         * sysLanguage : CN
         * redirectUrl :
         */

        private int id;
        private String parentTypeName;
        private String name;
        private String sysLanguage;
        private String redirectUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getParentTypeName() {
            return parentTypeName;
        }

        public void setParentTypeName(String parentTypeName) {
            this.parentTypeName = parentTypeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSysLanguage() {
            return sysLanguage;
        }

        public void setSysLanguage(String sysLanguage) {
            this.sysLanguage = sysLanguage;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }
    }
}
