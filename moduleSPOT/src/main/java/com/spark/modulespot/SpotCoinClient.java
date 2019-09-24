package com.spark.modulespot;

import com.spark.modulespot.base.SpotHost;
import com.spark.modulespot.pojo.FavorFindResult;
import com.spark.modulespot.pojo.OpenOrderDetailsResult;
import com.spark.modulespot.pojo.OpenOrdersBean;
import com.spark.modulespot.pojo.OpenOrdersResult;
import com.spark.modulespot.pojo.PlaceOrderBean;
import com.spark.modulespot.pojo.QueryWithSymbolResult;
import com.spark.modulespot.pojo.SpotCoinResult;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/27
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SpotCoinClient extends BaseHttpClient {

    private static SpotCoinClient sSpotCoinClient;

    private SpotCoinClient() {
    }

    public static SpotCoinClient getInstance() {
        if (sSpotCoinClient == null) {
            synchronized (SpotCoinClient.class) {
                if (sSpotCoinClient == null) {
                    sSpotCoinClient = new SpotCoinClient();
                }
            }
        }
        return sSpotCoinClient;
    }

    /**
     * 查询平台支持的币币可用币种
     */
    public void getSpotCoinAll() {
        EasyHttp.get(SpotHost.spotCoinAllUrl)
                .baseUrl(BaseHost.SPOT_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                SpotCoinResult spotCoinResult = BaseApplication.gson.fromJson(s, SpotCoinResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.spotCoinAll, spotCoinResult.getCode(), spotCoinResult.getMessage(), spotCoinResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.spotCoinAll, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.spotCoinAll, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.spotCoinAll, e);
                    }
                });
    }

    /**
     * 获取全部收藏币种
     */
    public void getFavorFind() {
        EasyHttp.get(SpotHost.favorFindUrl)
                .baseUrl(BaseHost.SPOT_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                FavorFindResult favorFindResult = BaseApplication.gson.fromJson(s, FavorFindResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.favorFind, favorFindResult.getCode(), favorFindResult.getMessage(), favorFindResult);
                            } else {
//                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
//                                    uodateLogin(generalResult);
//                                } else {
//                                    EventBusUtils.postErrorEvent(EvKey.favorFind, generalResult.getCode(), generalResult.getMessage());
//                                }
                                EventBusUtils.postErrorEvent(EvKey.favorFind, generalResult.getCode(), generalResult.getMessage());
                            }
                        } catch (Exception e) {
                            postException(EvKey.favorFind, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.favorFind, e);
                    }
                });
    }

    /**
     * 收藏币种
     *
     * @param symbol
     */
    public void favorAdd(String symbol) {
        EasyHttp.get(SpotHost.favorAddUrl + symbol.replace("/", "-"))
                .baseUrl(BaseHost.SPOT_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.favorAdd, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.favorAdd, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.favorAdd, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.favorAdd, e);
                    }
                });
    }

    /**
     * 删除币种
     *
     * @param symbol
     */
    public void favorDelete(String symbol) {
        EasyHttp.get(SpotHost.favorDeleteUrl + symbol.replace("/", "-"))
                .baseUrl(BaseHost.SPOT_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.favorDelete, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.favorDelete, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.favorDelete, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.favorDelete, e);
                    }
                });
    }

    /**
     * 当前委托
     *
     * @param pageIndex
     * @param side      "" - 全部  "0" - 买入 "1" - 卖出
     * @param symbol
     */
    public void findOpenOrders(int pageIndex, String side, String symbol) {
        OpenOrdersBean openOrdersBean = new OpenOrdersBean();
        openOrdersBean.setPageNo(pageIndex);
        openOrdersBean.setPageSize(10);
        openOrdersBean.setSymbol(symbol);
        openOrdersBean.setSide(side);

        EasyHttp.post(SpotHost.openOrdersUrl)
                .baseUrl(BaseHost.SPOT_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(openOrdersBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("findOpenOrders", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                OpenOrdersResult openOrdersResult = BaseApplication.gson.fromJson(s, OpenOrdersResult.class);
                                openOrdersResult.setHistoryOrder(false);
                                EventBusUtils.postSuccessEvent(EvKey.openOrders, generalResult.getCode(), generalResult.getMessage(), openOrdersResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.openOrders, generalResult.getCode(), generalResult.getMessage());

                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.openOrders, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.openOrders, e);
                    }
                });
    }

    /**
     * 历史委托
     *
     * @param pageIndex
     * @param symbol
     * @param side
     */
    public void findOpenOrdersHistory(int pageIndex, String side, String symbol) {
        OpenOrdersBean openOrdersBean = new OpenOrdersBean();
        openOrdersBean.setPageNo(pageIndex);
        openOrdersBean.setPageSize(10);
        openOrdersBean.setSymbol(symbol);
        openOrdersBean.setSide(side);
        EasyHttp.post(SpotHost.openOrdersHistoryUrl)
                .baseUrl(BaseHost.SPOT_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(openOrdersBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("findOpenOrders", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                OpenOrdersResult openOrdersResult = BaseApplication.gson.fromJson(s, OpenOrdersResult.class);
                                openOrdersResult.setHistoryOrder(true);
                                EventBusUtils.postSuccessEvent(EvKey.openOrdersHistory, generalResult.getCode(), generalResult.getMessage(), openOrdersResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.openOrdersHistory, generalResult.getCode(), generalResult.getMessage());

                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.openOrdersHistory, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.openOrdersHistory, e);
                    }
                });
    }

    /**
     * 用户下单
     */
    public void placeOrder(PlaceOrderBean placeOrderBean) {
        EasyHttp.post(SpotHost.placeOrderUrl)
                .baseUrl(BaseHost.SPOT_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(placeOrderBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.placeOrder, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.placeOrder, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.placeOrder, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.placeOrder, e);
                    }
                });
    }

    /**
     * 历史委托成交明细
     *
     * @param orderId
     */
    public void openOrderDetails(String orderId) {
        EasyHttp.get(SpotHost.openOrdersDetailsUrl + orderId)
                .baseUrl(BaseHost.SPOT_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                OpenOrderDetailsResult openOrderDetailsResult = BaseApplication.gson.fromJson(s, OpenOrderDetailsResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.openOrderDetails, generalResult.getCode(), generalResult.getMessage(), openOrderDetailsResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.openOrderDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.openOrderDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.openOrderDetails, e);
                    }
                });
    }


    /**
     * 用户撤单
     *
     * @param orderId
     */
    public void cancelOrder(String orderId) {
        EasyHttp.get(SpotHost.cancelOrderUrl + orderId + SpotHost.cancelOrderUrlEnd)
                .baseUrl(BaseHost.SPOT_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("cancelOrder", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.cancelOrder, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.cancelOrder, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.cancelOrder, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.cancelOrder, e);
                    }
                });
    }

    /**
     * spot钱包指定交易对查询业务处理
     *
     * @param symbol
     */
    public void queryWithSymbol(String symbol) {
        EasyHttp.get(SpotHost.queryWithSymbolUrl)
                .baseUrl(BaseHost.SPOT_HOST)
                .params("symbol", symbol)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                QueryWithSymbolResult queryWithSymbolResult = BaseApplication.gson.fromJson(s, QueryWithSymbolResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.queryWithSymbol, generalResult.getCode(), generalResult.getMessage(), queryWithSymbolResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.queryWithSymbol, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.queryWithSymbol, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.queryWithSymbol, e);
                    }
                });
    }


}
