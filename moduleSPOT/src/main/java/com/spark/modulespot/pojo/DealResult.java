package com.spark.modulespot.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.spark.modulespot.R;

import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.DateUtils;
import me.spark.mvvm.utils.MathUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DealResult implements Parcelable {

    /**
     * code : 200
     * message : 操作成功!
     * data : [{"symbol":"BTC/USDT","side":0,"amount":1.0323273303499E-4,"buyOrderId":"","price":9686.85,"completedTime":1561102287826,"_id":{"timestamp":1561102287,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471259,"time":1561102287000,"date":1561102287000,"timeSecond":1561102287},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":1.0323273303499E-4,"buyOrderId":"","price":9686.85,"completedTime":1561102287754,"_id":{"timestamp":1561102287,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471258,"time":1561102287000,"date":1561102287000,"timeSecond":1561102287},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.0015,"buyOrderId":"","price":9686.32,"completedTime":1561102287245,"_id":{"timestamp":1561102287,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471248,"time":1561102287000,"date":1561102287000,"timeSecond":1561102287},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":1.0323273303499E-4,"buyOrderId":"","price":9686.85,"completedTime":1561102286764,"_id":{"timestamp":1561102286,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471237,"time":1561102286000,"date":1561102286000,"timeSecond":1561102286},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.1022,"buyOrderId":"","price":9686.85,"completedTime":1561102286484,"_id":{"timestamp":1561102286,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471231,"time":1561102286000,"date":1561102286000,"timeSecond":1561102286},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.2581,"buyOrderId":"","price":9686.85,"completedTime":1561102286084,"_id":{"timestamp":1561102286,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471221,"time":1561102286000,"date":1561102286000,"timeSecond":1561102286},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":1.0323273303499E-4,"buyOrderId":"","price":9686.85,"completedTime":1561102285580,"_id":{"timestamp":1561102285,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471215,"time":1561102285000,"date":1561102285000,"timeSecond":1561102285},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":1,"amount":0.0099,"buyOrderId":"","price":9685.79,"completedTime":1561102284869,"_id":{"timestamp":1561102285,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471205,"time":1561102285000,"date":1561102285000,"timeSecond":1561102285},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":1.0323273303499E-4,"buyOrderId":"","price":9686.85,"completedTime":1561102284625,"_id":{"timestamp":1561102284,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471196,"time":1561102284000,"date":1561102284000,"timeSecond":1561102284},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.0462,"buyOrderId":"","price":9686.85,"completedTime":1561102284339,"_id":{"timestamp":1561102284,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471190,"time":1561102284000,"date":1561102284000,"timeSecond":1561102284},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.3955,"buyOrderId":"","price":9686.85,"completedTime":1561102284013,"_id":{"timestamp":1561102284,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471181,"time":1561102284000,"date":1561102284000,"timeSecond":1561102284},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.1962,"buyOrderId":"","price":9686.85,"completedTime":1561102283973,"_id":{"timestamp":1561102284,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471178,"time":1561102284000,"date":1561102284000,"timeSecond":1561102284},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.0015,"buyOrderId":"","price":9686.84,"completedTime":1561102283973,"_id":{"timestamp":1561102284,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471177,"time":1561102284000,"date":1561102284000,"timeSecond":1561102284},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":1.03232839605072E-4,"buyOrderId":"","price":9686.84,"completedTime":1561102283350,"_id":{"timestamp":1561102283,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471168,"time":1561102283000,"date":1561102283000,"timeSecond":1561102283},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.0155,"buyOrderId":"","price":9686.85,"completedTime":1561102282827,"_id":{"timestamp":1561102282,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471155,"time":1561102282000,"date":1561102282000,"timeSecond":1561102282},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.0535,"buyOrderId":"","price":9686.85,"completedTime":1561102282790,"_id":{"timestamp":1561102282,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471154,"time":1561102282000,"date":1561102282000,"timeSecond":1561102282},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":1,"amount":0.3994,"buyOrderId":"","price":9685.36,"completedTime":1561102282767,"_id":{"timestamp":1561102282,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471152,"time":1561102282000,"date":1561102282000,"timeSecond":1561102282},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":0,"amount":0.1593,"buyOrderId":"","price":9686.85,"completedTime":1561102282664,"_id":{"timestamp":1561102282,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471151,"time":1561102282000,"date":1561102282000,"timeSecond":1561102282},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":1,"amount":0.0612,"buyOrderId":"","price":9685.36,"completedTime":1561102282662,"_id":{"timestamp":1561102282,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471150,"time":1561102282000,"date":1561102282000,"timeSecond":1561102282},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""},{"symbol":"BTC/USDT","side":1,"amount":1.0E-4,"buyOrderId":"","price":9685.36,"completedTime":1561102282563,"_id":{"timestamp":1561102282,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471149,"time":1561102282000,"date":1561102282000,"timeSecond":1561102282},"buyTurnover":0,"sellTurnover":0,"sellOrderId":""}]
     * count : null
     * responseString : 200~操作成功!
     */

    private int code;
    private String message;
    private Object count;
    private String responseString;
    private List<DataBean> data;


    protected DealResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
        responseString = in.readString();
    }

    public static final Creator<DealResult> CREATOR = new Creator<DealResult>() {
        @Override
        public DealResult createFromParcel(Parcel in) {
            return new DealResult(in);
        }

        @Override
        public DealResult[] newArray(int size) {
            return new DealResult[size];
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
    }


    public static class DataBean {
        /**
         * symbol : BTC/USDT
         * side : 0
         * amount : 1.0323273303499E-4
         * buyOrderId :
         * price : 9686.85
         * completedTime : 1561102287826
         * _id : {"timestamp":1561102287,"machineIdentifier":7009612,"processIdentifier":2240,"counter":16471259,"time":1561102287000,"date":1561102287000,"timeSecond":1561102287}
         * buyTurnover : 0
         * sellTurnover : 0
         * sellOrderId :
         */

        private String symbol;
        private int side;
        private double amount;
        private String buyOrderId;
        private double price;
        private long completedTime;
        private IdBean _id;
        private double buyTurnover;
        private double sellTurnover;
        private String sellOrderId;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public int getSide() {
            return side;
        }

        public void setSide(int side) {
            this.side = side;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getBuyOrderId() {
            return buyOrderId;
        }

        public void setBuyOrderId(String buyOrderId) {
            this.buyOrderId = buyOrderId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public long getCompletedTime() {
            return completedTime;
        }

        public void setCompletedTime(long completedTime) {
            this.completedTime = completedTime;
        }

        public IdBean get_id() {
            return _id;
        }

        public void set_id(IdBean _id) {
            this._id = _id;
        }

        public double getBuyTurnover() {
            return buyTurnover;
        }

        public void setBuyTurnover(double buyTurnover) {
            this.buyTurnover = buyTurnover;
        }

        public double getSellTurnover() {
            return sellTurnover;
        }

        public void setSellTurnover(double sellTurnover) {
            this.sellTurnover = sellTurnover;
        }

        public String getSellOrderId() {
            return sellOrderId;
        }

        public void setSellOrderId(String sellOrderId) {
            this.sellOrderId = sellOrderId;
        }

        public static class IdBean {
            /**
             * timestamp : 1561102287
             * machineIdentifier : 7009612
             * processIdentifier : 2240
             * counter : 16471259
             * time : 1561102287000
             * date : 1561102287000
             * timeSecond : 1561102287
             */

            private long timestamp;
            private long machineIdentifier;
            private long processIdentifier;
            private long counter;
            private long time;
            private long date;
            private long timeSecond;

            public long getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }

            public long getMachineIdentifier() {
                return machineIdentifier;
            }

            public void setMachineIdentifier(long machineIdentifier) {
                this.machineIdentifier = machineIdentifier;
            }

            public long getProcessIdentifier() {
                return processIdentifier;
            }

            public void setProcessIdentifier(long processIdentifier) {
                this.processIdentifier = processIdentifier;
            }

            public long getCounter() {
                return counter;
            }

            public void setCounter(long counter) {
                this.counter = counter;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public long getTimeSecond() {
                return timeSecond;
            }

            public void setTimeSecond(long timeSecond) {
                this.timeSecond = timeSecond;
            }
        }

        public boolean isBuy() {
            return side == 0;
        }


        public String formatTime() {
            return completedTime == 0 ? "--" : DateUtils.formatDate("HH:mm:ss", completedTime);
        }

        public String formatType() {
            return side == 0 ? BaseApplication.getInstance().getString(R.string.buy) : BaseApplication.getInstance().getString(R.string.sell);
        }

        public String formatPrice() {
            return price == 0 ? "--" : MathUtils.getRundNumber(price, Constant.currencySymbolRate, null);
        }

        public String formatNumber() {
            return amount == 0 ? "--" : MathUtils.getRundNumber(amount, Constant.currencySymbolRate, null);
        }
    }
}
