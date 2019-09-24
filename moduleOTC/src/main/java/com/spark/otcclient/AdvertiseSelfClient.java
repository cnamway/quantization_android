package com.spark.otcclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spark.otcclient.base.OtcHost;
import com.spark.otcclient.pojo.AdCreateBean;
import com.spark.otcclient.pojo.AdDetailResult;
import com.spark.otcclient.pojo.AdSelfDownFindResult;
import com.spark.otcclient.pojo.AdSelfUpFindResult;
import com.spark.otcclient.pojo.AdvertiseCoinListResult;
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
public class AdvertiseSelfClient extends BaseHttpClient {
    private static AdvertiseSelfClient sAdvertiseScanClient;

    private AdvertiseSelfClient() {
    }

    public static AdvertiseSelfClient getInstance() {
        if (sAdvertiseScanClient == null) {
            synchronized (AdvertiseSelfClient.class) {
                sAdvertiseScanClient = new AdvertiseSelfClient();
            }
        }
        return sAdvertiseScanClient;
    }

    /**
     * 广告交易币种信息
     */
    public void getTradeCoinList(final int adType) {
        EasyHttp.get(OtcHost.advertiseCoinListUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                AdvertiseCoinListResult tradeCoinListBean = BaseApplication.gson.fromJson(s, AdvertiseCoinListResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.advertiseCoinList, tradeCoinListBean.getCode(), tradeCoinListBean.getMessage(), adType, tradeCoinListBean);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.advertiseCoinList, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.advertiseCoinList, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.advertiseCoinList, e);
                    }
                });
    }

    /**
     * 查询广告详情
     */
    public void selfAdvertiseUpFind(long id) {
        EasyHttp.get(OtcHost.selfAdvertiseUpUrl + "/" + id)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                AdDetailResult adDetailResult = BaseApplication.gson.fromJson(s, AdDetailResult.class);
                                if (adDetailResult != null) {
                                    Gson gson = new Gson();
                                    AdSelfDownFindResult.DataBean.RecordsBean recordsBean = new Gson().fromJson(gson.toJson(adDetailResult.getData()), new TypeToken<AdSelfDownFindResult.DataBean.RecordsBean>() {
                                    }.getType());
                                    EventBusUtils.postSuccessEvent(EvKey.advertiseDetail, adDetailResult.getCode(), adDetailResult.getMessage(), recordsBean);
                                }
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.advertiseDetail, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.advertiseDetail, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.advertiseDetail, e);
                    }
                });
    }


    /**
     * 修改广告
     *
     * @param adCreateBean
     */
    public void updateAd(AdCreateBean adCreateBean, final int type, long advertiseId) {
        EasyHttp.post(OtcHost.selfAdvertiseUpdate + advertiseId)
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

}
