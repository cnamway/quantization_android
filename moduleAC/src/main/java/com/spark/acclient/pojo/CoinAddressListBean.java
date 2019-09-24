package com.spark.acclient.pojo;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/22
 * 描    述：
 * 修订历史：
 * ================================================
 */
@SuppressLint("ParcelCreator")
public class CoinAddressListBean implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : [{"id":18,"address":"wwerrty43555","createTime":1558512546000,"deleteTime":1558512546000,"memberId":54,"remark":"test","status":0,"coinId":"CNT"},{"id":22,"address":"zjhebsbabn64949djsn","createTime":1558513494000,"deleteTime":1558512546000,"memberId":54,"remark":"test","status":0,"coinId":"CNT"}]
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
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static class DataBean {
        /**
         * id : 18
         * address : wwerrty43555
         * createTime : 1558512546000
         * deleteTime : 1558512546000
         * memberId : 54
         * remark : test
         * status : 0
         * coinId : CNT
         */

        private int id;
        private String address;
        private long createTime;
        private long deleteTime;
        private int memberId;
        private String remark;
        private int status;
        private String coinId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getDeleteTime() {
            return deleteTime;
        }

        public void setDeleteTime(long deleteTime) {
            this.deleteTime = deleteTime;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCoinId() {
            return coinId;
        }

        public void setCoinId(String coinId) {
            this.coinId = coinId;
        }
    }
}
