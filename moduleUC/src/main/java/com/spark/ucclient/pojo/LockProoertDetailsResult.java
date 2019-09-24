package com.spark.ucclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.spark.ucclient.R;

import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.utils.DfUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-15
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LockProoertDetailsResult implements Parcelable {
    /**
     * code : 200
     * message : SUCCESS
     * data : {"total":4,"size":10,"pages":1,"current":1,"records":[{"id":"1","memberId":"55","coinId":"BTC","amount":"4.95000000","releaseAmount":"4.95000000","businessId":"w2019071215295947019460","status":1,"type":1,"createTime":1562743800000,"releaseTime":1562743800000,"days":4}]}
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

    protected LockProoertDetailsResult(Parcel in) {
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

    public static final Creator<LockProoertDetailsResult> CREATOR = new Creator<LockProoertDetailsResult>() {
        @Override
        public LockProoertDetailsResult createFromParcel(Parcel in) {
            return new LockProoertDetailsResult(in);
        }

        @Override
        public LockProoertDetailsResult[] newArray(int size) {
            return new LockProoertDetailsResult[size];
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
         * total : 4
         * size : 10
         * pages : 1
         * current : 1
         * records : [{"id":"1","memberId":"55","coinId":"BTC","amount":"4.95000000","releaseAmount":"4.95000000","businessId":"w2019071215295947019460","status":1,"type":1,"createTime":1562743800000,"releaseTime":1562743800000,"days":4}]
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
             * memberId : 55
             * coinId : BTC
             * amount : 4.95000000
             * releaseAmount : 4.95000000
             * businessId : w2019071215295947019460
             * status : 1
             * type : 1
             * createTime : 1562743800000
             * releaseTime : 1562743800000
             * days : 4
             */

            private String id;
            private String memberId;
            private String coinId;
            private String amount;
            private String releaseAmount;
            private String businessId;
            private int status;
            private int type;
            private long createTime;
            private long releaseTime;
            private int days;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getCoinId() {
                return coinId;
            }

            public void setCoinId(String coinId) {
                this.coinId = coinId;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getReleaseAmount() {
                return releaseAmount;
            }

            public void setReleaseAmount(String releaseAmount) {
                this.releaseAmount = releaseAmount;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(long releaseTime) {
                this.releaseTime = releaseTime;
            }

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public String formatDate() {
                return me.spark.mvvm.utils.DateUtils.formatDate("yyyy.MM.dd HH:mm:ss", createTime);
            }

            public String formatType() {
                String typeText = "";
                switch (status) {
                    case 0:
                        typeText = BaseApplication.getInstance().getString(R.string.str_locking);
                        break;
                    case 1:
                        typeText = BaseApplication.getInstance().getString(R.string.str_locked);
                        break;
                    case 2:
                        typeText = BaseApplication.getInstance().getString(R.string.str_un_lock);
                        break;
                }

                return typeText;
            }

            public String formatAmount() {
                return DfUtils.numberFormat(amount,8);
            }

            public String formatReleaseAmount() {
                return DfUtils.numberFormat(releaseAmount,8);            }
        }
    }
}


//    /**
//     * code : 200
//     * message : SUCCESS
//     * data : [{"id":"1","memberId":"55","coinId":"BTC","amount":"4.95000000","releaseAmount":"4.95000000","businessId":"w2019071215295947019460","status":1,"type":1,"createTime":1562743800000,"releaseTime":1562743800000,"days":4}]
//     * count : null
//     * responseString : 200~SUCCESS
//     * url : null
//     * cid : null
//     */
//
//    private int code;
//    private String message;
//    private Object count;
//    private String responseString;
//    private Object url;
//    private Object cid;
//    private List<DataBean> data;
//
//    protected LockProoertDetailsResult(Parcel in) {
//        code = in.readInt();
//        message = in.readString();
//        responseString = in.readString();
//    }
//
//    public static final Creator<LockProoertDetailsResult> CREATOR = new Creator<LockProoertDetailsResult>() {
//        @Override
//        public LockProoertDetailsResult createFromParcel(Parcel in) {
//            return new LockProoertDetailsResult(in);
//        }
//
//        @Override
//        public LockProoertDetailsResult[] newArray(int size) {
//            return new LockProoertDetailsResult[size];
//        }
//    };
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Object getCount() {
//        return count;
//    }
//
//    public void setCount(Object count) {
//        this.count = count;
//    }
//
//    public String getResponseString() {
//        return responseString;
//    }
//
//    public void setResponseString(String responseString) {
//        this.responseString = responseString;
//    }
//
//    public Object getUrl() {
//        return url;
//    }
//
//    public void setUrl(Object url) {
//        this.url = url;
//    }
//
//    public Object getCid() {
//        return cid;
//    }
//
//    public void setCid(Object cid) {
//        this.cid = cid;
//    }
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(code);
//        parcel.writeString(message);
//        parcel.writeString(responseString);
//    }
//
//    public static class DataBean {
//        /**
//         * id : 1
//         * memberId : 55
//         * coinId : BTC
//         * amount : 4.95000000
//         * releaseAmount : 4.95000000
//         * businessId : w2019071215295947019460
//         * status : 1
//         * type : 1
//         * createTime : 1562743800000
//         * releaseTime : 1562743800000
//         * days : 4
//         */
//
//        private String id;
//        private String memberId;
//        private String coinId;
//        private String amount;
//        private String releaseAmount;
//        private String businessId;
//        private int status;
//        private int type;
//        private long createTime;
//        private long releaseTime;
//        private int days;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getMemberId() {
//            return memberId;
//        }
//
//        public void setMemberId(String memberId) {
//            this.memberId = memberId;
//        }
//
//        public String getCoinId() {
//            return coinId;
//        }
//
//        public void setCoinId(String coinId) {
//            this.coinId = coinId;
//        }
//
//        public String getAmount() {
//            return amount;
//        }
//
//        public void setAmount(String amount) {
//            this.amount = amount;
//        }
//
//        public String getReleaseAmount() {
//            return releaseAmount;
//        }
//
//        public void setReleaseAmount(String releaseAmount) {
//            this.releaseAmount = releaseAmount;
//        }
//
//        public String getBusinessId() {
//            return businessId;
//        }
//
//        public void setBusinessId(String businessId) {
//            this.businessId = businessId;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public long getCreateTime() {
//            return createTime;
//        }
//
//        public void setCreateTime(long createTime) {
//            this.createTime = createTime;
//        }
//
//        public long getReleaseTime() {
//            return releaseTime;
//        }
//
//        public void setReleaseTime(long releaseTime) {
//            this.releaseTime = releaseTime;
//        }
//
//        public int getDays() {
//            return days;
//        }
//
//        public void setDays(int days) {
//            this.days = days;
//        }
//
//
//        public String formatDate() {
//            return me.spark.mvvm.utils.DateUtils.formatDate("yyyy.MM.dd HH:mm:ss", createTime);
//        }
//
//        public String formatType() {
//            String typeText = "";
//            switch (status) {
//                case 0:
//                    typeText = "锁仓中 ";
//                    break;
//                case 1:
//                    typeText = "锁仓到期";
//                    break;
//                case 2:
//                    typeText = "已解锁";
//                    break;
//            }
//
//            return typeText;
//        }
//    }
