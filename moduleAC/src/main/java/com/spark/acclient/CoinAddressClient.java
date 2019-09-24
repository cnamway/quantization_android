package com.spark.acclient;

import com.spark.acclient.base.AcHost;
import com.spark.acclient.pojo.CoinAddressAddBean;
import com.spark.acclient.pojo.CoinAddressListBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.CommonResult;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/22
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinAddressClient extends BaseHttpClient {
    private static CoinAddressClient sCoinAddressClient;

    private CoinAddressClient() {
    }

    public static CoinAddressClient getInstance() {
        if (sCoinAddressClient == null) {
            synchronized (FinanceClient.class) {
                if (sCoinAddressClient == null) {
                    sCoinAddressClient = new CoinAddressClient();
                }
            }
        }
        return sCoinAddressClient;
    }

    /**
     * 增加用户某个币种的提币地址信息
     *
     * @param address 提币地址
     * @param coinId  币种id
     * @param remark  备注信息
     */
    public void addCoinAddress(String address, String coinId, String remark, String code) {
        CoinAddressAddBean coinAddressAddBean = new CoinAddressAddBean();
        coinAddressAddBean.setAddress(address);
        coinAddressAddBean.setCoinId(coinId);
        coinAddressAddBean.setRemark(remark);

        EasyHttp.post(AcHost.coinAddressAddUrl)
                .baseUrl(BaseHost.AC_HOST)
                .headers("Content-Type", "application/json")
                .headers("check", "phone::" + code)
                .upJson(BaseApplication.gson.toJson(coinAddressAddBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CommonResult addCoinAddressResult = BaseApplication.gson.fromJson(s, CommonResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.coinAddressAdd, addCoinAddressResult.getCode(), addCoinAddressResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinAddressAdd, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinAddressAdd, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinAddressAdd, e);
                    }
                });
    }

    /**
     * 查询用户的某个币种的提币地址信息
     */
    public void getCoinAddressList() {
        EasyHttp.post(AcHost.coinAddressListUrl)
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CoinAddressListBean coinAddressListBean = BaseApplication.gson.fromJson(s, CoinAddressListBean.class);
                                EventBusUtils.postSuccessEvent(EvKey.coinAddressList, coinAddressListBean.getCode(), coinAddressListBean.getMessage(), coinAddressListBean);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinAddressList, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinAddressList, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinAddressList, e);
                    }
                });
    }

    /**
     * 删除提币地址信息
     *
     * @param id
     */
    public void deleteSelectCoin(int id) {
        EasyHttp.get(AcHost.coinAddressDeleteUrl)
                .baseUrl(BaseHost.AC_HOST)
                .params("id", String.valueOf(id))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.coinAddressDelete, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinAddressDelete, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinAddressDelete, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinAddressDelete, e);
                    }
                });
    }

    /**
     * 批量删除提币地址信息
     *
     * @param ids
     */
    public void deleteSelectCoinBatch(String ids) {
        EasyHttp.get(AcHost.coinAddressBatchDeleteUrl)
                .baseUrl(BaseHost.AC_HOST)
                .params("ids", ids)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.coinAddressDeleteBatch, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinAddressDeleteBatch, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinAddressDeleteBatch, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinAddressDeleteBatch, e);
                    }
                });
    }
}
