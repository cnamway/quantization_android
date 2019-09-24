package com.spark.wsclient.base;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/9
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class WsCMD {
    public static final short HEART_BEAT = 11004;                   // 心跳包
    public static final short JSONLOGIN = 11002;                    // 交易登录
    public static final short PUSH_REQUEST = 11003;                 // 获取连接状态

    // 首页币币推送模块
    public static final short GET_ALL_THUMB = 22003;                // 查询所有币种信息（行情首页界面显示）
    public static final short GET_OVERVIEW_THUMB = 22004;           // 获取首页缩略图和涨幅榜
    public static final short ENABLE_SYMBOL = 20009;                // 查询行情可用币种
    public static final short SUBSCRIBE_THUMB = 22011;              // 首页缩略图订阅与取消
    public static final short UN_SUBSCRIBE_THUMB = 22012;
    public static final short SUBSCRIBE_CFD_THUMB = 23013;          // 首页CFD缩略图订阅与取消
    public static final short UN_SUBSCRIBE_CFD_THUMB = 22014;
    public static final short PUSH_THUMB = 22101;                   // 首页缩略图接受
    public static final short PUSH_CFD_THUMB = 22107;               // 首页CFD缩略图接受

    // 币币收藏模块
    public static final short FIND_FAVOR = 20103;                   // 自选
    public static final short ADD_FAVOR = 20002;                    // 添加取消收藏
    public static final short DELETE_FAVOR = 20003;
    public static final short USD_CNY_RATE = 22008;                 // 查询USD-货币 费率

    // 盘口模块
    public static final short SUBSCRIBE_SPOT_TRADE_PLATE = 21000;   //订阅币币盘口
    public static final short UNSUBSCRIBE_SPOT_TRADE_PLATE = 21001; //取消订阅币币盘口
    public static final short GET_TRADE_PLATE = 21002;              //查询盘口信息
    public static final short SUBSCRIBE_CFD_TRADE_PLATE = 21003;    //订阅CFD盘口
    public static final short UNSUBSCRIBE_CFD_TRADE_PLATE = 21004;  //取消订阅CFD盘口
    public static final short SUBSCRIBE_SPOT_DEPTH_TRADE_PLATE = 21005;//订阅币币深度盘口
    public static final short UNSUBSCRIBE_SPOT_DEPTH_TRADE_PLATE = 21006;//取消订阅币币深度盘口
    public static final short PUSH_TRADE_PLATE = 21100;//推送币币盘口信息
    public static final short PUSH_DEPTH_TRADE_PLATE = 21101;//推送币币深度盘口信息
    public static final short PUSH_CFD_TRADE_PLATE = 21102;//推送CFD平台盘口信息
    // 币币交易模块
    public static final short ENTER_ORDER = 20000;                  // 交易下单
    public static final short PUSH_EXCHANGE_DEPTH = 20000;          // 交易下单
    public static final short CANCEL_ORDER = 20001;                 // 交易撤单
    public static final short HISTORY_ORDER = 20100;                // 交易模块历史成交
    public static final short CURRENT_ORDER = 20101;                // 交易模块当前委托
    public static final short PUSH_EXCHANGE_ORDER_COMPLETED = 20201;// 完成订单回报
    public static final short PUSH_EXCHANGE_ORDER_CANCELED = 20202; // 撤单回报
    public static final short PUSH_EXCHANGE_ORDER_TRADE = 20200;    // 成交回报
    public static final short ACCEPT_ORDER = 20204;                 // 下单成功回报
    public static final short ORDER_REJECT = 20206;                 // 下单请求拒绝
    public static final short RISK_FLAT_NOTIFY = 20207;             // 风控强平通知
    public static final short PUSH_TRADE = 22102;                   // 交易的接受指令 推送撮合成交记录
    public static final short ORDER_DETAIL = 20102;                 // 已成交委托详情

    // K线模块
    public static final short GET_KLINE_HISTORY = 22006;            // 获取K线的历史数据
    public static final short PUSH_KLINE = 22100;                   // K线接受实时推送的指令
    public static final short PUSH_KLINE_CFD = 22106;               // cfdK线接受实时推送的指令
    public static final short SUBSCRIBE_KLINE = 22001;              // 订阅和取消订阅K线
    public static final short UN_SUBSCRIBE_KLINE = 22002;           // 取消订阅K线
    public static final short SUBSCRIBE_KLINE_CFD = 23003;              // 订阅和取消订阅K线
    public static final short UN_SUBSCRIBE_KLINE_CFD = 23004;           // 取消订阅K线
    public static final short DEAL_PUSH = 22102;                    // 成交图推送
    public static final short SUBSCRIBE_DEAL = 22015;               // 订阅成交图
    public static final short UN_SUBSCRIBE_DEAL = 22016;            // 取消订阅成交图

    public static final short QUERY_WALLET = 20111;                 // 查看币币钱包

    // 聊天模块
    public static final short PUSH_CHAT = 20039;                    // 收到聊天消息
    public static final short SEND_CHAT = 20034;                    // 发送聊天

    public static final short SUBSCRIBE_GROUP_CHAT = 20035;         // 订阅聊天
    public static final short UNSUBSCRIBE_GROUP_CHAT = 20036;       // 取消订阅聊天

    //
    public static final short LATEST_TRADE = 22007;                 // 最近成交记录
    public static final short UN_Definition_Code = (short) 99992;
    public static final short RECIEVE_ADD_DAVOR = -1;
    public static final short RECIEVE_DEL_DAVOR = -2;               //风控强平回报通知

    //CFD
    public static final short CFD_POSITION_DETAIL = 20115;          //持仓明细
    public static final short CFD_CURRENT_ORDER = 20107;            //当前委托
    public static final short CFD_TURNOVER_DETAIL = 20103;          //已成交
    public static final short CFD_HISTORY_ORDER = 20106;            //已撤单

    public static final short CFD_ACCOUNT_ORDER = 20110;            //交易记录

}
