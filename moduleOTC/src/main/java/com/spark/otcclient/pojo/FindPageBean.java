package com.spark.otcclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class FindPageBean implements Parcelable {

    /**
     * pageIndex : 0
     * pageSize : 0
     * queryList : [{"join":"string","key":"string","oper":"string","value":""}]
     * sortFields : string
     */

    private int pageIndex;                  //页码
    private int pageSize;                   //每页数量
    private String sortFields;              //排序字段，格式为:colume_d(倒叙)/colume_a(正序)
    private List<QueryListBean> queryList;  //查询条件

    public FindPageBean() {
    }

    protected FindPageBean(Parcel in) {
        pageIndex = in.readInt();
        pageSize = in.readInt();
        sortFields = in.readString();
    }

    public static final Creator<FindPageBean> CREATOR = new Creator<FindPageBean>() {
        @Override
        public FindPageBean createFromParcel(Parcel in) {
            return new FindPageBean(in);
        }

        @Override
        public FindPageBean[] newArray(int size) {
            return new FindPageBean[size];
        }
    };

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortFields() {
        return sortFields;
    }

    public void setSortFields(String sortFields) {
        this.sortFields = sortFields;
    }

    public List<QueryListBean> getQueryList() {
        return queryList;
    }

    public void setQueryList(List<QueryListBean> queryList) {
        this.queryList = queryList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pageIndex);
        dest.writeInt(pageSize);
        dest.writeString(sortFields);
    }

    public static class QueryListBean {
        /**
         * join : string
         * key : string
         * oper : string
         * value :
         */

        private String join;    //连接的方式：and或者or
        private String key;     //操作符的key，如查询时的name,id之类
        private String oper;    //操作符,默认是等于，冒号表示模糊匹配
        private String value;   //操作符的value，具体要查询的值，如果是字符串改成字符串即可

        public String getJoin() {
            return join;
        }

        public void setJoin(String join) {
            this.join = join;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getOper() {
            return oper;
        }

        public void setOper(String oper) {
            this.oper = oper;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
