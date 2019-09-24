package com.spark.otcclient.pojo;

import java.util.List;

/**
 * Created by Administrator on 2019/8/9 0009.
 */

public class ChatListResult {

    /**
     * code : 200
     * message : SUCCESS
     * data : {"total":1,"size":20,"pages":1,"current":1,"records":[{"orderId":"215840774814220288","uidTo":"271","uidFrom":"271","nameTo":"五五六六","nameFrom":"ccs","messageType":"1","content":"111","sendTime":1565332671000,"partitionKey":201908}]}
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
         * total : 1
         * size : 20
         * pages : 1
         * current : 1
         * records : [{"orderId":"215840774814220288","uidTo":"271","uidFrom":"271","nameTo":"五五六六","nameFrom":"ccs","messageType":"1","content":"111","sendTime":1565332671000,"partitionKey":201908}]
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
             * orderId : 215840774814220288
             * uidTo : 271
             * uidFrom : 271
             * nameTo : 五五六六
             * nameFrom : ccs
             * messageType : 1
             * content : 111
             * sendTime : 1565332671000
             * partitionKey : 201908
             */

            private String orderId;
            private String uidTo;
            private String uidFrom;
            private String nameTo;
            private String nameFrom;
            private String messageType;
            private String content;
            private long sendTime;
            private int partitionKey;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getUidTo() {
                return uidTo;
            }

            public void setUidTo(String uidTo) {
                this.uidTo = uidTo;
            }

            public String getUidFrom() {
                return uidFrom;
            }

            public void setUidFrom(String uidFrom) {
                this.uidFrom = uidFrom;
            }

            public String getNameTo() {
                return nameTo;
            }

            public void setNameTo(String nameTo) {
                this.nameTo = nameTo;
            }

            public String getNameFrom() {
                return nameFrom;
            }

            public void setNameFrom(String nameFrom) {
                this.nameFrom = nameFrom;
            }

            public String getMessageType() {
                return messageType;
            }

            public void setMessageType(String messageType) {
                this.messageType = messageType;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getSendTime() {
                return sendTime;
            }

            public void setSendTime(long sendTime) {
                this.sendTime = sendTime;
            }

            public int getPartitionKey() {
                return partitionKey;
            }

            public void setPartitionKey(int partitionKey) {
                this.partitionKey = partitionKey;
            }
        }
    }
}
