package com.example.modulecfd;

import com.example.modulecfd.base.CfdHost;
import com.example.modulecfd.pojo.CfdAllThumbResult;
import com.example.modulecfd.pojo.CfdCommissionResult;
import com.example.modulecfd.pojo.CfdDealResult;
import com.example.modulecfd.pojo.CfdOrderPostBean;
import com.example.modulecfd.pojo.CfdOrderQueryBean;
import com.example.modulecfd.pojo.CfdPositionResult;
import com.example.modulecfd.pojo.CfdProfitLossBean;
import com.example.modulecfd.pojo.CfdRevokeResult;
import com.example.modulecfd.pojo.CfdSymbolAllResult;
import com.example.modulecfd.pojo.CfdTradeOrderResult;
import com.example.modulecfd.pojo.CfdTradePlaceBean;
import com.example.modulecfd.pojo.KlineControllerBean;
import com.spark.modulespot.pojo.MarketSymbolResult;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-06-25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdClient extends BaseHttpClient {
    private static CfdClient sCfdClient;
    private KlineControllerBean klineControllerBean = new KlineControllerBean();
    private String symbolCurrent = "";
    private List<MarketSymbolResult.DataBean.AskBean> askList = new ArrayList<>();
    private List<MarketSymbolResult.DataBean.BidBean> bidList = new ArrayList<>();

    private CfdClient() {
    }

    public static CfdClient getInstance() {
        if (sCfdClient == null) {
            synchronized (CfdClient.class) {
                if (sCfdClient == null) {
                    sCfdClient = new CfdClient();
                }
            }
        }
        return sCfdClient;
    }

    /**
     * CFD所有支持币种
     */
    public void getAllCfdSymbol() {
        EasyHttp.get(CfdHost.cfdSymbolAll)
                .baseUrl(BaseHost.CFD_HOST)
                .params("symbol",Constant.casSymbol)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {

                                CfdSymbolAllResult cfdSymbolAllResult = BaseApplication.gson.fromJson(s, CfdSymbolAllResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.cfdSymbolAll, generalResult.getCode(), generalResult.getMessage(), cfdSymbolAllResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdSymbolAll, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdSymbolAll, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdSymbolAll, e);
                    }
                });

    }


    /**
     * 成交全币种缩略图
     */
    private void getAllThumb() {
        EasyHttp.post(CfdHost.cfdAllThumbUrl)
                .baseUrl(BaseHost.KLINE_HOST)
                .retryCount(0)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(klineControllerBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        CfdAllThumbResult cfdAllThumbResult = BaseApplication.gson.fromJson(s, CfdAllThumbResult.class);
                        if (cfdAllThumbResult.getCode() == BaseRequestCode.OK) {
                            EventBusUtils.postSuccessEvent(EvKey.cfdAllSymbolPush, cfdAllThumbResult.getCode(), cfdAllThumbResult.getMessage(), cfdAllThumbResult);
                        } else {
                            EventBusUtils.postErrorEvent(EvKey.cfdAllSymbolPush, cfdAllThumbResult.getCode(), cfdAllThumbResult.getMessage());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.cfdAllSymbolPush, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 成交全币种缩略图轮询
     */
    public void getAllThumbPolling() {
        klineControllerBean.setService("CFD");
        if (!StringUtils.isEmpty(Constant.casSymbol)){
            klineControllerBean.setSymbol(Constant.casSymbol);
        }
        //2s刷新一次
        Observable.interval(0, 2, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (Constant.isHomeVisiable) {
                            if (Constant.isTradeVisiable && Constant.tradePage == 2) {
                                getAllThumb();
                            }
                        } else {
                            if (Constant.isCfdKineVisiable || Constant.isCfdKineHorizontalVisiable || Constant.isMeFinanceVisiable) {
                                getAllThumb();
                            }
                        }

                    }
                }).subscribe();
    }


    /**
     * 获取指定币种指定深度盘口
     *
     * @param symbol
     */
    private void getMarketCfdSymbol(final String symbol) {
        EasyHttp.get(CfdHost.marketCfdSymbolUrl + symbol.replace("/", "-") + "/20")
                .baseUrl(BaseHost.QUOTE_HOST)
                .retryCount(0)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                JSONArray askArray = (JSONArray) new JSONObject((String) BaseApplication.gson.fromJson(s, Map.class).get("data")).get("asks");
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
                                JSONArray bidArray = (JSONArray) new JSONObject((String) BaseApplication.gson.fromJson(s, Map.class).get("data")).get("bids");
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

                                EventBusUtils.postSuccessEvent(EvKey.marketCfdSymbol, generalResult.getCode(), symbol, marketSymbolResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.marketCfdSymbol, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            LogUtils.e("getMarketCfdSymbol", e.toString());
                            postException(EvKey.marketCfdSymbol, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.marketCfdSymbol, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取指定币种指定深度盘口轮询
     */
    public void getMarketSymbolPolling(String symbol) {
        if (StringUtils.isEmpty(symbolCurrent)) {
            symbolCurrent = symbol;
            //2s刷新一次
            Observable.interval(0, 2, TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (Constant.isHomeVisiable) {
                                if (Constant.isTradeVisiable && Constant.tradePage == 2) {
                                    getMarketCfdSymbol(symbolCurrent);
                                }
                            }
                        }
                    }).subscribe();
        } else {
            symbolCurrent = symbol;
        }
    }


    /**
     * 合约下单
     */
    public void orderPlace(CfdTradePlaceBean cfdTradePlaceBean) {
        EasyHttp.post(CfdHost.orderPlaceUrl)
                .baseUrl(BaseHost.CFD_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(cfdTradePlaceBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.cfdOrderPlace, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdOrderPlace, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdOrderPlace, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdOrderPlace, e);
                    }
                });
    }

    /**
     * 撤单
     *
     * @param cfdOrderPostBean
     */
    public void orderCancel(CfdOrderPostBean cfdOrderPostBean) {
        EasyHttp.post(CfdHost.orderCancelUrl)
                .baseUrl(BaseHost.CFD_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(cfdOrderPostBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.cfdOrderCancel, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdOrderCancel, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdOrderCancel, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdOrderCancel, e);
                    }
                });
    }

    /**
     * 平仓
     *
     * @param cfdOrderPostBean
     */
    public void orderClose(CfdOrderPostBean cfdOrderPostBean) {
        EasyHttp.post(CfdHost.orderCloseUrl)
                .baseUrl(BaseHost.CFD_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(cfdOrderPostBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.cfdOrderClose, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdOrderClose, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdOrderClose, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdOrderClose, e);
                    }
                });
    }

    /**
     * 设置止盈止损价格
     *
     * @param cfdProfitLossBean
     */
    public void orderProfitLossSet(final int type, CfdProfitLossBean cfdProfitLossBean) {
        LogUtils.e("orderProfitLossSet", "come");
        EasyHttp.post(CfdHost.orderProfitLosslUrl)
                .baseUrl(BaseHost.CFD_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(cfdProfitLossBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.cfdOrderProfitLoss, generalResult.getCode(), generalResult.getMessage(), type);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdOrderProfitLoss, generalResult.getCode(), generalResult.getMessage(), type);
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdOrderProfitLoss, e, type);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdOrderProfitLoss, e, type);
                    }
                });
    }

    /**
     * 仓位
     */
    public void orderPosition() {
        EasyHttp.get(CfdHost.orderPositionUrl)
                .baseUrl(BaseHost.CFD_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("orderPosition", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CfdPositionResult cfdPositionResult = BaseApplication.gson.fromJson(s, CfdPositionResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.cfdOrderPosition, generalResult.getCode(), generalResult.getMessage(), cfdPositionResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdOrderPosition, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdOrderPosition, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdOrderPosition, e);
                    }
                });
    }

    /**
     * 当前委托
     *
     * @param symbol
     * @param page
     */
    public void orderCommission(String symbol, int page) {
        CfdOrderQueryBean cfdOrderQueryBean = new CfdOrderQueryBean();
        cfdOrderQueryBean.setSymbol(symbol);
        cfdOrderQueryBean.setPageNo(page);
        cfdOrderQueryBean.setPageSize(10);

        EasyHttp.post(CfdHost.orderCommissionUrl)
                .baseUrl(BaseHost.CFD_HOST)
                .params("symbol", symbol)
                .params("pageNo", page + "")
                .params("pageSize", "10")
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(cfdOrderQueryBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("orderCommission", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CfdCommissionResult cfdCommissionResult = BaseApplication.gson.fromJson(s, CfdCommissionResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.cfdOrderCommission, generalResult.getCode(), generalResult.getMessage(), cfdCommissionResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdOrderCommission, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdOrderCommission, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdOrderCommission, e);
                    }
                });
    }

    /**
     * 已成交
     *
     * @param symbol
     * @param page
     */
    public void orderDeal(String symbol, int page) {
        CfdOrderQueryBean cfdOrderQueryBean = new CfdOrderQueryBean();
        cfdOrderQueryBean.setSymbol(symbol);
        cfdOrderQueryBean.setPageNo(page);
        cfdOrderQueryBean.setPageSize(10);

        EasyHttp.post(CfdHost.orderDealUrl)
                .baseUrl(BaseHost.CFD_HOST)
                .params("symbol", symbol)
                .params("pageNo", page + "")
                .params("pageSize", "10")
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(cfdOrderQueryBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("orderCommission", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CfdDealResult cfdDealResult = BaseApplication.gson.fromJson(s, CfdDealResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.cfdOrderDeal, generalResult.getCode(), generalResult.getMessage(), cfdDealResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdOrderDeal, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdOrderDeal, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdOrderDeal, e);
                    }
                });
    }

    /**
     * 已撤单
     *
     * @param symbol
     * @param page
     */
    public void orderRevoke(String symbol, int page) {
        CfdOrderQueryBean cfdOrderQueryBean = new CfdOrderQueryBean();
        cfdOrderQueryBean.setSymbol(symbol);
        cfdOrderQueryBean.setPageNo(page);
        cfdOrderQueryBean.setPageSize(10);

        EasyHttp.post(CfdHost.orderRevokeUrl)
                .baseUrl(BaseHost.CFD_HOST)
                .params("symbol", symbol)
                .params("pageNo", page + "")
                .params("pageSize", "10")
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(cfdOrderQueryBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("orderCommission", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CfdRevokeResult cfdRevokeResult = BaseApplication.gson.fromJson(s, CfdRevokeResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.cfdOrderRevoke, generalResult.getCode(), generalResult.getMessage(), cfdRevokeResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdOrderRevoke, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdOrderRevoke, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdOrderRevoke, e);
                    }
                });
    }

    /**
     * 交易记录
     *
     * @param page
     */
    public void getCfdTradeOrder(int page) {
        EasyHttp.get(CfdHost.cfdTradeOrderUrl)
                .baseUrl(BaseHost.CFD_HOST)
                .params("pageNo", page + "")
                .params("pageSize", "20")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CfdTradeOrderResult cfdTradeOrderResult = BaseApplication.gson.fromJson(s, CfdTradeOrderResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.cfdTradeOrder, generalResult.getCode(), generalResult.getMessage(), cfdTradeOrderResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cfdTradeOrder, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cfdTradeOrder, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cfdTradeOrder, e);
                    }
                });
    }
}
