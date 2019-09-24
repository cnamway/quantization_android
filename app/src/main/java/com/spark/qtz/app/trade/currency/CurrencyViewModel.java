package com.spark.qtz.app.trade.currency;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.XPopup;
import com.spark.acclient.CaptchaAcClient;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinAddressBean;
import com.spark.acclient.pojo.CoinExtractBean;
import com.spark.acclient.pojo.CoinExtractSubmitBean;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.acclient.pojo.CoinWithdrawAddressResult;
import com.spark.acclient.pojo.MerberWalletResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.popup.B2BBuyBottomPopup;
import com.spark.qtz.ui.popup.CoinChoosePopup;
import com.spark.qtz.ui.popup.CoinExtractPopup;
import com.spark.qtz.ui.popup.CoinExtractSubmitPopup;
import com.spark.qtz.ui.popup.CoinRechargePopup;
import com.spark.qtz.ui.popup.CoinTransPopup;
import com.spark.qtz.ui.popup.CoinWithdrawAddressPopup;
import com.spark.qtz.ui.popup.SmsVerifyPopup;
import com.spark.qtz.ui.popup.impl.OnCoinChooseListener;
import com.spark.qtz.ui.popup.impl.OnCoinExtractListener;
import com.spark.qtz.ui.popup.impl.OnCoinExtractSubmitListener;
import com.spark.qtz.ui.popup.impl.OnCoinRechargeListener;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.modulespot.B2BWsClient;
import com.spark.modulespot.QuoteClient;
import com.spark.modulespot.SpotCoinClient;
import com.spark.modulespot.pojo.AllThumbResult;
import com.spark.modulespot.pojo.MarketSymbolResult;
import com.spark.modulespot.pojo.MarketSymbolResult.DataBean.AskBean;
import com.spark.modulespot.pojo.MarketSymbolResult.DataBean.BidBean;
import com.spark.modulespot.pojo.PlaceOrderBean;
import com.spark.modulespot.pojo.QueryWithSymbolResult;
import com.spark.otcclient.AdvertiseScanClient;
import com.spark.otcclient.pojo.LcCoinListResult;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.pojo.B2BMarketBean;
import com.spark.wsclient.pojo.B2BThumbBean;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SPUtils;
import me.spark.mvvm.utils.SpanUtils;
import me.spark.mvvm.utils.StringUtils;
import me.spark.mvvm.utils.WebSocketRequest;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/24
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CurrencyViewModel extends BaseViewModel {
    public CurrencyViewModel(@NonNull Application application) {
        super(application);
    }

    private Context mContext;
    private OnRequestListener onRequestListener;
    //币对名称
    public ObservableField<String> symbolName = new ObservableField<>("");
    public ObservableField<CharSequence> symbolNameTV = new ObservableField<>();
    //交易类型  限价 - 市价
    public ObservableField<String> currentBuyType = new ObservableField<>(App.getInstance().getString(R.string.limit_price_buy));
    //当前价
    public ObservableField<CharSequence> closePrice = new ObservableField<>();
    public ObservableField<Boolean> closePriceType = new ObservableField<>(true);
    public ObservableField<String> priceConvert = new ObservableField<>("");
    //价格
    public ObservableField<String> priceEt = new ObservableField<>("");
    public ObservableField<String> priceCNY = new ObservableField<>("≈ --CNY");
    public ObservableField<String> priceTag = new ObservableField<>(App.getInstance().getString(R.string.price) + "(--)");
    //数量 - 可用数量
    public ObservableField<String> numberEt = new ObservableField<>("");
    public ObservableField<String> numberTag = new ObservableField<>(App.getInstance().getString(R.string.number) + "(--)");
    public ObservableField<String> numberAvailable = new ObservableField<>("");
    public ObservableField<String> seekBarText = new ObservableField<>("");
    //交易额
    public ObservableField<String> tradeValue = new ObservableField<>("");
    public ObservableField<Boolean> tradeValueVisiable = new ObservableField<>(true);
    // 0 - 买入  1 - 卖出
    public ObservableField<Integer> buyType = new ObservableField<>(0);
    public ObservableField<String> buyText = new ObservableField<>();
    public ObservableField<Boolean> typeVisiable = new ObservableField<>(false);
    // 0 - 市价  1 - 限价
    public ObservableField<Integer> priceType = new ObservableField<>(1);
    //是否收藏
    public ObservableField<Boolean> isFavor = new ObservableField<>(false);

    public AllThumbResult.DataBean allThumbResult = new AllThumbResult.DataBean();
    //当前币对信息
    private QueryWithSymbolResult queryWithSymbolResult;
    //精度
    private int symbolScale = 2;
    private String numberBuyAvailable, numberBuyName, numberSellAvailable, numberSellName;
    private boolean isLoadAllThumb = false;
    private double seekBarEnd;
    private String tradePrice;
    private double coin2Usd;                        //所选币种对美元的汇率

    private int getCoinSupportType = 0;             //0 - 充币  1 - 提币   2 - 划转
    private CoinSupportBean.DataBean mCoinSupportBean;
    private CoinExtractPopup mCoinExtractPopup;
    private CoinExtractBean mCoinExtractBean;
    private CoinTransPopup mCoinTransPopup;
    private CoinSupportBean mSpotTransSupportBean;
    public boolean isOnPause = true;
    private AllThumbResult.DataBean mAllThumbResultPolling;
    private String lastSymbol;
    private boolean isFirstHttpLoadSuccess = false;
    private List<AskBean> mMarketSellList = new ArrayList<>();
    private List<BidBean> mMarketBuyList = new ArrayList<>();
    private User mUser;
    public int type = 0; //0 - 手机注册 1 - 邮箱注册
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<AllThumbResult.DataBean> mDataBeanSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<MarketSymbolResult> mMarketSymbolResultSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> loginStatue = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> fromKlineStatue = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> orderStatue = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> mFilterSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<List<AskBean>> mMarketSellSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<List<BidBean>> mMarketBuySingleLiveEvent = new SingleLiveEvent<>();
    }

    public void initContext(Context context) {
        this.mContext = context;
        mUser = App.getInstance().getCurrentUser();
        if (mUser != null) {
            if (StringUtils.isNotEmpty(mUser.getMobilePhone())) {
                type = 0;
            } else {
                type = 1;
            }
        }
    }

    //查看K线图
    public BindingCommand klineClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_QUOTES_KLINE_HTTP)
                    .withParcelable("quotesThumbClick", allThumbResult)
                    .navigation();
        }
    });

    public BindingCommand priceSubtractClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            int scale = allThumbResult.getBaseCoinScale();
            if (StringUtils.isEmpty(priceEt.get()) || Double.valueOf(priceEt.get()) < 1 / Math.pow(10, scale) || null == allThumbResult) {
                return;
            }
            priceEt.set(new BigDecimal(priceEt.get()).subtract(new BigDecimal(1 / Math.pow(10, scale))).setScale(scale, BigDecimal.ROUND_HALF_UP).toString());
        }
    });

    public BindingCommand priceAddClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (StringUtils.isEmpty(priceEt.get())) {
                return;
            }
            int scale = allThumbResult.getBaseCoinScale();
            priceEt.set(new BigDecimal(priceEt.get()).add(new BigDecimal(1 / Math.pow(10, scale))).setScale(scale, BigDecimal.ROUND_HALF_UP).toString());
        }
    });

    public BindingCommand numberSubtractClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            int scale = allThumbResult.getBaseCoinScale();
            if (StringUtils.isEmpty(numberEt.get()) || Double.valueOf(numberEt.get()) < 1 / Math.pow(10, scale) || null == allThumbResult) {
                return;
            }
            numberEt.set(new BigDecimal(numberEt.get()).subtract(new BigDecimal(1 / Math.pow(10, scale))).setScale(scale, BigDecimal.ROUND_HALF_UP).toString());
        }
    });

    public BindingCommand numberAddClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (StringUtils.isEmpty(numberEt.get())) {
                return;
            }
            int scale = allThumbResult.getBaseCoinScale();
            numberEt.set(new BigDecimal(numberEt.get()).add(new BigDecimal(1 / Math.pow(10, scale))).setScale(scale, BigDecimal.ROUND_HALF_UP).toString());
        }
    });

    public BindingCommand openOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!App.getInstance().isAppLogin()) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
                return;
            }
            if (StringUtils.isEmpty(symbolName.get())) return;
            ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_CC_OPEN_ORDERS)
                    .withString("symbol", symbolName.get())
                    .navigation();
        }
    });


    //提交
    public BindingCommand currentBuyCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!App.getInstance().isAppLogin()) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
                return;
            }
            if (!StringUtils.isNotEmpty(priceEt.get()) || Double.parseDouble(priceEt.get()) <= 0) {
                Toasty.showError(App.getInstance().getString(R.string.price_bigger_0));
                return;
            }

            if (!StringUtils.isNotEmpty(numberEt.get()) || Double.parseDouble(numberEt.get()) <= 0) {
                Toasty.showError(App.getInstance().getString(R.string.commission_num_bigger_0));
                return;
            }

            PlaceOrderBean placeOrderBean = new PlaceOrderBean();
            placeOrderBean.setOrderQty(numberEt.get());
            placeOrderBean.setPrice(priceType.get() == 0 ? closePrice.get().toString() : priceEt.get());
            placeOrderBean.setPriceType(priceType.get());
            placeOrderBean.setSide(buyType.get());
            placeOrderBean.setSymbol(symbolNameTV.get().toString().replace(" ", ""));

            new XPopup.Builder(mContext)
                    .asCustom(new B2BBuyBottomPopup(mContext,
                            buyType.get(),
                            tradePrice,
                            placeOrderBean))
                    .show();
        }
    });

    public void findOpenOrders(int pageIndex, OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
        SpotCoinClient.getInstance().findOpenOrders(pageIndex, "", symbolName.get());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        if ((!isVisible2User() || isOnPause)
                && (!eventBean.getOrigin().equals(EvKey.loginStatue)
                && !eventBean.getOrigin().equals(EvKey.fromKline)
                && !eventBean.getOrigin().equals(EvKey.queryWithSymbol)))
            return;
        switch (eventBean.getOrigin()) {
            //成交全币种缩略图
            case EvKey.klineThumbAll:
                synchronized (this) {
                    if (eventBean.isStatue()) {
                        if (!Constant.isHttpAndWs) {
                            if (!isLoadAllThumb) {
                                Constant.allThumbSymbol = new String[((AllThumbResult) eventBean.getObject()).getData().size()];
                                for (int i = 0; i < ((AllThumbResult) eventBean.getObject()).getData().size(); i++) {
                                    Constant.allThumbSymbol[i] = ((AllThumbResult) eventBean.getObject()).getData().get(i).getSymbol();
                                }
                                isLoadAllThumb = true;
                            }
                        } else {
                            if (!TextUtils.isEmpty(Constant.b2bKlinePushJson)) {
                                Constant.b2bKlinePushJson = App.gson.toJson(eventBean.getObject());
                            }
                        }
                        initWsData((AllThumbResult) eventBean.getObject());

//                        if (!isLoadAllThumb) {
//                            Constant.allThumbSymbol = new String[((AllThumbResult) eventBean.getObject()).getData().size()];
//                            for (int i = 0; i < ((AllThumbResult) eventBean.getObject()).getData().size(); i++) {
//                                Constant.allThumbSymbol[i] = ((AllThumbResult) eventBean.getObject()).getData().get(i).getSymbol();
//                            }
//                            isLoadAllThumb = true;
//                        }
//                        for (AllThumbResult.DataBean dataBean : ((AllThumbResult) eventBean.getObject()).getData()) {
//                            if (Constant.isCcFirstLoad && !StringUtils.isEmpty(Constant.ccFirstLoadSymbol)) {
//                                //第一次未加载从Kline页面进入
//                                if (dataBean.getSymbol().equals(Constant.ccFirstLoadSymbol)) {
//                                    Constant.isCcFirstLoad = false;
//                                    updateLoadView(dataBean);
//                                    break;
//                                }
//                            } else if (StringUtils.isEmpty(symbolName.get())) {
//                                Constant.isCcFirstLoad = false;
//                                updateLoadView(dataBean);
//                                break;
//                            } else {
//                                if (dataBean.getSymbol().equals(symbolName.get())) {
//                                    mAllThumbResultPolling = dataBean;
//                                    closePrice.set(initClose(dataBean));
//                                    priceConvert.set(initConvert(dataBean));
//                                    if (priceType.get() == 0 && !StringUtils.isEmpty(numberEt.get())) {
//                                        tradePrice = MathUtils.getRundNumber(Double.valueOf(numberEt.get().trim()) * Double.valueOf(dataBean.getClose()), symbolScale, null);
//                                        tradeValue.set(mContext.getString(R.string.turnover) + tradePrice + dataBean.getSymbol().split("/")[1]);
//                                    }
//                                    break;
//                                }
//                            }
//                        }
                    }
                }
                break;
            //获取指定币种指定深度盘口
            case EvKey.marketSymbol:
                synchronized (this) {
                    if (eventBean.isStatue()
                            && eventBean.getMessage().equals(allThumbResult.getSymbol())) {
                        uc.mMarketSymbolResultSingleLiveEvent.setValue((MarketSymbolResult) eventBean.getObject());
                    }
                }
                break;
            //Drawer点击事件
            case EvKey.drawerClick:
                if (eventBean.isStatue()) {
                    updateLoadView((AllThumbResult.DataBean) eventBean.getObject());
                }
                break;
            //登录状态监听
            case EvKey.loginStatue:
                if (!App.getInstance().isAppLogin()) {
                    if (allThumbResult != null) {
                        numberAvailable.set(mContext.getString(R.string.available) + "--" + allThumbResult.getSymbol().split("/")[1]);
                        seekBarText.set("--" + allThumbResult.getSymbol().split("/")[0]);
                    }
                }
                initBuyText();
                initQueryWithSymbol();
                uc.loginStatue.setValue(App.getInstance().isAppLogin());
                break;
            //用户下单
            case EvKey.placeOrder:
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(mContext.getString(R.string.submit_success));
                    initQueryWithSymbol();
                    uc.orderStatue.setValue(true);
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //用户撤单
            case EvKey.cancelOrder:
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(mContext.getString(R.string.successful_withdrawal_of_application));
                    initQueryWithSymbol();
                    uc.orderStatue.setValue(true);
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            case EvKey.queryWithSymbol:
                if (eventBean.isStatue()) {
                    queryWithSymbolResult = (QueryWithSymbolResult) eventBean.getObject();
                    updateNumberAvailable();
                }
                break;
            //当前委托
            case EvKey.openOrders:
                if (eventBean.isStatue()) {
                    onRequestListener.onSuccess(eventBean.getObject());
                } else {
                    onRequestListener.onFail(eventBean.getMessage());
                }
                break;

            //查询平台支持到币种信息
            case EvKey.coinSupport:
                dismissDialog();
                if (eventBean.isStatue()) {
                    final CoinSupportBean coinSupportBean = (CoinSupportBean) eventBean.getObject();
                    if (getCoinSupportType == 0 || getCoinSupportType == 1) {
                        new XPopup.Builder(mContext)
                                .asCustom(new CoinChoosePopup(mContext, coinSupportBean.getData(), new OnCoinChooseListener() {
                                    @Override
                                    public void onClickOrder() {
                                        //记录
                                        switch (getCoinSupportType) {
                                            case 0:
                                                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_RECORD)
                                                        .withInt("recordType", 0)
                                                        .navigation();
                                                break;
                                            case 1:
                                                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_RECORD)
                                                        .withInt("recordType", 1)
                                                        .navigation();
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onClickItem(int position) {
                                        mCoinSupportBean = coinSupportBean.getData().get(position);
                                        //item点击
                                        switch (getCoinSupportType) {
                                            case 0:
                                                getCoinInInfo(coinSupportBean.getData().get(position).getCoinName());
                                                break;
                                            case 1:
                                                getCoinOutInfo(coinSupportBean.getData().get(position).getCoinName());
                                                break;
                                        }
                                    }
                                }))
                                .show();
                    } else {
                        if (coinSupportBean.getData().isEmpty()) {
                            Toasty.showError(App.getInstance().getString(R.string.no_support_coin));
                            return;
                        }
                        mSpotTransSupportBean = coinSupportBean;
                        if (!Constant.lcCoinPairThumbBeanList.isEmpty()) {
                            mCoinTransPopup = new CoinTransPopup(mContext, 0, coinSupportBean);
                            new XPopup.Builder(mContext)
                                    .asCustom(mCoinTransPopup)
                                    .show();
                        } else {
                            AdvertiseScanClient.getInstance().getIndexTradeCoinList();
                        }
                    }
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //查询法币可用币种
            case EvKey.indexCoinList:
                if (eventBean.isStatue()) {
                    Constant.lcCoinPairThumbBeanList.clear();
                    for (LcCoinListResult.DataBean dataBean : ((LcCoinListResult) eventBean.getObject()).getData()) {
                        if (!Constant.lcCoinPairThumbBeanList.contains(dataBean.getCoinName()))
                            Constant.lcCoinPairThumbBeanList.add(dataBean.getCoinName());
                    }
                    mCoinTransPopup = new CoinTransPopup(mContext, 0, mSpotTransSupportBean);
                    new XPopup.Builder(mContext)
                            .asCustom(mCoinTransPopup)
                            .show();
                } else {
                    Toasty.showError(App.getInstance().getString(R.string.network_abnormal));
                }
                break;
            //查询指定币种的钱包地址
            case EvKey.coinAddress:
                dismissDialog();
                if (eventBean.isStatue()) {
                    CoinAddressBean coinAddressBean = (CoinAddressBean) eventBean.getObject();
                    new XPopup.Builder(mContext)
                            .asCustom(new CoinRechargePopup(mContext, coinAddressBean, mCoinSupportBean, new OnCoinRechargeListener() {
                                @Override
                                public void onClickOrder() {
                                    //记录
                                    // TODO: 2019/5/21
                                }

                                @Override
                                public void onClickChoose() {
                                    //选择币种
                                    getCoinSupport();
                                }
                            }))
                            .show();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //币币选定币种提币信息
            case EvKey.merberWallet:
                dismissDialog();
                if (eventBean.isStatue()) {
                    MerberWalletResult merberWalletResult = (MerberWalletResult) eventBean.getObject();
                    mCoinExtractPopup = new CoinExtractPopup(mContext, merberWalletResult, mCoinSupportBean, new OnCoinExtractListener() {
                        @Override
                        public void onReceiveCoinExtract(CoinExtractSubmitBean coinExtractSubmitBean) {
                            new XPopup.Builder(mContext)
                                    .asCustom(new CoinExtractSubmitPopup(mContext, coinExtractSubmitBean, new OnCoinExtractSubmitListener() {
                                        @Override
                                        public void onReceiveCoinExtractSubmit(final CoinExtractBean coinExtractBean) {
                                            mCoinExtractBean = coinExtractBean;
                                            showDialog(mContext.getString(R.string.loading));
                                            CaptchaAcClient.getInstance().sendVertifyCode(type);
                                        }
                                    }))
                                    .show();
                        }
                    });
                    new XPopup.Builder(mContext)
                            .asCustom(mCoinExtractPopup)
                            .show();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //法币选定币种提币信息
            case EvKey.merberOtcWallet:
                dismissDialog();
                if (eventBean.isStatue()) {
                    MerberWalletResult merberWalletResult = (MerberWalletResult) eventBean.getObject();
                    mCoinTransPopup.updateTransNumAvailable(merberWalletResult);
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //获取短信验证码
            case EvKey.acPhoneCaptcha:
                dismissDialog();
                if (eventBean.isStatue()) {
                    new XPopup.Builder(mContext)
                            .asCustom(new SmsVerifyPopup(mContext, App.getInstance().getCurrentUser().getMobilePhone(), new OnEtContentListener() {
                                @Override
                                public void onCEtContentInput(String content) {
                                    showDialog(mContext.getString(R.string.loading));
                                    FinanceClient.getInstance().withdrawRequest(content, mCoinExtractBean);
                                }
                            })).show();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //查询用户的某个币种的提币地址信息
            case EvKey.coinWithdrawAddress:
                dismissDialog();
                if (eventBean.isStatue()) {
                    CoinWithdrawAddressResult coinWithdrawAddressResult = (CoinWithdrawAddressResult) eventBean.getObject();
                    new XPopup.Builder(mContext)
                            .asCustom(new CoinWithdrawAddressPopup(mContext, coinWithdrawAddressResult.getData(), new OnEtContentListener() {
                                @Override
                                public void onCEtContentInput(String content) {
                                    mCoinExtractPopup.updateAddress(content);
                                }
                            }))
                            .show();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //提币
            case EvKey.coinWithdrawRequest:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(mContext.getString(R.string.mention_success));
                    initQueryWithSymbol();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //划转
            case EvKey.coinTransfer:
                if (eventBean.isStatue()) {
                    if (mCoinTransPopup != null && mCoinTransPopup.isShow()) {
                        mCoinTransPopup.dismiss();
                    }
                    Toasty.showSuccess(mContext.getString(R.string.coin_trans_success));
                    initQueryWithSymbol();
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //添加收藏
            case EvKey.favorAdd:
                dismissDialog();
                if (eventBean.isStatue()) {
                    List<String> tempList = SPUtils.getInstance().getFavorFindList();
                    if (!tempList.contains(allThumbResult.getSymbol())) {
                        tempList.add(allThumbResult.getSymbol());
                    }
                    SPUtils.getInstance().setFavorFindList(tempList);
                    isFavor.set(true);
                    Toasty.showSuccess(mContext.getString(R.string.add_collection_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //取消收藏
            case EvKey.favorDelete:
                dismissDialog();
                if (eventBean.isStatue()) {
                    List<String> tempList = SPUtils.getInstance().getFavorFindList();
                    if (tempList.contains(allThumbResult.getSymbol())) {
                        tempList.remove(allThumbResult.getSymbol());
                    }
                    SPUtils.getInstance().setFavorFindList(tempList);
                    isFavor.set(false);
                    Toasty.showSuccess(mContext.getString(R.string.cancel_collection_success));
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //K线页面发送数据 0 - 买入 1 - 卖出
            case EvKey.fromKline:
                uc.fromKlineStatue.setValue(eventBean.getCode());
                updateLoadView((AllThumbResult.DataBean) eventBean.getObject());
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //缩略图推送
            case WsCMD.PUSH_THUMB:
                Constant.lastB2BKlinePushTime = System.currentTimeMillis();
                if (!isFirstHttpLoadSuccess) return;
                try {
                    String json = "{\"date\":" + webSocketResponse.getResponse() + "}";
                    B2BThumbBean b2BThumbBean = App.gson.fromJson(json, B2BThumbBean.class);
                    for (B2BThumbBean.DateBean dateBean : b2BThumbBean.getDate()) {
                        if (dateBean.getSymbol().equals(symbolName.get())) {
                            allThumbResult.setVolume(dateBean.getVolume());
                            allThumbResult.setHigh(dateBean.getHigh());
                            allThumbResult.setChg(dateBean.getChg());
                            allThumbResult.setCnyLegalAsset(dateBean.getCnyLegalAsset());
                            allThumbResult.setLow(dateBean.getLow());
                            allThumbResult.setClose(dateBean.getClose());
                            allThumbResult.setTurnover(dateBean.getTurnover());
                            mAllThumbResultPolling = allThumbResult;
                            closePrice.set(initClose(allThumbResult));
                            priceConvert.set(initConvert(allThumbResult));
                            if (priceType.get() == 0 && !StringUtils.isEmpty(numberEt.get())) {
                                tradePrice = MathUtils.getRundNumber(Double.valueOf(numberEt.get().trim()) * Double.valueOf(allThumbResult.getClose()), symbolScale, null);
                                tradeValue.set(mContext.getString(R.string.turnover) + tradePrice + allThumbResult.getSymbol().split("/")[1]);
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    LogUtils.e("PUSH_THUMB", e.toString());
                }
                break;
            //盘口推送
            case WsCMD.PUSH_TRADE_PLATE:
                try {
                    Constant.lastB2BMarketPushTime = System.currentTimeMillis();
                    B2BMarketBean b2BMarketBean = App.gson.fromJson(webSocketResponse.getResponse(), B2BMarketBean.class);
                    if (!b2BMarketBean.getSymbol().contains(symbolName.get())) return;

                    if (b2BMarketBean.getDirection() == 0) {//买
                        mMarketBuyList.clear();
                        for (B2BMarketBean.ItemsBean item : b2BMarketBean.getItems()) {
                            BidBean bidBean = new BidBean();
                            bidBean.setAmount(item.getAmount());
                            bidBean.setPrice(item.getPrice());
                            mMarketBuyList.add(bidBean);
                        }
                        uc.mMarketBuySingleLiveEvent.setValue(mMarketBuyList);
                    } else if (b2BMarketBean.getDirection() == 1) {//卖
                        mMarketSellList.clear();
                        for (B2BMarketBean.ItemsBean item : b2BMarketBean.getItems()) {
                            AskBean askBean = new AskBean();
                            askBean.setAmount(item.getAmount());
                            askBean.setPrice(item.getPrice());
                            mMarketSellList.add(askBean);
                        }
                        uc.mMarketSellSingleLiveEvent.setValue(mMarketSellList);
                    }
                } catch (Exception e) {
                    LogUtils.e("PUSH_THUMB", e.toString());
                }
                break;
            default:
                break;
        }
    }

    public void initWsData(AllThumbResult allThumbResult) {
        for (AllThumbResult.DataBean dataBean : allThumbResult.getData()) {
            if (Constant.isCcFirstLoad && !StringUtils.isEmpty(Constant.ccFirstLoadSymbol)) {
                //第一次未加载从Kline页面进入
                if (dataBean.getSymbol().equals(Constant.ccFirstLoadSymbol)) {
                    Constant.isCcFirstLoad = false;
                    updateLoadView(dataBean);
                    break;
                }
            } else if (StringUtils.isEmpty(symbolName.get())) {
                Constant.isCcFirstLoad = false;
                updateLoadView(dataBean);
                break;
            } else {
                if (dataBean.getSymbol().equals(symbolName.get())) {
                    mAllThumbResultPolling = dataBean;
                    closePrice.set(initClose(dataBean));
                    priceConvert.set(initConvert(dataBean));
                    if (priceType.get() == 0 && !StringUtils.isEmpty(numberEt.get())) {
                        tradePrice = MathUtils.getRundNumber(Double.valueOf(numberEt.get().trim()) * Double.valueOf(dataBean.getClose()), symbolScale, null);
                        tradeValue.set(mContext.getString(R.string.turnover) + tradePrice + dataBean.getSymbol().split("/")[1]);
                    }
                    break;
                }
            }
        }
    }

    private void updateLoadView(AllThumbResult.DataBean dataBean) {
        allThumbResult = dataBean;
        coin2Usd = allThumbResult.getCnyLegalAsset() / allThumbResult.getUsdLegalAsset();

        Constant.currencySymbolRate = allThumbResult.getBaseCoinScreenScale();
        Constant.currencyPriceRate = allThumbResult.getBaseCoinScreenScale();
        Constant.currencyNumberRate = allThumbResult.getCoinScale();
        if (App.getInstance().isAppLogin()) {
            isFavor.set(SPUtils.getInstance().getFavorFindList().contains(allThumbResult.getSymbol()));
            SpotCoinClient.getInstance().queryWithSymbol(dataBean.getSymbol());
        }
        priceTag.set(mContext.getString(R.string.price) + "(" + dataBean.getSymbol().split("/")[1] + ")");
        numberTag.set(mContext.getString(R.string.number) + "(" + dataBean.getSymbol().split("/")[0] + ")");

        symbolScale = dataBean.getBaseCoinScreenScale();
        symbolName.set(dataBean.getSymbol());
        symbolNameTV.set(initSymbol(dataBean.getSymbol()));

        priceCNY.set("≈ " + MathUtils.getRundNumber(dataBean.getCnyLegalAsset(), symbolScale, null) + Constant.CNY);
        priceConvert.set("≈ " + MathUtils.getRundNumber(dataBean.getCnyLegalAsset(), symbolScale, null) + Constant.CNY);

        if (!App.getInstance().isAppLogin()) {
            numberAvailable.set(mContext.getString(R.string.available) + "--" + allThumbResult.getSymbol().split("/")[1]);
            seekBarText.set("--" + allThumbResult.getSymbol().split("/")[0]);
        }

        priceEt.set(MathUtils.getRundNumber(dataBean.getClose(), symbolScale, null));
        closePrice.set(initClose(dataBean));
        closePriceType.set(dataBean.getChg() >= 0);
        priceConvert.set(initConvert(dataBean));
        tradeValue.set(mContext.getString(R.string.turnover) + "0.0000" + dataBean.getSymbol().split("/")[1]);
        Constant.currentSymbol = dataBean.getSymbol();

        uc.mDataBeanSingleLiveEvent.setValue(dataBean);
        if (Constant.isHttpAndWs) {
            //打开盘口轮询 Ws + Http
            if (!TextUtils.isEmpty(lastSymbol)) {
                unsubscribeB2BTradePlate();
            }
            lastSymbol = symbolName.get();
            subscribeB2BTradePlate();
            B2BWsClient.getInstance().monitorB2BMarketPush(symbolName.get());
        } else {
            //打开盘口轮询 Http
            QuoteClient.getInstance().getMarketSymbolPolling(dataBean.getSymbol());
        }
        isFirstHttpLoadSuccess = true;
    }

    public void updateTradeValue(float progressFloat) {
        if (!App.getInstance().isAppLogin()) return;
        numberEt.set(DfUtils.numberFormatDown(seekBarEnd * progressFloat / 100, symbolScale));
    }

    //可用 监听
    public void updateNumberAvailable() {
        //清空数量
        numberEt.set("");
        if (priceType.get() == 0 && buyType.get() == 0) {
            uc.mFilterSingleLiveEvent.setValue(allThumbResult.getBaseCoinScale());
        } else {
            uc.mFilterSingleLiveEvent.setValue(allThumbResult.getCoinScreenScale());
        }
        tradeValue.set(mContext.getString(R.string.turnover) + "0.0000" + allThumbResult.getSymbol().split("/")[1]);
        tradeValueVisiable.set(!currentBuyType.get().contains(mContext.getString(R.string.market_price)));

        if (!App.getInstance().isAppLogin()) {
            numberAvailable.set(mContext.getString(R.string.available) + "--" + allThumbResult.getSymbol().split("/")[1]);
            seekBarText.set("--" + allThumbResult.getSymbol().split("/")[0]);
        } else {
            switch (buyType.get()) {
                //买入
                case 0:
                    if (null == queryWithSymbolResult) return;
                    for (QueryWithSymbolResult.DataBean dataBean : queryWithSymbolResult.getData()) {
                        if (symbolName.get().endsWith(dataBean.getCoinId())) {
                            numberBuyAvailable = MathUtils.getRundNumber(dataBean.getBalance(), 6, null);
                            numberBuyName = dataBean.getCoinId();
                            numberAvailable.set(mContext.getString(R.string.available) + numberBuyAvailable + numberBuyName);
                            if (priceType.get() != 0) {
                                seekBarEnd = Double.valueOf(priceEt.get()) == 0 ? 0 : dataBean.getBalance() / Double.valueOf(priceEt.get());
                                seekBarText.set(MathUtils.getRundNumber(seekBarEnd, 6, null) + allThumbResult.getSymbol().split("/")[0]);
                            } else {
                                seekBarEnd = dataBean.getBalance();
                                seekBarText.set(MathUtils.getRundNumber(seekBarEnd, 6, null) + allThumbResult.getSymbol().split("/")[1]);
                            }
                        }
                    }
                    break;
                //卖出
                case 1:
                    if (null == queryWithSymbolResult) return;
                    for (QueryWithSymbolResult.DataBean dataBean : queryWithSymbolResult.getData()) {
                        if (symbolName.get().startsWith(dataBean.getCoinId())) {
                            numberSellAvailable = MathUtils.getRundNumber(dataBean.getBalance(), 6, null);
                            numberSellName = dataBean.getCoinId();
                            numberAvailable.set(mContext.getString(R.string.available) + numberSellAvailable + numberSellName);
                            seekBarEnd = dataBean.getBalance();
                            seekBarText.set(MathUtils.getRundNumber(seekBarEnd, 6, null) + allThumbResult.getSymbol().split("/")[0]);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 价格监听
     *
     * @param price
     */
    public void updatePriceEt(String price) {
        priceEt.set(price);
        priceCNY.set("≈ "
                + MathUtils.getRundNumber(Double.parseDouble(price) * coin2Usd, 2, null)
                + "CNY");
        if (!StringUtils.isEmpty(numberEt.get())) {
            tradePrice = MathUtils.getRundNumber(Double.valueOf(price.trim()) * Double.valueOf(numberEt.get().trim()), symbolScale, null);
            tradeValue.set(mContext.getString(R.string.turnover) + tradePrice + allThumbResult.getSymbol().split("/")[1]);
        }
    }

    /**
     * 数量 - 交易额 监听
     *
     * @param s
     */
    public void updateNumberEt(Editable s) {
        if (!StringUtils.isEmpty(s.toString())) {
            if (priceType.get() == 0 && null != mAllThumbResultPolling) {
                tradePrice = MathUtils.getRundNumber(Double.valueOf(s.toString().trim()) * Double.valueOf(mAllThumbResultPolling.getClose()), symbolScale, null);
            } else {
                tradePrice = MathUtils.getRundNumber(Double.valueOf(s.toString().trim()) * Double.valueOf(priceEt.get()), symbolScale, null);
            }
            tradeValue.set(mContext.getString(R.string.turnover) + tradePrice + allThumbResult.getSymbol().split("/")[1]);
        } else {
            tradeValue.set(mContext.getString(R.string.turnover) + "0.0000" + allThumbResult.getSymbol().split("/")[1]);
        }

    }


    //币种
    public CharSequence initSymbol(String symbol) {
        CharSequence text = new SpanUtils()
                .append(symbol.split("/")[0]).setBold()
                .append(" / " + symbol.split("/")[1])
                .setFontSize(12, true)
                .create();
        return text;
    }

    //现价
    public CharSequence initClose(AllThumbResult.DataBean dataBean) {
        String close = DfUtils.numberFormat(dataBean.getClose(), dataBean.getBaseCoinScreenScale());
        if (!close.contains(".")) return close;
        CharSequence text = new SpanUtils()
                .append(close.split("\\.")[0])
                .append("." + close.split("\\.")[1])
                .setFontSize(12, true)
                .create();
        return text;
    }

    public String initConvert(AllThumbResult.DataBean dataBean) {
        return "≈" + MathUtils.getRundNumber(dataBean.getCnyLegalAsset(), 2, null)
                + "CNY";
    }

    public void initQueryWithSymbol() {
        if (App.getInstance().isAppLogin()) {
            SpotCoinClient.getInstance().queryWithSymbol(allThumbResult.getSymbol());
        }
    }

    //买入 - 卖出 交易按钮监听
    public void initBuyText() {
        String text = "";
        switch (buyType.get()) {
            //买入
            case 0:
                text = App.getInstance().isAppLogin() ? mContext.getString(R.string.buy) : mContext.getString(R.string.buy_after_login);
                break;
            //卖出
            case 1:
                text = App.getInstance().isAppLogin() ? mContext.getString(R.string.sell) : mContext.getString(R.string.sell_after_login);
                break;
        }
        buyText.set(text);
    }

    //菜单 点击事件
    public void onMenuclick(int type) {
        switch (type) {
            //充币
            case 0:
                getCoinSupportType = 0;
                getCoinSupport();
                break;
            //提币
            case 1:
                getCoinSupportType = 1;
                getCoinSupport();
                break;
            //划转
            case 2:
                getCoinSupportType = 2;
                getCoinSupport();
                break;
            //收藏
            case 3:
                if (!App.getInstance().isAppLogin()) {
                    ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                            .navigation();
                } else {
                    showDialog(mContext.getString(R.string.loading));
                    if (isFavor.get()) {
                        SpotCoinClient.getInstance().favorDelete(allThumbResult.getSymbol());
                    } else {
                        SpotCoinClient.getInstance().favorAdd(allThumbResult.getSymbol());
                    }
                }
                break;
        }
    }

    /**
     * 查询平台支持到币种信息
     */
    public void getCoinSupport() {
        showDialog(mContext.getString(R.string.loading));
        FinanceClient.getInstance().getCoinSupport();
    }

    /**
     * 充币详情
     *
     * @param coinAddress
     */
    public void getCoinInInfo(String coinAddress) {
        showDialog(mContext.getString(R.string.loading));
        FinanceClient.getInstance().getCoinAddress(coinAddress);
    }


    /**
     * 提币详情
     *
     * @param coinAddress
     */
    public void getCoinOutInfo(String coinAddress) {
        showDialog(mContext.getString(R.string.loading));
        FinanceClient.getInstance().getCoinOutInfo(coinAddress);
    }

    /**
     * 订阅盘口ws
     */
    private void subscribeB2BTradePlate() {
        Constant.lastB2BMarketPushSymbol = symbolName.get();
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_MARKET);
        mWebSocketRequest.setCmd(WsCMD.SUBSCRIBE_SPOT_TRADE_PLATE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setCFDMarketJsonMap(symbolName.get())).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    /**
     * 取消订阅
     */
    private void unsubscribeB2BTradePlate() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_MARKET);
        mWebSocketRequest.setCmd(WsCMD.UNSUBSCRIBE_SPOT_TRADE_PLATE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setCFDMarketJsonMap(lastSymbol)).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }

    private boolean isVisible2User() {
        return Constant.isTradeVisiable && Constant.tradePage == 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        isOnPause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isOnPause = true;
    }
}
