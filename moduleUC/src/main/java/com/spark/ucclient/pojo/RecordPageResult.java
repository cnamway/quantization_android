package com.spark.ucclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.spark.ucclient.R;

import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.utils.DateUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/13
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RecordPageResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"total":1,"size":10,"pages":1,"current":1,"records":[{"username":"u1563528488226","level":0,"createTime":1563528488000,"inviteeId":"146","realNameStatus":3}]}
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

    protected RecordPageResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(responseString);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecordPageResult> CREATOR = new Creator<RecordPageResult>() {
        @Override
        public RecordPageResult createFromParcel(Parcel in) {
            return new RecordPageResult(in);
        }

        @Override
        public RecordPageResult[] newArray(int size) {
            return new RecordPageResult[size];
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

    public static class DataBean {
        /**
         * total : 1
         * size : 10
         * pages : 1
         * current : 1
         * records : [{"username":"u1563528488226","level":0,"createTime":1563528488000,"inviteeId":"146","realNameStatus":3}]
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
             * username : u1563528488226
             * level : 0
             * createTime : 1563528488000
             * inviteeId : 146
             * realNameStatus : 3
             */

            private String username;
            private int level;
            private long createTime;
            private String inviteeId;
            private int realNameStatus;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getInviteeId() {
                return inviteeId;
            }

            public void setInviteeId(String inviteeId) {
                this.inviteeId = inviteeId;
            }

            public int getRealNameStatus() {
                return realNameStatus;
            }

            public void setRealNameStatus(int realNameStatus) {
                this.realNameStatus = realNameStatus;
            }

            public String formatTime() {
                return DateUtils.formatDate("yyyy-MM-dd hh:mm:ss", createTime);
            }

            public String formatLevel() {
                return (level + 1) + BaseApplication.getInstance().getString(R.string.level);
            }

            public String formatStatue() {
                String str = "";
                switch (realNameStatus) {
                    case 0:
                        str = BaseApplication.getInstance().getString(R.string.unauthorized);
                        break;
                    case 1:
                        str = BaseApplication.getInstance().getString(R.string.approving);
                        break;
                    case 2:
                        str = BaseApplication.getInstance().getString(R.string.approving_error);
                        break;
                    default:
                        str = BaseApplication.getInstance().getString(R.string.authorized);
                        break;
                }
                return str;
            }
        }
    }
}
