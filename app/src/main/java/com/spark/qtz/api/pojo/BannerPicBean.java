package com.spark.qtz.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/14
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BannerPicBean implements Parcelable {

    private List<AppCarouselBean> appCarousel;

    protected BannerPicBean(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BannerPicBean> CREATOR = new Creator<BannerPicBean>() {
        @Override
        public BannerPicBean createFromParcel(Parcel in) {
            return new BannerPicBean(in);
        }

        @Override
        public BannerPicBean[] newArray(int size) {
            return new BannerPicBean[size];
        }
    };

    public List<AppCarouselBean> getAppCarousel() {
        return appCarousel;
    }

    public void setAppCarousel(List<AppCarouselBean> appCarousel) {
        this.appCarousel = appCarousel;
    }

    public static class AppCarouselBean implements Parcelable{
        /**
         * picture : [{"language":"zh","url":"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/pc.png"},{"language":"en","url":"https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/app.png"}]
         * link : index
         * index : 0
         * sort : 0
         */

        private String link;
        private int index;
        private int sort;
        private List<PictureBean> picture;

        protected AppCarouselBean(Parcel in) {
            link = in.readString();
            index = in.readInt();
            sort = in.readInt();
        }

        public static final Creator<AppCarouselBean> CREATOR = new Creator<AppCarouselBean>() {
            @Override
            public AppCarouselBean createFromParcel(Parcel in) {
                return new AppCarouselBean(in);
            }

            @Override
            public AppCarouselBean[] newArray(int size) {
                return new AppCarouselBean[size];
            }
        };

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public List<PictureBean> getPicture() {
            return picture;
        }

        public void setPicture(List<PictureBean> picture) {
            this.picture = picture;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(link);
            parcel.writeInt(index);
            parcel.writeInt(sort);
        }

        public static class PictureBean {
            /**
             * language : zh
             * url : https://bitrade.oss-cn-hongkong.aliyuncs.com/logo/bitrade/pc.png
             */

            private String language;
            private String url;

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
