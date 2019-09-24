package com.spark.acclient.pojo;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
@SuppressLint("ParcelCreator")
public class CoinSupportBean implements Parcelable {

    /**
     * code : 200
     * message : SUCCESS
     * data : [{"coinName":"CNT","nameCn":"CNT","unit":"cnt","mainCoinType":"520","coinType":"520","scale":8,"iconUrl":null,"enableAutoWithdraw":1,"enableWithdraw":1,"enableGenerate":1,"rpcType":2,"isPlatformCoin":0,"withdrawFeeType":2,"withdrawFee":0.01,"maxWithdrawFee":10,"maxWithdrawAmount":1000,"minWithdrawFee":0.1,"minWithdrawAmount":5,"depositFeeType":2,"depositFee":0.01,"maxDepositFee":100,"minDepositFee":0,"minDepositAmount":0.01,"withdrawThreshold":1000,"dailyMaxWithdrawAmount":1000,"status":1,"sort":3}]
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
         * coinName : CNT
         * nameCn : CNT
         * unit : cnt
         * mainCoinType : 520
         * coinType : 520
         * scale : 8
         * iconUrl : null
         * enableAutoWithdraw : 1
         * enableWithdraw : 1
         * enableGenerate : 1
         * rpcType : 2
         * isPlatformCoin : 0
         * withdrawFeeType : 2
         * withdrawFee : 0.01
         * maxWithdrawFee : 10.0
         * maxWithdrawAmount : 1000.0
         * minWithdrawFee : 0.1
         * minWithdrawAmount : 5.0
         * depositFeeType : 2
         * depositFee : 0.01
         * maxDepositFee : 100.0
         * minDepositFee : 0.0
         * minDepositAmount : 0.01
         * withdrawThreshold : 1000.0
         * dailyMaxWithdrawAmount : 1000
         * status : 1
         * sort : 3
         */

        private String coinName;
        private String nameCn;
        private String unit;
        private String mainCoinType;
        private String coinType;
        private int scale;
        private String iconUrl;
        private int enableAutoWithdraw;
        private int enableWithdraw;
        private int enableGenerate;
        private int rpcType;
        private int isPlatformCoin;
        private int withdrawFeeType;
        private double withdrawFee;
        private double maxWithdrawFee;
        private double maxWithdrawAmount;
        private double minWithdrawFee;
        private double minWithdrawAmount;
        private int depositFeeType;
        private double depositFee;
        private double maxDepositFee;
        private double minDepositFee;
        private double minDepositAmount;
        private double withdrawThreshold;
        private int dailyMaxWithdrawAmount;
        private int status;
        private int sort;

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getNameCn() {
            return nameCn;
        }

        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getMainCoinType() {
            return mainCoinType;
        }

        public void setMainCoinType(String mainCoinType) {
            this.mainCoinType = mainCoinType;
        }

        public String getCoinType() {
            return coinType;
        }

        public void setCoinType(String coinType) {
            this.coinType = coinType;
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getEnableAutoWithdraw() {
            return enableAutoWithdraw;
        }

        public void setEnableAutoWithdraw(int enableAutoWithdraw) {
            this.enableAutoWithdraw = enableAutoWithdraw;
        }

        public int getEnableWithdraw() {
            return enableWithdraw;
        }

        public void setEnableWithdraw(int enableWithdraw) {
            this.enableWithdraw = enableWithdraw;
        }

        public int getEnableGenerate() {
            return enableGenerate;
        }

        public void setEnableGenerate(int enableGenerate) {
            this.enableGenerate = enableGenerate;
        }

        public int getRpcType() {
            return rpcType;
        }

        public void setRpcType(int rpcType) {
            this.rpcType = rpcType;
        }

        public int getIsPlatformCoin() {
            return isPlatformCoin;
        }

        public void setIsPlatformCoin(int isPlatformCoin) {
            this.isPlatformCoin = isPlatformCoin;
        }

        public int getWithdrawFeeType() {
            return withdrawFeeType;
        }

        public void setWithdrawFeeType(int withdrawFeeType) {
            this.withdrawFeeType = withdrawFeeType;
        }

        public double getWithdrawFee() {
            return withdrawFee;
        }

        public void setWithdrawFee(double withdrawFee) {
            this.withdrawFee = withdrawFee;
        }

        public double getMaxWithdrawFee() {
            return maxWithdrawFee;
        }

        public void setMaxWithdrawFee(double maxWithdrawFee) {
            this.maxWithdrawFee = maxWithdrawFee;
        }

        public double getMaxWithdrawAmount() {
            return maxWithdrawAmount;
        }

        public void setMaxWithdrawAmount(double maxWithdrawAmount) {
            this.maxWithdrawAmount = maxWithdrawAmount;
        }

        public double getMinWithdrawFee() {
            return minWithdrawFee;
        }

        public void setMinWithdrawFee(double minWithdrawFee) {
            this.minWithdrawFee = minWithdrawFee;
        }

        public double getMinWithdrawAmount() {
            return minWithdrawAmount;
        }

        public void setMinWithdrawAmount(double minWithdrawAmount) {
            this.minWithdrawAmount = minWithdrawAmount;
        }

        public int getDepositFeeType() {
            return depositFeeType;
        }

        public void setDepositFeeType(int depositFeeType) {
            this.depositFeeType = depositFeeType;
        }

        public double getDepositFee() {
            return depositFee;
        }

        public void setDepositFee(double depositFee) {
            this.depositFee = depositFee;
        }

        public double getMaxDepositFee() {
            return maxDepositFee;
        }

        public void setMaxDepositFee(double maxDepositFee) {
            this.maxDepositFee = maxDepositFee;
        }

        public double getMinDepositFee() {
            return minDepositFee;
        }

        public void setMinDepositFee(double minDepositFee) {
            this.minDepositFee = minDepositFee;
        }

        public double getMinDepositAmount() {
            return minDepositAmount;
        }

        public void setMinDepositAmount(double minDepositAmount) {
            this.minDepositAmount = minDepositAmount;
        }

        public double getWithdrawThreshold() {
            return withdrawThreshold;
        }

        public void setWithdrawThreshold(double withdrawThreshold) {
            this.withdrawThreshold = withdrawThreshold;
        }

        public int getDailyMaxWithdrawAmount() {
            return dailyMaxWithdrawAmount;
        }

        public void setDailyMaxWithdrawAmount(int dailyMaxWithdrawAmount) {
            this.dailyMaxWithdrawAmount = dailyMaxWithdrawAmount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }



    }
}
