package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/23
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PayListBean implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : [{"id":40,"memberId":58,"payType":"alipay","payAddress":"18773738oi","qrCodeUrl":"http://nbtconline.oss-ap-northeast-1.aliyuncs.com/2019/05/20/61793af4-ee74-482b-b12f-58f2fe1fbdf4.jpg","bank":null,"branch":null,"status":0,"createTime":1558345865000,"updateTime":1558600477000},{"id":41,"memberId":58,"payType":"wechat","payAddress":"wechat","qrCodeUrl":"http://nbtconline.oss-ap-northeast-1.aliyuncs.com/2019/05/20/164d9b08-b33f-461c-b974-c591a232a0aa.jpg","bank":null,"branch":null,"status":1,"createTime":1558346738000,"updateTime":1558346814000},{"id":44,"memberId":58,"payType":"card","payAddress":"62281329559","qrCodeUrl":null,"bank":"中国邮政储蓄银行","branch":"合肥事俄i","status":1,"createTime":1558347880000,"updateTime":1558347880000}]
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

    protected PayListBean(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<PayListBean> CREATOR = new Creator<PayListBean>() {
        @Override
        public PayListBean createFromParcel(Parcel in) {
            return new PayListBean(in);
        }

        @Override
        public PayListBean[] newArray(int size) {
            return new PayListBean[size];
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(responseString);
        dest.writeTypedList(data);
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 40
         * memberId : 58
         * payType : alipay
         * payAddress : 18773738oi
         * qrCodeUrl : http://nbtconline.oss-ap-northeast-1.aliyuncs.com/2019/05/20/61793af4-ee74-482b-b12f-58f2fe1fbdf4.jpg
         * bank : null
         * branch : null
         * status : 0
         * createTime : 1558345865000
         * updateTime : 1558600477000
         */

        private int id;
        private int memberId;
        private String payType;
        private String payAddress;
        private String qrCodeUrl;
        private String bank;
        private String branch;
        private int status;
        private long createTime;
        private long updateTime;
        private String realName;
        private int isSelected;

        protected DataBean(Parcel in) {
            id = in.readInt();
            memberId = in.readInt();
            payType = in.readString();
            payAddress = in.readString();
            qrCodeUrl = in.readString();
            bank = in.readString();
            branch = in.readString();
            status = in.readInt();
            createTime = in.readLong();
            updateTime = in.readLong();
            realName =in.readString();
            isSelected=in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPayAddress() {
            return payAddress;
        }

        public void setPayAddress(String payAddress) {
            this.payAddress = payAddress;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(int isSelected) {
            this.isSelected = isSelected;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(memberId);
            dest.writeString(payType);
            dest.writeString(payAddress);
            dest.writeString(qrCodeUrl);
            dest.writeString(bank);
            dest.writeString(branch);
            dest.writeInt(status);
            dest.writeLong(createTime);
            dest.writeLong(updateTime);
            dest.writeString(realName);
            dest.writeInt(isSelected);
        }
    }
}
