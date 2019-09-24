package com.spark.qtz.bean;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/13
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DepthHistoryBean {

    /**
     * code : 200
     * data : {"ask":[{"amount":0.93,"price":356.46},{"amount":0.91,"price":356.48},{"amount":84.74,"price":356.49},{"amount":32.43,"price":356.5},{"amount":40.12,"price":356.51},{"amount":24.29,"price":356.53},{"amount":24.29,"price":356.59},{"amount":56.23,"price":356.6},{"amount":16.01,"price":356.65},{"amount":6.79,"price":356.66},{"amount":5.75,"price":356.8},{"amount":51.92,"price":359.09},{"amount":62.97,"price":359.1},{"amount":183.32,"price":359.26},{"amount":453.96,"price":359.27},{"amount":366.54,"price":359.28},{"amount":61.27,"price":359.32},{"amount":124.66,"price":359.33},{"amount":183.32,"price":359.34},{"amount":119.36,"price":359.35},{"amount":100.81,"price":359.36},{"amount":15.28,"price":359.38},{"amount":30.57,"price":359.41},{"amount":183.32,"price":359.42},{"amount":113.31,"price":359.43},{"amount":306.38,"price":359.45},{"amount":18.44,"price":359.46},{"amount":12.15,"price":359.49},{"amount":139.69,"price":359.5},{"amount":100.88,"price":359.51},{"amount":733.08,"price":359.53},{"amount":366.54,"price":359.58},{"amount":85.06,"price":359.62},{"amount":100.72,"price":359.63},{"amount":12.15,"price":359.65},{"amount":14.97,"price":359.66},{"amount":102.11,"price":359.68},{"amount":130.69,"price":359.69},{"amount":204.22,"price":359.74},{"amount":265.73,"price":359.79},{"amount":100.67,"price":359.81},{"amount":12.83,"price":359.82},{"amount":89.81,"price":359.83},{"amount":100.7,"price":359.84},{"amount":12.15,"price":359.85},{"amount":366.54,"price":359.93},{"amount":467.16,"price":359.95},{"amount":144.29,"price":359.99},{"amount":125.11,"price":360.01},{"amount":269.5,"price":360.02},{"amount":204.19,"price":360.08},{"amount":146.66,"price":360.13},{"amount":24.45,"price":360.15},{"amount":12.15,"price":360.23},{"amount":12.15,"price":360.32},{"amount":12.15,"price":360.33},{"amount":146.66,"price":360.42},{"amount":24.45,"price":360.44},{"amount":146.77,"price":360.63},{"amount":12.15,"price":360.66},{"amount":24.41,"price":360.67},{"amount":12.15,"price":360.69},{"amount":17.75,"price":360.8},{"amount":140.98,"price":360.88},{"amount":445.83,"price":360.96},{"amount":56.77,"price":360.97},{"amount":176,"price":360.98},{"amount":145.26,"price":360.99},{"amount":535.33,"price":361},{"amount":14.82,"price":361.02},{"amount":12.15,"price":361.04},{"amount":12.15,"price":361.05},{"amount":343.54,"price":361.08},{"amount":142.3,"price":361.12},{"amount":14.82,"price":361.13},{"amount":22.85,"price":361.14},{"amount":244.68,"price":361.16},{"amount":176.97,"price":361.18},{"amount":42.94,"price":361.19},{"amount":214.71,"price":361.21},{"amount":214.71,"price":361.23},{"amount":128.83,"price":361.24},{"amount":127.53,"price":361.33},{"amount":535.33,"price":361.34},{"amount":165.88,"price":361.36},{"amount":269.82,"price":361.38},{"amount":1070.66,"price":361.4},{"amount":49.91,"price":361.41},{"amount":249.82,"price":361.43},{"amount":535.33,"price":361.45},{"amount":556.8,"price":361.49},{"amount":76.61,"price":361.5},{"amount":229.35,"price":361.51},{"amount":429.42,"price":361.53},{"amount":995.46,"price":361.54},{"amount":415.55,"price":361.6},{"amount":38.18,"price":361.63},{"amount":118.61,"price":361.64},{"amount":99.62,"price":361.65},{"amount":29.64,"price":361.67}],"bid":[{"amount":20.28,"price":356.41},{"amount":19.12,"price":356.4},{"amount":85.6,"price":356.38},{"amount":5.75,"price":356.32},{"amount":85.68,"price":356.29},{"amount":36.32,"price":356.28},{"amount":5.75,"price":356.27},{"amount":85.68,"price":356.22},{"amount":85.68,"price":356.2},{"amount":91.43,"price":356.19},{"amount":40.49,"price":356.1},{"amount":80.35,"price":356.09},{"amount":126.17,"price":356.03},{"amount":25.41,"price":355.99},{"amount":5.79,"price":355.94},{"amount":54.91,"price":355.67},{"amount":45.44,"price":355.65},{"amount":37.57,"price":354.49},{"amount":5.75,"price":354.43},{"amount":5.75,"price":354.38},{"amount":26.81,"price":354.35},{"amount":5.75,"price":354.24},{"amount":55.34,"price":354.21},{"amount":67.91,"price":354.12},{"amount":32.34,"price":354.1},{"amount":16.19,"price":353.69},{"amount":10.92,"price":353.67},{"amount":22.52,"price":353.36},{"amount":32.33,"price":353.34},{"amount":66.13,"price":353.26},{"amount":151.02,"price":351.2},{"amount":189.68,"price":351.11},{"amount":12.15,"price":351.08},{"amount":366.54,"price":351.06},{"amount":12.15,"price":351.02},{"amount":366.54,"price":351},{"amount":12.15,"price":350.98},{"amount":109.99,"price":350.96},{"amount":218.52,"price":350.9},{"amount":366.54,"price":350.88},{"amount":156.75,"price":350.85},{"amount":218.71,"price":350.81},{"amount":36.69,"price":350.8},{"amount":89.49,"price":350.75},{"amount":12.15,"price":350.73},{"amount":12.15,"price":350.7},{"amount":109.99,"price":350.66},{"amount":12.15,"price":350.64},{"amount":176.49,"price":350.63},{"amount":293.32,"price":350.6},{"amount":15.58,"price":350.57},{"amount":12.15,"price":350.53},{"amount":183.51,"price":350.5},{"amount":31.16,"price":350.46},{"amount":302.3,"price":350.45},{"amount":190.61,"price":350.44},{"amount":12.15,"price":350.37},{"amount":104.53,"price":350.36},{"amount":146.66,"price":350.33},{"amount":109.99,"price":350.3},{"amount":56.35,"price":350.27},{"amount":146.66,"price":350.26},{"amount":52.32,"price":350.23},{"amount":34.59,"price":350.22},{"amount":276,"price":350.18},{"amount":12.15,"price":350.16},{"amount":14.53,"price":350.13},{"amount":366.54,"price":350.1},{"amount":366.54,"price":350.07},{"amount":178.89,"price":350.03},{"amount":366.54,"price":350.01},{"amount":366.54,"price":350},{"amount":12.15,"price":349.97},{"amount":12.15,"price":349.94},{"amount":12.15,"price":349.91},{"amount":366.54,"price":349.89},{"amount":12.15,"price":349.85},{"amount":56.39,"price":349.84},{"amount":6.01,"price":3.6}]}
     * message : SUCCESS
     * responseString : 200~SUCCESS
     */

    private int code;
    private DataBean data;
    private String message;
    private String responseString;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public static class DataBean {
        private List<AskBean> ask;
        private List<BidBean> bid;

        public List<AskBean> getAsk() {
            return ask;
        }

        public void setAsk(List<AskBean> ask) {
            this.ask = ask;
        }

        public List<BidBean> getBid() {
            return bid;
        }

        public void setBid(List<BidBean> bid) {
            this.bid = bid;
        }

        public static class AskBean {
            /**
             * amount : 0.93
             * price : 356.46
             */

            private double amount;
            private double price;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }

        public static class BidBean {
            /**
             * amount : 20.28
             * price : 356.41
             */

            private double amount;
            private double price;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }
    }
}
