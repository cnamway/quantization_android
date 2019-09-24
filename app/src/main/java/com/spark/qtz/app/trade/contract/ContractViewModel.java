package com.spark.qtz.app.trade.contract;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.modulecfd.CfdClient;
import com.example.modulecfd.CfdWsClient;
import com.example.modulecfd.pojo.CfdAllThumbResult;
import com.example.modulecfd.pojo.CfdPositionResult;
import com.example.modulecfd.pojo.CfdSymbolAllResult;
import com.example.modulecfd.pojo.CfdTradePlaceBean;
import com.lxj.xpopup.XPopup;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.acclient.pojo.MerberWalletResult;
import com.spark.acclient.pojo.SpotWalletResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.popup.CfdTradePopup;
import com.spark.qtz.ui.popup.CoinTransPopup;
import com.spark.qtz.ui.popup.impl.OnCfdTradePlaceListener;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.modulespot.pojo.MarketSymbolResult;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.pojo.CfdThumbBean;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.bus.event.SingleLiveEvent;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.EventBean;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.SpanUtils;
import me.spark.mvvm.utils.StringUtils;
import me.spark.mvvm.utils.WebSocketRequest;
import me.spark.mvvm.utils.WebSocketResponse;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ContractViewModel extends BaseViewModel {
    public ContractViewModel(@NonNull Application application) {
        super(application);
    }

    private boolean isLoadSuccess = false;

    public boolean isOnPause = true;
    public String[] allSymbol, allLever;
    public CfdSymbolAllResult cfdSymbolAllResult;
    public CfdSymbolAllResult.DataBean currentCfdSymbolAllResult;
    public CfdAllThumbResult mCfdAllThumbResult;
    public CfdAllThumbResult.DataBean currentSymbolBean;
    public CfdAllThumbResult.DataBean currentSymbolWsBean;
    public CfdAllThumbResult.DataBean currentSymbolBeanPolling;
    private OnRequestListener mOnRequestListener;
    private Context mContext;
    public double walletAvailable, walletOccupy, walletFreeze, rightsValue, riskRateValue, numberAvailableValue = 0, profitAndLoss;
    private int leverAmount = 1;
    private CfdPositionResult cfdPositionResult;
    private CoinTransPopup mCoinTransPopup;
    private List<MarketSymbolResult.DataBean.AskBean> askList = new ArrayList<>();
    private List<MarketSymbolResult.DataBean.BidBean> bidList = new ArrayList<>();
    public boolean isFirstHttpLoadSuccess = false;
    private boolean isCfdWalletRetry = false;
    //币对名称
    public ObservableField<String> symbolName = new ObservableField<>("");
    public ObservableField<CharSequence> symbolNameTV = new ObservableField<>();
    //交易类型  限价 - 市价
    public ObservableField<String> buyTypeName = new ObservableField<>(App.getInstance().getString(R.string.market_price));
    public ObservableField<Boolean> buyType = new ObservableField<>(true);
    //杠杆数
    public ObservableField<String> leverNum = new ObservableField<>("");
    //当前价
    public ObservableField<CharSequence> closePrice = new ObservableField<>();
    public ObservableField<Boolean> closePriceType = new ObservableField<>(true);
    public ObservableField<String> priceConvert = new ObservableField<>("");
    //价格
    public ObservableField<String> priceEt = new ObservableField<>("");
    public ObservableField<String> priceTag = new ObservableField<>(App.getInstance().getString(R.string.price) + "(--)");
    //数量 - 可用数量
    public ObservableField<String> numberEt = new ObservableField<>("1");
    public ObservableField<String> numberTag = new ObservableField<>(App.getInstance().getString(R.string.number) + "(--)");
    public ObservableField<String> numberAvailable = new ObservableField<>(App.getInstance().getString(R.string.can_open) + "--" + App.getInstance().getString(R.string.hands));
    public ObservableField<String> commissionAccount = new ObservableField<>(App.getInstance().getString(R.string.commission_margin) + "--");
    //资金
    public ObservableField<String> accountAvailable = new ObservableField<>("----");
    public ObservableField<String> accountOccupy = new ObservableField<>("----");
    public ObservableField<String> accountFreeze = new ObservableField<>("----");
    public ObservableField<String> rights = new ObservableField<>("----");
    public ObservableField<String> riskRate = new ObservableField<>("----");
    //是否登录
    public ObservableField<Boolean> loginStatue = new ObservableField<>(App.getInstance().isAppLogin());

    //Kline
    public BindingCommand goKline = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (null == currentSymbolBeanPolling) return;
            ARouter.getInstance().build(ARouterPath.ACTIVITY_TRADE_CFD_KLINE)
                    .withParcelable("cfdThumbClick", currentSymbolBeanPolling)
                    .navigation();
        }
    });

    public BindingCommand closePriceClickCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (closePrice.get().toString().equals("--")) return;
            updatePriceEt(closePrice.get().toString());
        }
    });

    public BindingCommand priceSubtractClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (StringUtils.isEmpty(priceEt.get()) || Double.valueOf(priceEt.get()) < 1) {
                return;
            }
            priceEt.set(String.valueOf(new BigDecimal(priceEt.get()).subtract(new BigDecimal(1))));
        }
    });

    public BindingCommand priceAddClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (StringUtils.isEmpty(priceEt.get())) {
                return;
            }
            priceEt.set(String.valueOf(new BigDecimal(priceEt.get()).add(new BigDecimal(1))));
        }
    });

    public BindingCommand numberSubtractClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (StringUtils.isEmpty(numberEt.get()) || Double.valueOf(numberEt.get()) < 1) {
                return;
            }
            numberEt.set(String.valueOf(new BigDecimal(numberEt.get()).subtract(new BigDecimal(1))));
        }
    });

    public BindingCommand numberAddClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (StringUtils.isEmpty(numberEt.get())) {
                return;
            }
            numberEt.set(String.valueOf(new BigDecimal(numberEt.get()).add(new BigDecimal(1))));
        }
    });

    //划转
    public BindingCommand transWalletClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!App.getInstance().isAppLogin()) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
            } else {
                showDialog(App.getInstance().getString(R.string.loading));
                FinanceClient.getInstance().getCoinSupport();
            }

        }
    });
    //看多
    public BindingCommand tradeMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            submit(0);
        }
    });


    //看空
    public BindingCommand tradeEmptyCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            submit(1);
        }
    });

    //登录
    public BindingCommand loginClickCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!App.getInstance().isAppLogin()) {
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                        .navigation();
            }
        }
    });

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<CfdAllThumbResult.DataBean> mCfdSymbolSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<MarketSymbolResult> mMarketCfdSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<CfdSymbolAllResult.DataBean> mSymbolSwitchSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> isLoginSingleLiveEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> isRefresh = new SingleLiveEvent<>();
    }

    public void getCfdSymbolAll(Context context, OnRequestListener onRequestListener) {
        isLoadSuccess = false;
        isCfdWalletRetry = false;
        isFirstHttpLoadSuccess = false;
        this.mContext = context;
        this.mOnRequestListener = onRequestListener;
        CfdClient.getInstance().getAllCfdSymbol();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean) {
        if ((!isVisible2User() || isOnPause)
                && (!eventBean.getOrigin().equals(EvKey.cfdSymbolAll))
                && (!eventBean.getOrigin().equals(EvKey.loginStatue))
                && (!eventBean.getOrigin().equals(EvKey.coinWallet)))
            return;
        switch (eventBean.getOrigin()) {
            //cfd可用币种
            case EvKey.cfdSymbolAll:
                if (!Constant.isHttpAndWs) {
                    //http
                    if (eventBean.isStatue()) {
                        cfdSymbolAllResult = (CfdSymbolAllResult) eventBean.getObject();
                        if (!cfdSymbolAllResult.getData().isEmpty()) {
                            allSymbol = new String[cfdSymbolAllResult.getData().size()];
                            for (int i = 0; i < cfdSymbolAllResult.getData().size(); i++) {
                                allSymbol[i] = cfdSymbolAllResult.getData().get(i).getSymbol();
                            }
                            initLoadSymbol(cfdSymbolAllResult.getData().get(0));
                            if (App.getInstance().isAppLogin()) {
                                FinanceClient.getInstance().getCoinWallet("CFD");
                            } else {
                                mOnRequestListener.onSuccess(cfdSymbolAllResult);
                            }
                            isLoadSuccess = true;
                        } else {
                            mOnRequestListener.onFail(App.getInstance().getString(R.string.no_support_coin));
                        }
                    } else {
                        mOnRequestListener.onFail(eventBean.getMessage());
                    }

                } else {
                    //http + ws
                    if (eventBean.isStatue()) {
                        cfdSymbolAllResult = (CfdSymbolAllResult) eventBean.getObject();
                        if (!cfdSymbolAllResult.getData().isEmpty()) {
                            allSymbol = new String[cfdSymbolAllResult.getData().size()];
                            for (int i = 0; i < cfdSymbolAllResult.getData().size(); i++) {
                                allSymbol[i] = cfdSymbolAllResult.getData().get(i).getSymbol();
                            }
                            initLoadSymbol(cfdSymbolAllResult.getData().get(0));
                            if (App.getInstance().isAppLogin()) {
                                FinanceClient.getInstance().getCoinWallet("CFD");
                            }
                            CfdWsClient.getInstance().getCfdKlinePush();
                        } else {
                            mOnRequestListener.onFail(App.getInstance().getString(R.string.no_support_coin));
                        }
                    } else {
                        mOnRequestListener.onFail(eventBean.getMessage());
                    }
                }
                break;
            //CFD钱包信息
            case EvKey.coinWallet:
                if (eventBean.getType() != 2) return;
                if (eventBean.isStatue()) {
                    updateCfdAccountInfo((SpotWalletResult) eventBean.getObject());
                } else {
                    if (isCfdWalletRetry) {
                        mOnRequestListener.onFail(eventBean.getMessage());
                    } else {
                        isCfdWalletRetry = true;
                        FinanceClient.getInstance().getCoinWallet("CFD");
                    }
                }
                break;
            //币种缩略图推送
            case EvKey.cfdAllSymbolPush:
                if (!Constant.isHttpAndWs) {
                    //http
                    if (!isLoadSuccess) return;
                    synchronized (this) {
                        if (eventBean.isStatue()) {
                            mCfdAllThumbResult = (CfdAllThumbResult) eventBean.getObject();
                            for (CfdAllThumbResult.DataBean dataBean : mCfdAllThumbResult.getData()) {
                                if (dataBean.getScreenName().equals(currentCfdSymbolAllResult.getScreenName())) {
                                    updateLoadView(dataBean);
                                    break;
                                }
                            }
                            updateProfitAndLoss((CfdAllThumbResult) eventBean.getObject());
                        }
                    }
                } else {
                    //http + ws
                    synchronized (this) {
                        if (eventBean.isStatue()) {
                            mCfdAllThumbResult = (CfdAllThumbResult) eventBean.getObject();
                            for (CfdAllThumbResult.DataBean dataBean : mCfdAllThumbResult.getData()) {
                                if (dataBean.getSymbol().equals(symbolName.get())) {
                                    updateLoadView(dataBean);
                                    break;
                                }
                            }
                            updateProfitAndLoss((CfdAllThumbResult) eventBean.getObject());
                            if (!isFirstHttpLoadSuccess) {
                                mOnRequestListener.onSuccess(cfdSymbolAllResult);
                                isFirstHttpLoadSuccess = true;
                                CfdWsClient.getInstance().monitorCfdKlinePush();
                            }
                        } else {
                            if (isFirstHttpLoadSuccess) return;
                            mOnRequestListener.onFail(eventBean.getMessage());
                        }
                    }
                }
                break;
            //盘口轮询
            case EvKey.marketCfdSymbol:
                synchronized (this) {
                    if (eventBean.isStatue()
                            && eventBean.getMessage().equals(currentCfdSymbolAllResult.getScreenName())) {
                        uc.mMarketCfdSingleLiveEvent.setValue((MarketSymbolResult) eventBean.getObject());
                    }
                }
                break;
            //下单
            case EvKey.cfdOrderPlace:
                dismissDialog();
                if (eventBean.isStatue()) {
                    Toasty.showSuccess(App.getInstance().getString(R.string.order_success));
                    FinanceClient.getInstance().getCoinWallet("CFD");
                    EventBusUtils.postSuccessEvent(EvKey.cfdRefresh, BaseRequestCode.OK, currentCfdSymbolAllResult.getScreenName(), currentSymbolBeanPolling.getClose());
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //权益 - 风险率
            case EvKey.profitAndLossAllObserve:
                cfdPositionResult = (CfdPositionResult) eventBean.getObject();
                break;
            //平仓 - 撤单 刷新钱包
            case EvKey.cfdWalletRefresh:
                FinanceClient.getInstance().getCoinWallet("CFD");
                break;
            //查询平台支持到币种信息
            case EvKey.coinSupport:
                dismissDialog();
                if (eventBean.isStatue()) {
                    CoinSupportBean coinSupportBean = (CoinSupportBean) eventBean.getObject();
                    if (coinSupportBean.getData().isEmpty()) {
                        Toasty.showError(App.getInstance().getString(R.string.no_support_coin));
                        return;
                    } else {
                        mCoinTransPopup = new CoinTransPopup(mContext, 2, coinSupportBean);
                        new XPopup.Builder(mContext)
                                .asCustom(mCoinTransPopup)
                                .show();
                    }
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
            //划转
            case EvKey.coinTransfer:
                if (eventBean.isStatue()) {
                    if (mCoinTransPopup != null && mCoinTransPopup.isShow()) {
                        mCoinTransPopup.dismiss();
                    }
                    Toasty.showSuccess(App.getInstance().getString(R.string.coin_trans_success));
                    FinanceClient.getInstance().getCoinWallet("CFD");
                } else {
                    Toasty.showError(eventBean.getMessage());
                }
                break;
            //登录状态监听
            case EvKey.loginStatue:
                loginStatue.set(App.getInstance().isAppLogin());
                uc.isLoginSingleLiveEvent.setValue(App.getInstance().isAppLogin());
                if (!App.getInstance().isAppLogin()) {
                    numberAvailable.set(App.getInstance().getString(R.string.can_open) + "--" + App.getInstance().getString(R.string.hands));
                    accountAvailable.set("----");
                    accountOccupy.set("----");
                    accountFreeze.set("----");
                    rights.set("----");
                    riskRate.set("----");
                } else {
                    FinanceClient.getInstance().getCoinWallet("CFD");
                }
                break;
            case EvKey.logout_success_401:
                if (eventBean.isStatue()) {
                    uc.isRefresh.setValue(true);
                }
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WebSocketResponse webSocketResponse) {
        switch (webSocketResponse.getCmd()) {
            //Ws 登录
            case WsCMD.JSONLOGIN:
                if (webSocketResponse.getCode() == BaseRequestCode.OK) {
                    WebSocketRequest mWebSocketRequest = new WebSocketRequest();
                    mWebSocketRequest.setCode(WsConstant.CODE_CFD_TRADE);
                    mWebSocketRequest.setCmd(WsCMD.PUSH_REQUEST);
                    mWebSocketRequest.setBody(null);
                    EventBusUtils.postEvent(mWebSocketRequest);
                }
                break;
            //缩略图推送
            case WsCMD.PUSH_CFD_THUMB:
                Constant.lastCfdKlinePushTime = System.currentTimeMillis();
                if (!isFirstHttpLoadSuccess) return;
                try {
                    String json = "{\"date\":" + webSocketResponse.getResponse() + "}";
                    CfdThumbBean cfdThumbBean = App.gson.fromJson(json, CfdThumbBean.class);
                    for (int i = 0; i < mCfdAllThumbResult.getData().size(); i++) {
                        for (CfdThumbBean.DateBean dateBean : cfdThumbBean.getDate()) {
                            if (mCfdAllThumbResult.getData().get(i).getSymbol().equals(dateBean.getSymbol())) {
                                mCfdAllThumbResult.getData().get(i).setVolume(dateBean.getVolume());
                                mCfdAllThumbResult.getData().get(i).setHigh(dateBean.getHigh());
                                mCfdAllThumbResult.getData().get(i).setChg(dateBean.getChg());
                                mCfdAllThumbResult.getData().get(i).setCnyLegalAsset(dateBean.getCnyLegalAsset());
                                mCfdAllThumbResult.getData().get(i).setLow(dateBean.getLow());
                                mCfdAllThumbResult.getData().get(i).setClose(dateBean.getClose());
                                mCfdAllThumbResult.getData().get(i).setTurnover(dateBean.getTurnover());
                            }
                        }
                        if (symbolName.get().equals(mCfdAllThumbResult.getData().get(i).getSymbol())) {
                            currentSymbolWsBean = mCfdAllThumbResult.getData().get(i);
                        }
                    }
                    updateLoadView(currentSymbolWsBean);
                    updateProfitAndLoss(mCfdAllThumbResult);
                } catch (Exception e) {
                    LogUtils.e("Exception", e.toString());
                }
                break;
            //盘口
            case WsCMD.PUSH_CFD_TRADE_PLATE:
                try {
                    Constant.lastCfdMarketPushTime = System.currentTimeMillis();
                    if (webSocketResponse.getCode() == BaseRequestCode.OK) {
                        JSONArray askArray = (JSONArray) new JSONObject(BaseApplication.gson.fromJson(webSocketResponse.getResponse(), Map.class)).get("asks");
                        if (askArray != null) {
                            askList.clear();
                            for (int i = 0; i < askArray.length(); i++) {
                                JSONArray data = askArray.optJSONArray(i);
                                MarketSymbolResult.DataBean.AskBean askBean = new MarketSymbolResult.DataBean.AskBean();
                                askBean.setPrice(data.optDouble(0));
                                askBean.setAmount(data.optDouble(1));
                                askList.add(askBean);
                            }
                        }
                        JSONArray bidArray = (JSONArray) new JSONObject(BaseApplication.gson.fromJson(webSocketResponse.getResponse(), Map.class)).get("bids");
                        if (bidArray != null) {
                            bidList.clear();
                            for (int i = 0; i < bidArray.length(); i++) {
                                JSONArray data = bidArray.optJSONArray(i);
                                MarketSymbolResult.DataBean.BidBean bidBean = new MarketSymbolResult.DataBean.BidBean();
                                bidBean.setPrice(data.optDouble(0));
                                bidBean.setAmount(data.optDouble(1));
                                bidList.add(bidBean);
                            }
                        }
                        MarketSymbolResult marketSymbolResult = new MarketSymbolResult();
                        marketSymbolResult.setData(new MarketSymbolResult.DataBean(askList, bidList));
                        uc.mMarketCfdSingleLiveEvent.setValue(marketSymbolResult);
                    }
                } catch (Exception e) {
                    LogUtils.e("Exception", e.toString());
                }

                break;
        }
    }

    /**
     * 切换币种刷新
     *
     * @param dataBean
     */
    public void initLoadSymbol(CfdSymbolAllResult.DataBean dataBean) {
        currentCfdSymbolAllResult = dataBean;
        if (App.getInstance().isAppLogin()) {
            EventBusUtils.postSuccessEvent(EvKey.cfdRefreshSceenName, BaseRequestCode.OK, dataBean.getSymbol());
            EventBusUtils.postSuccessEvent(EvKey.cfdRefresh, BaseRequestCode.OK, dataBean.getScreenName(), 0.0);
        }
        Constant.ContractSymbolRate = dataBean.getCoinScale();
        Constant.contractPriceRate = dataBean.getBaseCoinScale();
        Constant.currencyNumberRate = dataBean.getCoinScale();
        LogUtils.e("ContractSymbolRate", Constant.ContractSymbolRate);
        symbolName.set(dataBean.getSymbol());
        allLever = dataBean.getMarginLever().split(",");
        initLeverAmount(allLever[allLever.length - 1]);
        symbolNameTV.set(initSymbol(dataBean.getSymbol()));
        priceTag.set(App.getInstance().getString(R.string.price) + "(" + dataBean.getSymbol().split("/")[1] + ")");
        numberTag.set(App.getInstance().getString(R.string.number) + "(" + dataBean.getSymbol().split("/")[0] + ")");
        closePrice.set("--");
        priceConvert.set("≈ --CNY");
        numberAvailable.set(App.getInstance().getString(R.string.can_open) + "--" + App.getInstance().getString(R.string.hands));
        commissionAccount.set(App.getInstance().getString(R.string.commission_margin) + "--");
        uc.mSymbolSwitchSingleLiveEvent.setValue(dataBean);
        if (!Constant.isHttpAndWs) {
            //Http
            CfdClient.getInstance().getMarketSymbolPolling(dataBean.getScreenName());
        } else {
            // Ws + Http
            if (!TextUtils.isEmpty(Constant.lastCfdMarketPushSymbol)) {
                unsubscribeCfdTradePlate();
            }
            Constant.lastCfdMarketPushSymbol = symbolName.get();
            subscribeCfdTradePlate();
            CfdWsClient.getInstance().monitorCfdMarketPush(symbolName.get());
        }
    }

    /**
     * CFD钱包信息
     *
     * @param spotWalletResult
     */
    private void updateCfdAccountInfo(SpotWalletResult spotWalletResult) {
        if (mOnRequestListener == null) return;
        walletAvailable = 0;
        walletOccupy = 0;
        walletFreeze = 0;
        rightsValue = 0;
        for (SpotWalletResult.DataBean dataBean : spotWalletResult.getData()) {
            walletAvailable = new BigDecimal(dataBean.getBalance()).add(new BigDecimal(walletAvailable)).doubleValue();
            walletOccupy = new BigDecimal(dataBean.getPositionMargin()).add(new BigDecimal(walletOccupy)).doubleValue();
            walletFreeze = new BigDecimal(dataBean.getFrozenMargin()).add(new BigDecimal(walletFreeze)).doubleValue();
        }
        if (walletAvailable == 0) {
            numberAvailable.set(App.getInstance().getString(R.string.can_open) + "0.00" + mContext.getString(R.string.hands));
        }
        accountAvailable.set(MathUtils.getRundNumber(walletAvailable, 4, null) + " USDT");
        accountOccupy.set(MathUtils.getRundNumber(walletOccupy, 4, null) + " USDT");
        accountFreeze.set(MathUtils.getRundNumber(walletFreeze, 4, null) + " USDT");
        rightsValue = walletAvailable + walletOccupy + walletFreeze;
        if (walletOccupy == 0) {
            riskRateValue = 0;
        } else {
            riskRateValue = (rightsValue / walletOccupy) * 100;
        }
        rights.set(MathUtils.getRundNumber(rightsValue, 4, null) + " USDT");
        riskRate.set(MathUtils.getRundNumber(riskRateValue, 2, null) + "%");

        mOnRequestListener.onSuccess(cfdSymbolAllResult);
    }

    /**
     * 刷新轮询
     *
     * @param dataBean
     */
    private void updateLoadView(CfdAllThumbResult.DataBean dataBean) {
        if (null == currentSymbolBean || !currentSymbolBean.getSymbol().equals(dataBean.getSymbol())) {
            currentSymbolBean = dataBean;
            priceEt.set(DfUtils.numberFormat(dataBean.getClose(), dataBean.getBaseCoinScreenScale()));
            uc.mCfdSymbolSingleLiveEvent.setValue(dataBean);
        }

        currentSymbolBeanPolling = dataBean;
        closePrice.set(initClose(dataBean));
        closePriceType.set(dataBean.getChg() >= 0);
        priceConvert.set(initConvert(dataBean));
        initNumberAvailable();
        initCommissionAccount();
    }

    //根据轮询刷新权益和风险率
    private void updateProfitAndLoss(CfdAllThumbResult cfdAllThumbResult) {
        if (App.getInstance().isAppLogin() && null != cfdPositionResult && !cfdPositionResult.getData().isEmpty()) {
            profitAndLoss = 0;
            for (CfdPositionResult.DataBean cfdPositionResultDatum : cfdPositionResult.getData()) {
                for (CfdAllThumbResult.DataBean thumbResultDatum : cfdAllThumbResult.getData()) {
                    if (cfdPositionResultDatum.getSymbol().equals(thumbResultDatum.getSymbol())) {
                        cfdPositionResultDatum.setCurrentPrice(thumbResultDatum.getClose());
                        profitAndLoss += (cfdPositionResultDatum.getProfitAndloss() * cfdPositionResultDatum.getVolume() * cfdPositionResultDatum.getMultiplier());
                        break;
                    }
                }
            }

            rightsValue = walletAvailable + walletFreeze + walletOccupy + profitAndLoss;
            rights.set(MathUtils.getRundNumber(rightsValue, 4, null) + " USDT");
            if (walletOccupy == 0) {
                riskRateValue = 0;
            } else {
                riskRateValue = (rightsValue / walletOccupy) * 100;
            }
            riskRate.set(MathUtils.getRundNumber(riskRateValue, 2, null) + "%");
        }
    }


    //可开=可用资金/[(当前价*合约乘数*1)/杠杆倍数+手续费]
    //手续费 = 当前价*合约乘数*手续费比例
    public void initNumberAvailable() {
        if (null == currentSymbolBeanPolling
                || null == currentCfdSymbolAllResult
                || walletAvailable == 0
                || currentSymbolBeanPolling.getClose() == 0
                || !App.getInstance().isAppLogin())
            return;

        double fee, walletAvailableValue;
        fee = currentSymbolBeanPolling.getClose() * currentCfdSymbolAllResult.getMultiplier() * currentCfdSymbolAllResult.getOpenFee();
        walletAvailableValue = profitAndLoss >= 0 ? walletAvailable : walletAvailable + profitAndLoss;
        numberAvailableValue = walletAvailableValue / ((currentSymbolBeanPolling.getClose() * currentCfdSymbolAllResult.getMultiplier()) / leverAmount + fee);
        numberAvailable.set(App.getInstance().getString(R.string.can_open) + DfUtils.numberFormat(Math.floor(numberAvailableValue), 0) + App.getInstance().getString(R.string.hands));
    }

    //委托保证金
    //委托保证金=保证金+委托价值*开仓手续费率
    //保证金=委托价值除以杠杆倍数
    //委托价值 = 价格*合约乘数*手数；
    //价格 = 市价就是最新价，限价就是用户设置的价格
    public void initCommissionAccount() {
        if (null == currentSymbolBeanPolling || null == currentCfdSymbolAllResult) return;

        double calculationCommissionAccount, commissionPrice, marginPrice;
        if (buyType.get()) {
            //市价
            commissionPrice = currentSymbolBeanPolling.getClose() * currentCfdSymbolAllResult.getMultiplier() * Double.valueOf(StringUtils.isEmpty(numberEt.get()) ? "0" : numberEt.get());
            marginPrice = commissionPrice / leverAmount;
            calculationCommissionAccount = marginPrice + commissionPrice * currentCfdSymbolAllResult.getOpenFee();
        } else {
            //限价
            commissionPrice = Double.valueOf(StringUtils.isEmpty(priceEt.get()) ? "0" : priceEt.get()) * currentCfdSymbolAllResult.getMultiplier() * Double.valueOf(StringUtils.isEmpty(numberEt.get()) ? "0" : numberEt.get());
            marginPrice = commissionPrice / leverAmount;
            calculationCommissionAccount = marginPrice + commissionPrice * currentCfdSymbolAllResult.getOpenFee();
        }
        commissionAccount.set(App.getInstance().getString(R.string.commission_margin) + MathUtils.getRundNumber(calculationCommissionAccount, 2, null));
    }

    /**
     * 价格监听
     *
     * @param price
     */
    public void updatePriceEt(String price) {
        priceEt.set(price);
        initCommissionAccount();
    }

    /**
     * 手数监听
     *
     * @param number
     */
    public void updateNumEt(String number) {
        numberEt.set(number);
        initCommissionAccount();
    }

    //杠杆数
    public void initLeverAmount(String leverAmountText) {
        leverAmount = Integer.valueOf(leverAmountText);
        leverNum.set(App.getInstance().getString(R.string.lever) + leverAmountText + "X");
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
    public CharSequence initClose(CfdAllThumbResult.DataBean dataBean) {
        String close = DfUtils.numberFormat(dataBean.getClose(), dataBean.getBaseCoinScreenScale());
        if (!close.contains(".")) return close;
        CharSequence text = new SpanUtils()
                .append(close.split("\\.")[0])
                .append("." + close.split("\\.")[1])
                .setFontSize(12, true)
                .create();
        return text;
    }

    public String initConvert(CfdAllThumbResult.DataBean dataBean) {
        return "≈" + MathUtils.getRundNumber(dataBean.getCnyLegalAsset(), 2, null)
                + "CNY";
    }

    /**
     * 看多 - 看空提交
     *
     * @param type
     */
    private void submit(int type) {
        if (null == currentCfdSymbolAllResult || null == currentSymbolBeanPolling) return;

        if (!App.getInstance().isAppLogin()) {
            ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_LOGIN)
                    .navigation();
            return;
        }

        if (!buyType.get()) {
            if (!StringUtils.isNotEmpty(priceEt.get()) || Double.parseDouble(priceEt.get()) <= 0) {
                Toasty.showError(App.getInstance().getString(R.string.price_bigger_0));
                return;
            }
            if (type == 0) {
                if (Double.parseDouble(priceEt.get()) > currentSymbolBeanPolling.getClose()) {
                    Toasty.showError(App.getInstance().getString(R.string.no_high_market_price));
                    return;
                }
            } else {
                if (Double.parseDouble(priceEt.get()) < currentSymbolBeanPolling.getClose()) {
                    Toasty.showError(App.getInstance().getString(R.string.no_low_market_price));
                    return;
                }
            }
        }
        if (!StringUtils.isNotEmpty(numberEt.get()) || Double.parseDouble(numberEt.get()) <= 0) {
            Toasty.showError(App.getInstance().getString(R.string.number_bigger_0));
            return;
        }
        if (currentCfdSymbolAllResult.getMaxExchangeSize() < Double.parseDouble(numberEt.get())) {
            Toasty.showError(App.getInstance().getString(R.string.maximum_transactions) + currentCfdSymbolAllResult.getMaxExchangeSize() + App.getInstance().getString(R.string.hands));
            return;
        }

        final CfdTradePlaceBean cfdTradePlaceBean = new CfdTradePlaceBean();
        cfdTradePlaceBean.setSymbol(currentCfdSymbolAllResult.getScreenName());
        cfdTradePlaceBean.setVolume(Integer.valueOf(numberEt.get()));
        cfdTradePlaceBean.setMarginLever(leverAmount);
        cfdTradePlaceBean.setPrice(buyType.get() ? currentSymbolBeanPolling.getClose() : Double.valueOf(priceEt.get()));
        cfdTradePlaceBean.setPriceType(buyType.get() ? 0 : 1);
        cfdTradePlaceBean.setSide(type);
        double openFee = currentSymbolBeanPolling.getClose() * currentCfdSymbolAllResult.getOpenFee() * (Integer.valueOf(numberEt.get()) * currentCfdSymbolAllResult.getMultiplier());
        cfdTradePlaceBean.setOpenFee(openFee);
        cfdTradePlaceBean.setMultiplier(currentCfdSymbolAllResult.getMultiplier());
        new XPopup.Builder(mContext)
                .asCustom(new CfdTradePopup(mContext, buyType.get(), type, commissionAccount.get().substring(6), cfdTradePlaceBean, new OnCfdTradePlaceListener() {
                    @Override
                    public void onChooseType(double stopProfitPrice, double stopLossPrice) {
                        cfdTradePlaceBean.setStopProfitPrice(stopProfitPrice);
                        cfdTradePlaceBean.setStopLossPrice(stopLossPrice);
                        showDialog(App.getInstance().getString(R.string.loading));
                        CfdClient.getInstance().orderPlace(cfdTradePlaceBean);
                    }
                }))
                .show();
    }

    /**
     * 订阅盘口ws
     */
    private void subscribeCfdTradePlate() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_MARKET);
        mWebSocketRequest.setCmd(WsCMD.SUBSCRIBE_CFD_TRADE_PLATE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setCFDMarketJsonMap(symbolName.get())).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    /**
     * 取消订阅
     */
    private void unsubscribeCfdTradePlate() {
        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_MARKET);
        mWebSocketRequest.setCmd(WsCMD.UNSUBSCRIBE_CFD_TRADE_PLATE);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setCFDMarketJsonMap(Constant.lastCfdMarketPushSymbol)).getBytes());
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
        return Constant.isTradeVisiable && Constant.tradePage == 2;
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
