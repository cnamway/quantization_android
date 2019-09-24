package com.spark.quantizationclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import me.spark.mvvm.utils.MathUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class IndexAccountResult implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"total":4,"size":20,"pages":1,"current":1,"records":[{"id":46,"name":"fp116252765ja@163.com","email":"fp116252765ja@163.com","account":"115570","apiKey":"fY6h311F6twar-eRDdjZ0aoE","apiSecret":"Ao0u6HyXnITLiYgupQaJvXxF6KzHXMRaZ9SPEKEHKU1vkqPe","currency":"XBt","balance":150.1861307,"frozenBalance":11.5267936,"isAssigned":1,"updateTime":1567757449000},{"id":51,"name":"ec6222069494hn@163.com","email":"ec6222069494hn@163.com","account":"115160","apiKey":"4KqKUFUjh-j1zKcCTp2X_N02","apiSecret":"Ed_UoxLy9cVxwi-DhGp-Y956FexQT1q-yAdAbtCVjTaMtx7d","currency":"XBt","balance":244.2922787,"frozenBalance":1.4425353,"isAssigned":1,"updateTime":1567429816000},{"id":43,"name":"qichen","email":"1030726326@qq.com","account":"236464","apiKey":"jmWtSfwSHhTOEsY_fXE2a246","apiSecret":"ixvoKM3Ys9EPp5RPKnMiBYm2Y-fvLiwB2EkOxCJaC5w9Nckg","currency":"XBt","balance":0.0101925,"frozenBalance":0.241246,"isAssigned":1,"updateTime":1567429774000},{"id":42,"name":"文罗","email":"1079599272@qq.com","account":"147116","apiKey":"pDL48IM6iXlh3QT53uUECjD6","apiSecret":"i0M-mVBIIj2i-wXhReA0LMOC1ayu3Kr78YchpFZcvcJM-RjB","currency":"XBt","balance":0.1088505,"frozenBalance":4.606E-4,"isAssigned":1,"updateTime":1567424605000}]}
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
         * size : 20
         * pages : 1
         * current : 1
         * records : [{"id":46,"name":"fp116252765ja@163.com","email":"fp116252765ja@163.com","account":"115570","apiKey":"fY6h311F6twar-eRDdjZ0aoE","apiSecret":"Ao0u6HyXnITLiYgupQaJvXxF6KzHXMRaZ9SPEKEHKU1vkqPe","currency":"XBt","balance":150.1861307,"frozenBalance":11.5267936,"isAssigned":1,"updateTime":1567757449000},{"id":51,"name":"ec6222069494hn@163.com","email":"ec6222069494hn@163.com","account":"115160","apiKey":"4KqKUFUjh-j1zKcCTp2X_N02","apiSecret":"Ed_UoxLy9cVxwi-DhGp-Y956FexQT1q-yAdAbtCVjTaMtx7d","currency":"XBt","balance":244.2922787,"frozenBalance":1.4425353,"isAssigned":1,"updateTime":1567429816000},{"id":43,"name":"qichen","email":"1030726326@qq.com","account":"236464","apiKey":"jmWtSfwSHhTOEsY_fXE2a246","apiSecret":"ixvoKM3Ys9EPp5RPKnMiBYm2Y-fvLiwB2EkOxCJaC5w9Nckg","currency":"XBt","balance":0.0101925,"frozenBalance":0.241246,"isAssigned":1,"updateTime":1567429774000},{"id":42,"name":"文罗","email":"1079599272@qq.com","account":"147116","apiKey":"pDL48IM6iXlh3QT53uUECjD6","apiSecret":"i0M-mVBIIj2i-wXhReA0LMOC1ayu3Kr78YchpFZcvcJM-RjB","currency":"XBt","balance":0.1088505,"frozenBalance":4.606E-4,"isAssigned":1,"updateTime":1567424605000}]
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
             * id : 46
             * name : fp116252765ja@163.com
             * email : fp116252765ja@163.com
             * account : 115570
             * apiKey : fY6h311F6twar-eRDdjZ0aoE
             * apiSecret : Ao0u6HyXnITLiYgupQaJvXxF6KzHXMRaZ9SPEKEHKU1vkqPe
             * currency : XBt
             * balance : 150.1861307
             * frozenBalance : 11.5267936
             * isAssigned : 1
             * updateTime : 1567757449000
             */

            private String id;
            private String name;
            private String email;
            private String account;
            private String apiKey;
            private String apiSecret;
            private String currency;
            private double balance;
            private double frozenBalance;
            private int isAssigned;
            private long updateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getApiKey() {
                return apiKey;
            }

            public void setApiKey(String apiKey) {
                this.apiKey = apiKey;
            }

            public String getApiSecret() {
                return apiSecret;
            }

            public void setApiSecret(String apiSecret) {
                this.apiSecret = apiSecret;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public double getFrozenBalance() {
                return frozenBalance;
            }

            public void setFrozenBalance(double frozenBalance) {
                this.frozenBalance = frozenBalance;
            }

            public int getIsAssigned() {
                return isAssigned;
            }

            public void setIsAssigned(int isAssigned) {
                this.isAssigned = isAssigned;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }


            public String getFroznBlance() {

                return frozenBalance + " " + currency;
            }
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeString(this.responseString);
    }

    public IndexAccountResult() {
    }

    protected IndexAccountResult(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.count = in.readParcelable(Object.class.getClassLoader());
        this.responseString = in.readString();
        this.url = in.readParcelable(Object.class.getClassLoader());
        this.cid = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Creator<IndexAccountResult> CREATOR = new Creator<IndexAccountResult>() {
        @Override
        public IndexAccountResult createFromParcel(Parcel source) {
            return new IndexAccountResult(source);
        }

        @Override
        public IndexAccountResult[] newArray(int size) {
            return new IndexAccountResult[size];
        }
    };


}
