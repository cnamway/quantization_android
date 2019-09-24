package com.spark.qtz.api.pojo;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：首页BannerBean
 * 修订历史：
 * ================================================
 */
@SuppressLint("ParcelCreator")
public class BannerBean implements Parcelable {


    /**
     * code : 200
     * message : SUCCESS
     * data : {"id":1,"name":"Bitrade","favicon":"http://nbtconline.oss-ap-northeast-1.aliyuncs.com/2019/03/06/26719656-9f48-4236-8357-2a0c35059d16.png","logo":"http://nbtconline.oss-ap-northeast-1.aliyuncs.com/2019/03/06/26719656-9f48-4236-8357-2a0c35059d16.png","carousel":"[{\"picture\":[{\"language\":\"zh\",\"url\":\"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/pc.png\"},{\"language\":\"en\",\"url\":\"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/app.png\"}],\"link\":\"index\",\"index\":0,\"sort\":0}]","appCarousel":"[{\"picture\":[{\"language\":\"zh\",\"url\":\"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/pc.png\"},{\"language\":\"en\",\"url\":\"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/app.png\"}],\"link\":\"index\",\"index\":0,\"sort\":0}]","location":"天鹅湖万达","phone":"13866191522","icp":"string","worktime":"8:00-10:00","businessEmail":"752017658@qq.com","serviceEmail":"752107658@qq.com","website":"http://www.nbtcpay.com","postcode":"00000","promotionPrefix":"http://www.nbtcpay.com","status":1}
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static class DataBean {
        /**
         * id : 1
         * name : Bitrade
         * favicon : http://nbtconline.oss-ap-northeast-1.aliyuncs.com/2019/03/06/26719656-9f48-4236-8357-2a0c35059d16.png
         * logo : http://nbtconline.oss-ap-northeast-1.aliyuncs.com/2019/03/06/26719656-9f48-4236-8357-2a0c35059d16.png
         * carousel : [{"picture":[{"language":"zh","url":"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/pc.png"},{"language":"en","url":"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/app.png"}],"link":"index","index":0,"sort":0}]
         * appCarousel : [{"picture":[{"language":"zh","url":"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/pc.png"},{"language":"en","url":"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/app.png"}],"link":"index","index":0,"sort":0}]
         * location : 天鹅湖万达
         * phone : 13866191522
         * icp : string
         * worktime : 8:00-10:00
         * businessEmail : 752017658@qq.com
         * serviceEmail : 752107658@qq.com
         * website : http://www.nbtcpay.com
         * postcode : 00000
         * promotionPrefix : http://www.nbtcpay.com
         * status : 1
         */

        private int id;
        private String name;
        private String favicon;
        private String logo;
        private String carousel;
        private String appCarousel;
        private String location;
        private String phone;
        private String icp;
        private String worktime;
        private String businessEmail;
        private String serviceEmail;
        private String website;
        private String postcode;
        private String promotionPrefix;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFavicon() {
            return favicon;
        }

        public void setFavicon(String favicon) {
            this.favicon = favicon;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCarousel() {
            return carousel;
        }

        public void setCarousel(String carousel) {
            this.carousel = carousel;
        }

        public String getAppCarousel() {
            return appCarousel;
        }

        public void setAppCarousel(String appCarousel) {
            this.appCarousel = appCarousel;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIcp() {
            return icp;
        }

        public void setIcp(String icp) {
            this.icp = icp;
        }

        public String getWorktime() {
            return worktime;
        }

        public void setWorktime(String worktime) {
            this.worktime = worktime;
        }

        public String getBusinessEmail() {
            return businessEmail;
        }

        public void setBusinessEmail(String businessEmail) {
            this.businessEmail = businessEmail;
        }

        public String getServiceEmail() {
            return serviceEmail;
        }

        public void setServiceEmail(String serviceEmail) {
            this.serviceEmail = serviceEmail;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getPromotionPrefix() {
            return promotionPrefix;
        }

        public void setPromotionPrefix(String promotionPrefix) {
            this.promotionPrefix = promotionPrefix;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
