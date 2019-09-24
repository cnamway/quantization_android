package com.spark.casclient.pojo;

/**
 * Created by Administrator on 2019/8/29 0029.
 */

public class LoginBean {

    /**
     * tgc : TGT-36-pJHFs0yGNfODyRLTmQmFmswqittU3HZZbEyUVDz5KR7pVY60WTodnxYzNzQWTY9JNcclocalhost
     * is_new_device : 1
     * notice_data : {"phone":"17756094740","google":"","email":"ccs7727@163.com"}
     */

    private String tgc;
    private int is_new_device;
    private NoticeDataBean notice_data;

    public String getTgc() {
        return tgc;
    }

    public void setTgc(String tgc) {
        this.tgc = tgc;
    }

    public int getIs_new_device() {
        return is_new_device;
    }

    public void setIs_new_device(int is_new_device) {
        this.is_new_device = is_new_device;
    }

    public NoticeDataBean getNotice_data() {
        return notice_data;
    }

    public void setNotice_data(NoticeDataBean notice_data) {
        this.notice_data = notice_data;
    }

    public static class NoticeDataBean {
        /**
         * phone : 17756094740
         * google :
         * email : ccs7727@163.com
         */

        private String phone;
        private String google;
        private String email;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGoogle() {
            return google;
        }

        public void setGoogle(String google) {
            this.google = google;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
