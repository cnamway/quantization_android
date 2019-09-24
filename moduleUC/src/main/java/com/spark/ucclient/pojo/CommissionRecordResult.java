package com.spark.ucclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import me.spark.mvvm.utils.DateUtils;
import me.spark.mvvm.utils.DfUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/13
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CommissionRecordResult implements Parcelable{
    /**
     * code : 200
     * message : SUCCESS
     * data : {"total":4,"size":10,"pages":1,"current":1,"records":[{"id":"1","inviteeName":"","inviteeId":1,"level":1,"amount":"20.00000000","rewardDate":1560775999000,"selfId":1,"inviterId":1,"inviterName":"","coinName":""}]}
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

    protected CommissionRecordResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<CommissionRecordResult> CREATOR = new Creator<CommissionRecordResult>() {
        @Override
        public CommissionRecordResult createFromParcel(Parcel in) {
            return new CommissionRecordResult(in);
        }

        @Override
        public CommissionRecordResult[] newArray(int size) {
            return new CommissionRecordResult[size];
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
         * total : 4
         * size : 10
         * pages : 1
         * current : 1
         * records : [{"id":"1","inviteeName":"","inviteeId":1,"level":1,"amount":"20.00000000","rewardDate":1560775999000,"selfId":1,"inviterId":1,"inviterName":"","coinName":""}]
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
             * id : 1
             * inviteeName :
             * inviteeId : 1
             * level : 1
             * amount : 20.00000000
             * rewardDate : 1560775999000
             * selfId : 1
             * inviterId : 1
             * inviterName :
             * coinName :
             */

            private String id;
            private String inviteeName;
            private int inviteeId;
            private int level;
            private String amount;
            private long rewardDate;
            private int selfId;
            private int inviterId;
            private String inviterName;
            private String coinName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getInviteeName() {
                return inviteeName;
            }

            public void setInviteeName(String inviteeName) {
                this.inviteeName = inviteeName;
            }

            public int getInviteeId() {
                return inviteeId;
            }

            public void setInviteeId(int inviteeId) {
                this.inviteeId = inviteeId;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public long getRewardDate() {
                return rewardDate;
            }

            public void setRewardDate(long rewardDate) {
                this.rewardDate = rewardDate;
            }

            public int getSelfId() {
                return selfId;
            }

            public void setSelfId(int selfId) {
                this.selfId = selfId;
            }

            public int getInviterId() {
                return inviterId;
            }

            public void setInviterId(int inviterId) {
                this.inviterId = inviterId;
            }

            public String getInviterName() {
                return inviterName;
            }

            public void setInviterName(String inviterName) {
                this.inviterName = inviterName;
            }

            public String getCoinName() {
                return coinName;
            }

            public void setCoinName(String coinName) {
                this.coinName = coinName;
            }

            public String formatTime() {
                return DateUtils.formatDate("yyyy-MM-dd hh:mm:ss", rewardDate);
            }

            public String formatName() {
                return inviteeName == null ? "" : inviteeName;
            }

            public String formatNumber() {
                return DfUtils.numberFormat(amount,4);
            }
        }
    }
}
