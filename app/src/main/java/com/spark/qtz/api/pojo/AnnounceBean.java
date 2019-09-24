package com.spark.qtz.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AnnounceBean implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"total":1,"size":10,"pages":1,"current":1,"records":[{"id":8,"header":"公告","redirectUrl":"https://sscoinhelp.zendesk.com/hc/zh-cn/categories/360001974012-%E5%85%AC%E5%91%8A","createTime":1548399512000,"updateTime":1548399514000,"sysLanguage":"CN"}]}
     * count : null
     * responseString : 200~SUCCESS
     * url : null
     * cid : null
     */

    private int code;
    private String message;
    private DataBean data;
    private Object count;
    private String responseString;
    private Object url;
    private Object cid;

    protected AnnounceBean(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<AnnounceBean> CREATOR = new Creator<AnnounceBean>() {
        @Override
        public AnnounceBean createFromParcel(Parcel in) {
            return new AnnounceBean(in);
        }

        @Override
        public AnnounceBean[] newArray(int size) {
            return new AnnounceBean[size];
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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
         * total : 1
         * size : 10
         * pages : 1
         * current : 1
         * records : [{"id":8,"header":"公告","redirectUrl":"https://sscoinhelp.zendesk.com/hc/zh-cn/categories/360001974012-%E5%85%AC%E5%91%8A","createTime":1548399512000,"updateTime":1548399514000,"sysLanguage":"CN"}]
         */

        private int total;
        private int size;
        private int pages;
        private int current;
        private List<RecordsBean> records;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            /**
             * id : 8
             * header : 公告
             * redirectUrl : https://sscoinhelp.zendesk.com/hc/zh-cn/categories/360001974012-%E5%85%AC%E5%91%8A
             * createTime : 1548399512000
             * updateTime : 1548399514000
             * sysLanguage : CN
             */

            private int id;
            private String header;
            private String redirectUrl;
            private long createTime;
            private long updateTime;
            private String sysLanguage;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHeader() {
                return header;
            }

            public void setHeader(String header) {
                this.header = header;
            }

            public String getRedirectUrl() {
                return redirectUrl;
            }

            public void setRedirectUrl(String redirectUrl) {
                this.redirectUrl = redirectUrl;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getSysLanguage() {
                return sysLanguage;
            }

            public void setSysLanguage(String sysLanguage) {
                this.sysLanguage = sysLanguage;
            }
        }
    }
}
