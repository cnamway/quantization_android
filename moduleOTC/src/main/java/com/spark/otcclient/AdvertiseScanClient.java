package com.spark.otcclient;

import com.spark.otcclient.base.OtcHost;
import com.spark.otcclient.pojo.AdCreateBean;
import com.spark.otcclient.pojo.AdSelfDownFindResult;
import com.spark.otcclient.pojo.AdSelfUpFindResult;
import com.spark.otcclient.pojo.FindMerchantDetailsResult;
import com.spark.otcclient.pojo.FindPageBean;
import com.spark.otcclient.pojo.FindPageResult;
import com.spark.otcclient.pojo.FindPriceResult;
import com.spark.otcclient.pojo.LcCoinListResult;
import com.spark.otcclient.pojo.TradeAreaListResult;
import com.spark.otcclient.pojo.TradeCoinListResult;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

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
 * 创建日期：2019/5/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AdvertiseScanClient extends BaseHttpClient {
    private static AdvertiseScanClient sAdvertiseScanClient;

    private AdvertiseScanClient() {
    }

    public static AdvertiseScanClient getInstance() {
        if (sAdvertiseScanClient == null) {
            synchronized (AdvertiseScanClient.class) {
                if (sAdvertiseScanClient == null) {
                    sAdvertiseScanClient = new AdvertiseScanClient();
                }
            }
        }
        return sAdvertiseScanClient;
    }

    /**
     * 查询所有的币种
     */
    public void getTradeCoinList() {
        EasyHttp.get(OtcHost.findIndexCoinListUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                TradeCoinListResult tradeCoinListBean = BaseApplication.gson.fromJson(s, TradeCoinListResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.tradeCoinList, tradeCoinListBean.getCode(), tradeCoinListBean.getMessage(), tradeCoinListBean);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.tradeCoinList, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.tradeCoinList, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.tradeCoinList, e);
                    }
                });
    }

    /**
     * 查询法币首页所有的币种
     */
    public void getIndexTradeCoinList() {
        EasyHttp.get(OtcHost.findIndexCoinListUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                LcCoinListResult lcCoinListResult = BaseApplication.gson.fromJson(s, LcCoinListResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.indexCoinList, generalResult.getCode(), generalResult.getMessage(), lcCoinListResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.indexCoinList, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.indexCoinList, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.indexCoinList, e);
                    }
                });
    }


    /**
     * 按指定币种查询上架的广告
     *
     * @param pageIndex
     * @param coinName
     * @param tradeType //1 - 我要买  0 - 我要卖
     * @param payMode
     * @param country
     * @param minLimit
     * @param maxLimit
     */
    public void findPage(int pageIndex, final String coinName, int tradeType, String payMode, String country, String minLimit, String maxLimit) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(20);
        findPageBean.setSortFields("");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        FindPageBean.QueryListBean queryListBean1 = new FindPageBean.QueryListBean();
        queryListBean1.setJoin("and");
        queryListBean1.setKey("coinName");
        queryListBean1.setOper("=");
        queryListBean1.setValue(coinName);
        queryList.add(queryListBean1);

        FindPageBean.QueryListBean queryListBean2 = new FindPageBean.QueryListBean();
        queryListBean2.setJoin("and");
        queryListBean2.setKey("advertiseType");
        queryListBean2.setOper("=");
        queryListBean2.setValue(String.valueOf(tradeType));
        queryList.add(queryListBean2);

        if (!StringUtils.isEmpty(country)) {
            FindPageBean.QueryListBean queryListBean4 = new FindPageBean.QueryListBean();
            queryListBean4.setJoin("and");
            queryListBean4.setKey("country");
            queryListBean4.setOper("=");
            queryListBean4.setValue(country);
            queryList.add(queryListBean4);
        }

        if (!StringUtils.isEmpty(minLimit)) {
            FindPageBean.QueryListBean queryListBean5 = new FindPageBean.QueryListBean();
            queryListBean5.setJoin("and");
            queryListBean5.setKey("minLimit");
            queryListBean5.setOper(">=");
            queryListBean5.setValue(minLimit);
            queryList.add(queryListBean5);
        }

        if (!StringUtils.isEmpty(maxLimit)) {
            FindPageBean.QueryListBean queryListBean6 = new FindPageBean.QueryListBean();
            queryListBean6.setJoin("and");
            queryListBean6.setKey("maxLimit");
            queryListBean6.setOper("<=");
            queryListBean6.setValue(maxLimit);
            queryList.add(queryListBean6);
        }

        if (!StringUtils.isEmpty(payMode)) {
            boolean has = false;
            FindPageBean.QueryListBean queryListBean3 = new FindPageBean.QueryListBean();
            if (payMode.contains(Constant.alipay)) {
                queryListBean3 = new FindPageBean.QueryListBean();
                if (has) {
                    queryListBean3.setJoin("and");
                } else {
                    queryListBean3.setJoin("andNew");
                    has = true;
                }
                queryListBean3.setKey("payMode");
                queryListBean3.setOper(":");
                queryListBean3.setValue(Constant.alipay);
                queryList.add(queryListBean3);
            }
            if (payMode.contains(Constant.wechat)) {
                queryListBean3 = new FindPageBean.QueryListBean();
                if (has) {
                    queryListBean3.setJoin("and");
                } else {
                    queryListBean3.setJoin("andNew");
                    has = true;
                }
                queryListBean3.setKey("payMode");
                queryListBean3.setOper(":");
                queryListBean3.setValue(Constant.wechat);
                queryList.add(queryListBean3);
            }
            if (payMode.contains(Constant.card)) {
                queryListBean3 = new FindPageBean.QueryListBean();
                if (has) {
                    queryListBean3.setJoin("and");
                } else {
                    queryListBean3.setJoin("andNew");
                    has = true;
                }
                queryListBean3.setKey("payMode");
                queryListBean3.setOper(":");
                queryListBean3.setValue(Constant.card);
                queryList.add(queryListBean3);
            }
            if (payMode.contains(Constant.PAYPAL)) {
                queryListBean3 = new FindPageBean.QueryListBean();
                if (has) {
                    queryListBean3.setJoin("and");
                } else {
                    queryListBean3.setJoin("andNew");
                    has = true;
                }
                queryListBean3.setKey("payMode");
                queryListBean3.setOper(":");
                queryListBean3.setValue(Constant.PAYPAL);
                queryList.add(queryListBean3);
            }
            if (payMode.contains(Constant.other)) {
                queryListBean3 = new FindPageBean.QueryListBean();
                if (has) {
                    queryListBean3.setJoin("and");
                } else {
                    queryListBean3.setJoin("andNew");
                    has = true;
                }
                queryListBean3.setKey("payMode");
                queryListBean3.setOper(":");
                queryListBean3.setValue(Constant.other);
                queryList.add(queryListBean3);
            }

        }

        findPageBean.setQueryList(queryList);

        EasyHttp.post(OtcHost.findPageUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                FindPageResult findPageResult = BaseApplication.gson.fromJson(s, FindPageResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.findPage, findPageResult.getCode(), coinName, findPageResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.findPage, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.findPage, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.findPage, e);
                    }
                });
    }


    /**
     * 查询所有的交易区
     */
    public void getTradeAreaList(final int type) {
        EasyHttp.get(OtcHost.tradeAreaListUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                TradeAreaListResult tradeAreaListResult = BaseApplication.gson.fromJson(s, TradeAreaListResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.tradeAreaList, tradeAreaListResult.getCode(), tradeAreaListResult.getMessage(), type, tradeAreaListResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.tradeAreaList, generalResult.getCode(), generalResult.getMessage(), type);
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.tradeAreaList, e, type);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.tradeAreaList, e, type);
                    }
                });
    }

    public void getTradeAreaList() {
        EasyHttp.get(OtcHost.tradeAreaListUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                TradeAreaListResult tradeAreaListResult = BaseApplication.gson.fromJson(s, TradeAreaListResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.tradeAreaList, tradeAreaListResult.getCode(), tradeAreaListResult.getMessage(), tradeAreaListResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.tradeAreaList, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.tradeAreaList, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.tradeAreaList, e);
                    }
                });
    }

    /**
     * 创建广告
     *
     * @param adCreateBean
     */
    public void createAd(AdCreateBean adCreateBean, final int type) {
        EasyHttp.post(OtcHost.adCreateUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(adCreateBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.adCreate, generalResult.getCode(), generalResult.getMessage(), type);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.adCreate, generalResult.getCode(), generalResult.getMessage(), type);
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.adCreate, e, type);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.adCreate, e, type);
                    }
                });
    }

    /**
     * 查询我的上架广告
     */
    public void selfAdvertiseUpFind() {
        EasyHttp.get(OtcHost.selfAdvertiseUpUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                AdSelfUpFindResult adSelfUpFindResult = BaseApplication.gson.fromJson(s, AdSelfUpFindResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.adSelfFindUp, adSelfUpFindResult.getCode(), adSelfUpFindResult.getMessage(), adSelfUpFindResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.adSelfFindUp, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.adSelfFindUp, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.adSelfFindUp, e);
                    }
                });
    }

    /**
     * 查询我的归档广告
     */
    public void selfAdvertiseDownFind(int pageIndex) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(20);
        findPageBean.setSortFields("createTime_d");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        /*FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
        queryListBean.setJoin("and");
        queryListBean.setKey("advertiseType");
        queryListBean.setOper("=");
        queryListBean.setValue("0");
        queryList.add(queryListBean);*/

        findPageBean.setQueryList(queryList);

        EasyHttp.post(OtcHost.selfAdvertiseDownUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                AdSelfDownFindResult adDownResult = BaseApplication.gson.fromJson(s, AdSelfDownFindResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.adSelfFindDown, adDownResult.getCode(), adDownResult.getMessage(), adDownResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.adSelfFindDown, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.adSelfFindDown, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.adSelfFindDown, e);
                    }
                });
    }

    /**
     * 上架广告
     */
    public void adOnShelves(long advertiseId, String tradePwd) {
        EasyHttp.post(OtcHost.onShelvesUrl + advertiseId)
                .baseUrl(BaseHost.OTC_HOST)
                .params("tradePwd", tradePwd)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.adOnShelves, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.adOnShelves, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.adOnShelves, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.adOnShelves, e);
                    }
                });
    }

    /**
     * 下架广告
     */
    public void adOffShelves(long advertiseId) {
        EasyHttp.post(OtcHost.offShelvesUrl + advertiseId)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.adOffShelves, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.adOffShelves, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.adOffShelves, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.adOffShelves, e);
                    }
                });
    }

    /**
     * 删除广告
     */
    public void adDelete(long advertiseId, String tradePwd) {
        EasyHttp.post(OtcHost.selfAdvertiseDelete + advertiseId)
                .baseUrl(BaseHost.OTC_HOST)
                .params("tradePwd", tradePwd)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.adDelete, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.adDelete, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.adDelete, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.adDelete, e);
                    }
                });
    }

    /**
     * 价格获取接口
     *
     * @param coinName
     * @param currency
     */
    public void priceFind(String coinName, String currency) {
        EasyHttp.get(OtcHost.priceFindUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .params("coinName", coinName)
                .params("currency", currency)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("priceFind", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                FindPriceResult findPriceResult = BaseApplication.gson.fromJson(s, FindPriceResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.pirceFind, generalResult.getCode(), generalResult.getMessage(), findPriceResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.pirceFind, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.pirceFind, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.pirceFind, e);
                    }
                });
    }

    /**
     * @param coinName
     * @param currency
     * @param type     0 - 购买  1 - 出售
     */
    public void priceFind(String coinName, String currency, final int type) {
        EasyHttp.get(OtcHost.priceFindUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .params("coinName", coinName)
                .params("currency", currency)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                FindPriceResult findPriceResult = BaseApplication.gson.fromJson(s, FindPriceResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.pirceFind, generalResult.getCode(), generalResult.getMessage(), type, findPriceResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.pirceFind, generalResult.getCode(), generalResult.getMessage(), type);
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.pirceFind, e, type);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.pirceFind, e, type);
                    }
                });
    }

    /**
     * 认证商家信息查询
     */
    public void findMerchantDetails(int memberId) {
        EasyHttp.get(OtcHost.findMerchantUrl + memberId)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                FindMerchantDetailsResult findMerchantDetailsResult = BaseApplication.gson.fromJson(s, FindMerchantDetailsResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.findMerchantDetails, generalResult.getCode(), generalResult.getMessage(), findMerchantDetailsResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.findMerchantDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.findMerchantDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.findMerchantDetails, e);
                    }
                });
    }
}
