package com.spark.acclient;

import android.content.Context;

import com.spark.acclient.base.AcHost;
import com.spark.acclient.pojo.CoinAddressBean;
import com.spark.acclient.pojo.CoinExtractBean;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.acclient.pojo.CoinTransBean;
import com.spark.acclient.pojo.CoinTransDetailsResult;
import com.spark.acclient.pojo.CoinWithdrawAddressResult;
import com.spark.acclient.pojo.FindPageBean;
import com.spark.acclient.pojo.MerberWalletResult;
import com.spark.acclient.pojo.PropertyDetailsTypeResult;
import com.spark.acclient.pojo.SpotWalletResult;
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
import me.spark.mvvm.utils.LanguageSPUtil;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/16
 * 描    述：财务模块
 * 修订历史：
 * ================================================
 */
public class FinanceClient extends BaseHttpClient {
    private static FinanceClient sFinanceClient;

    private FinanceClient() {
    }

    public static FinanceClient getInstance() {
        if (sFinanceClient == null) {
            synchronized (FinanceClient.class) {
                if (sFinanceClient == null) {
                    sFinanceClient = new FinanceClient();
                }
            }
        }
        return sFinanceClient;
    }

    /**
     * 查询平台支持到币种信息
     */
    public void getCoinSupport() {
        EasyHttp.get(AcHost.coinSupportUrl)
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CoinSupportBean coinSupportBean = BaseApplication.gson.fromJson(s, CoinSupportBean.class);
                                if (null == coinSupportBean.getData()) {
                                    if (Constant.getCoinSupportRetryTime > 2) {
                                        Constant.getCoinSupportRetryTime += 1;
                                        getCoinSupport();
                                    } else {
                                        Constant.getCoinSupportRetryTime = 0;
                                        EventBusUtils.postErrorEvent(EvKey.coinSupport, 500, "发生异常，请重试！");
                                    }
                                } else {
                                    Constant.getCoinSupportRetryTime = 0;
                                    EventBusUtils.postSuccessEvent(EvKey.coinSupport, coinSupportBean.getCode(), coinSupportBean.getMessage(), coinSupportBean);
                                }
                            } else {
                                Constant.getCoinSupportRetryTime = 0;
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinSupport, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            Constant.getCoinSupportRetryTime = 0;
                            postException(EvKey.coinSupport, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinSupport, e);
                    }
                });
    }

    /**
     * 查询指定币种的钱包地址
     *
     * @param coinAddress
     */
    public void getCoinAddress(String coinAddress) {
        EasyHttp.get(AcHost.coinAddressUrl + coinAddress)
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CoinAddressBean coinAddressBean = BaseApplication.gson.fromJson(s, CoinAddressBean.class);
                                EventBusUtils.postSuccessEvent(EvKey.coinAddress, coinAddressBean.getCode(), coinAddressBean.getMessage(), coinAddressBean);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinAddress, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinAddress, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinAddress, e);
                    }
                });
    }

    /**
     * 查询用户币币账户某个业务所有资产信息
     *
     * @param coinAddress
     */
    public void getCoinOutInfo(String coinAddress) {
        EasyHttp.get(AcHost.coinOutInfoUrl + "/SPOT/" + coinAddress)
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                MerberWalletResult merberWalletResult = BaseApplication.gson.fromJson(s, MerberWalletResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.merberWallet, merberWalletResult.getCode(), merberWalletResult.getMessage(), merberWalletResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.merberWallet, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.merberWallet, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.merberWallet, e);
                    }
                });
    }

    /**
     * 查询用户法币某个业务所有资产信息
     *
     * @param type        0 - SPOT 1 - OTC
     * @param coinAddress
     */
    public void getCoinOutOtcInfo(int type, String coinAddress) {
        EasyHttp.get(AcHost.coinOutInfoUrl + (type == 0 ? "/SPOT/" : "/OTC/") + coinAddress)
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                MerberWalletResult merberWalletResult = BaseApplication.gson.fromJson(s, MerberWalletResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.merberOtcWallet, merberWalletResult.getCode(), merberWalletResult.getMessage(), merberWalletResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.merberOtcWallet, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.merberOtcWallet, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.merberOtcWallet, e);
                    }
                });
    }

    public void getCoinOutOtcInfo(String type, String coinAddress) {
        EasyHttp.get(AcHost.coinOutInfoUrl + type + coinAddress)
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                MerberWalletResult merberWalletResult = BaseApplication.gson.fromJson(s, MerberWalletResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.merberOtcWallet, merberWalletResult.getCode(), merberWalletResult.getMessage(), merberWalletResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.merberOtcWallet, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.merberOtcWallet, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.merberOtcWallet, e);
                    }
                });
    }

    /**
     * 查询用户的某个币种的提币地址信息
     *
     * @param coinAddress
     */
    public void getWithdrawAddress(String coinAddress) {
        EasyHttp.get(AcHost.withdrawAddressUrl + coinAddress)
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CoinWithdrawAddressResult coinWithdrawAddressResult = BaseApplication.gson.fromJson(s, CoinWithdrawAddressResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.coinWithdrawAddress, coinWithdrawAddressResult.getCode(), coinWithdrawAddressResult.getMessage(), coinWithdrawAddressResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinWithdrawAddress, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinWithdrawAddress, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinWithdrawAddress, e);
                    }
                });
    }

    /**
     * 用户提币请求
     *
     * @param code
     * @param coinExtractBean
     */
    public void withdrawRequest(String code, CoinExtractBean coinExtractBean) {
        EasyHttp.post(AcHost.withdrawRequestUrl)
                .baseUrl(BaseHost.AC_HOST)
                .headers("Content-Type", "application/json")
                .headers("check", "phone::" + code)
                .upJson(BaseApplication.gson.toJson(coinExtractBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.coinWithdrawRequest, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinWithdrawRequest, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinWithdrawRequest, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinWithdrawRequest, e);
                    }
                });
    }

    /**
     * 用户划转请求
     *
     * @param coinTransBean
     */
    public void coinTransfer(CoinTransBean coinTransBean) {
        EasyHttp.post(AcHost.coinTransferUrl)
                .baseUrl(BaseHost.AC_HOST)
                .upJson(BaseApplication.gson.toJson(coinTransBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.coinTransfer, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinTransfer, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinTransfer, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinTransfer, e);
                    }
                });
    }

    /**
     * cfd用户提币请求
     *
     * @param coinTransBean
     */
    public void coinCFfdTransfer(CoinTransBean coinTransBean) {
        EasyHttp.post(AcHost.coinTransferUrl)
                .baseUrl(BaseHost.AC_HOST)
                .upJson(BaseApplication.gson.toJson(coinTransBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.coinCfdTransfer, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinCfdTransfer, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinCfdTransfer, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinCfdTransfer, e);
                    }
                });
    }

    /**
     * 钱包业务类型:基础钱包-COMMON 币币钱包-SPOT 合约钱包-CFD 法币钱包-OTC
     *
     * @param type
     */
    public void getCoinWallet(final String type) {
        EasyHttp.get(AcHost.coinWalletUrl + type)
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                SpotWalletResult spotWalletResult = BaseApplication.gson.fromJson(s, SpotWalletResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.coinWallet, spotWalletResult.getCode(), spotWalletResult.getMessage(), setWalletType(type), spotWalletResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinWallet, generalResult.getCode(), generalResult.getMessage(), setWalletType(type));
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinWallet, e, setWalletType(type));
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinWallet, e, setWalletType(type));
                    }
                });
    }


    /**
     * @param type 0 - SPOT 1 - OTC 2 - CFD
     * @return
     */
    private int setWalletType(String type) {
        int tradeType = 0;
        switch (type) {
            case "SPOT":
                tradeType = 0;
                break;
            case "OTC":
                tradeType = 1;
                break;
            case "CFD":
                tradeType = 2;
                break;
        }
        return tradeType;
    }

    public void getLcWalletAvailable(final String coinName) {
        EasyHttp.get(AcHost.coinWalletUrl + "OTC")
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                SpotWalletResult spotWalletResult = BaseApplication.gson.fromJson(s, SpotWalletResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.coinWalletOtcAvailable, spotWalletResult.getCode(), coinName, spotWalletResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coinWalletOtcAvailable, generalResult.getCode(), coinName);
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coinWalletOtcAvailable, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coinWalletOtcAvailable, e);
                    }
                });
    }

    public void getPropertyDetailsType(Context context) {
        EasyHttp.get(AcHost.transLogTypeUrl + (LanguageSPUtil.getInstance(context).getSelectLanguage() == 0 ? "sub_type_zh" : "sub_type_en"))
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                PropertyDetailsTypeResult propertyDetailsTypeResult = BaseApplication.gson.fromJson(s, PropertyDetailsTypeResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.propertyDetailsType, generalResult.getCode(), generalResult.getMessage(), propertyDetailsTypeResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.propertyDetailsType, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.propertyDetailsType, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.propertyDetailsType, e);
                    }
                });
    }

    /**
     * 查询用户某个业务所有资产交易信息
     *
     * @param pageIndex
     * @param coinName
     * @param busiType  基础钱包-COMMON 币币钱包-SPOT 合约钱包-SWAP 法币钱包-OTC
     */
    public void getProperyDetails(int pageIndex, final String coinName, String busiType) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(20);
        findPageBean.setSortFields("createTime_d");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        FindPageBean.QueryListBean queryListBean2 = new FindPageBean.QueryListBean();
        queryListBean2.setJoin("and");
        queryListBean2.setKey("type");
        queryListBean2.setOper("in");
        queryListBean2.setValue("1,2,3,4,5,6");
        queryList.add(queryListBean2);
        FindPageBean.QueryListBean queryListBean3 = new FindPageBean.QueryListBean();
        queryListBean3.setJoin("and");
        queryListBean3.setKey("coinName");
        queryListBean3.setOper("=");
        queryListBean3.setValue(coinName);
        queryList.add(queryListBean3);

        findPageBean.setQueryList(queryList);

        EasyHttp.post(AcHost.transLogUrl + busiType)
                .baseUrl(BaseHost.AC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CoinTransDetailsResult coinTransDetailsResult = BaseApplication.gson.fromJson(s, CoinTransDetailsResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.propertyDetails, coinTransDetailsResult.getCode(), coinTransDetailsResult.getMessage(), coinTransDetailsResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.propertyDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.propertyDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.propertyDetails, e);
                    }
                });
    }

    /**
     * CFD资金划转记录
     *
     * @param pageIndex
     * @param busiType
     */
    public void getProperyCFDDetails(int pageIndex, String busiType) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(10);
        findPageBean.setSortFields("createTime_d");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
        queryListBean.setJoin("and");
        queryListBean.setKey("type");
        queryListBean.setOper("in");
        queryListBean.setValue("3,4");
        queryList.add(queryListBean);

        findPageBean.setQueryList(queryList);
        EasyHttp.post(AcHost.transLogUrl + busiType)
                .baseUrl(BaseHost.AC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CoinTransDetailsResult coinTransDetailsResult = BaseApplication.gson.fromJson(s, CoinTransDetailsResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.propertyCFDDetails, coinTransDetailsResult.getCode(), coinTransDetailsResult.getMessage(), coinTransDetailsResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.propertyCFDDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.propertyCFDDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.propertyCFDDetails, e);
                    }
                });
    }

    //筛选
    public void getProperyDetails(int pageIndex, String coinName, String busiType, String type, String subType, String minLimit, String maxLimit, String startTime, String endTime) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(20);
        findPageBean.setSortFields("createTime_d");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        FindPageBean.QueryListBean queryListBean1 = new FindPageBean.QueryListBean();
        queryListBean1.setJoin("and");
        queryListBean1.setKey("coinName");
        queryListBean1.setOper("=");
        queryListBean1.setValue(coinName);
        queryList.add(queryListBean1);

        FindPageBean.QueryListBean queryListBean2 = new FindPageBean.QueryListBean();
        queryListBean2.setJoin("and");
        queryListBean2.setKey("amount");
        queryListBean2.setOper("!=");
        queryListBean2.setValue("0");
        queryList.add(queryListBean2);

        if (StringUtils.isNotEmpty(type)) {
            FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
            queryListBean.setJoin("and");
            queryListBean.setKey("type");
            queryListBean.setOper("in");
            queryListBean.setValue(type);
            queryList.add(queryListBean);
        } else {
            FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
            queryListBean.setJoin("and");
            queryListBean.setKey("type");
            queryListBean.setOper("in");
            queryListBean.setValue("1,2,3,4,5,6");
            queryList.add(queryListBean);
        }

        if (StringUtils.isNotEmpty(subType)) {
            FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
            queryListBean.setJoin("and");
            queryListBean.setKey("subType");
            queryListBean.setOper("in");
            queryListBean.setValue(subType);
            queryList.add(queryListBean);
        }

        if (!StringUtils.isEmpty(minLimit)) {
            FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
            queryListBean.setJoin("and");
            queryListBean.setKey("amount");
            queryListBean.setOper(">=");
            queryListBean.setValue(minLimit);
            queryList.add(queryListBean);
        }

        if (!StringUtils.isEmpty(maxLimit)) {
            FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
            queryListBean.setJoin("and");
            queryListBean.setKey("amount");
            queryListBean.setOper("<=");
            queryListBean.setValue(maxLimit);
            queryList.add(queryListBean);
        }

        if (!StringUtils.isEmpty(startTime)) {
            FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
            queryListBean.setJoin("and");
            queryListBean.setKey("createTime");
            queryListBean.setOper(">=");
            queryListBean.setValue(startTime);
            queryList.add(queryListBean);
        }

        if (!StringUtils.isEmpty(endTime)) {
            FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
            queryListBean.setJoin("and");
            queryListBean.setKey("createTime");
            queryListBean.setOper("<=");
            queryListBean.setValue(endTime);
            queryList.add(queryListBean);
        }

        findPageBean.setQueryList(queryList);

        EasyHttp.post(AcHost.transLogUrl + busiType)
                .baseUrl(BaseHost.AC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CoinTransDetailsResult coinTransDetailsResult = BaseApplication.gson.fromJson(s, CoinTransDetailsResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.propertyDetails, coinTransDetailsResult.getCode(), coinTransDetailsResult.getMessage(), coinTransDetailsResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.propertyDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.propertyDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.propertyDetails, e);
                    }
                });
    }

    /**
     * 充币  提币  划转记录
     *
     * @param pageIndex
     * @param coinName
     * @param busiType
     * @param orderType
     */
    public void getProperyOrder(int pageIndex, final String coinName, String busiType, String orderType) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(10);
        findPageBean.setSortFields("createTime_d");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        if (!StringUtils.isEmpty(orderType)) {
            FindPageBean.QueryListBean queryListBean2 = new FindPageBean.QueryListBean();
            queryListBean2.setJoin("and");
            queryListBean2.setKey("type");
            queryListBean2.setOper("in");
            queryListBean2.setValue(orderType);
            queryList.add(queryListBean2);
        }

        if (!StringUtils.isEmpty(coinName)) {
            FindPageBean.QueryListBean queryListBean3 = new FindPageBean.QueryListBean();
            queryListBean3.setJoin("and");
            queryListBean3.setKey("coinName");
            queryListBean3.setOper("=");
            queryListBean3.setValue(coinName);
            queryList.add(queryListBean3);
        }


        findPageBean.setQueryList(queryList);

        EasyHttp.post(AcHost.transLogUrl + busiType)
                .baseUrl(BaseHost.AC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CoinTransDetailsResult coinTransDetailsResult = BaseApplication.gson.fromJson(s, CoinTransDetailsResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.propertyDetails, coinTransDetailsResult.getCode(), coinTransDetailsResult.getMessage(), coinTransDetailsResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.propertyDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.propertyDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.propertyDetails, e);
                    }
                });
    }

    /**
     * 查询交易类型type
     *
     * @param context
     */
    public void getPropertyDetailsTypeMain(Context context) {
        EasyHttp.get(AcHost.transLogTypeUrl + (LanguageSPUtil.getInstance(context).getSelectLanguage() == 0 ? "transaction_type_zh" : "transaction_type_en"))
                .baseUrl(BaseHost.AC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                PropertyDetailsTypeResult propertyDetailsTypeResult = BaseApplication.gson.fromJson(s, PropertyDetailsTypeResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.propertyDetailsTypeMain, generalResult.getCode(), generalResult.getMessage(), propertyDetailsTypeResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.propertyDetailsTypeMain, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.propertyDetailsTypeMain, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.propertyDetailsTypeMain, e);
                    }
                });
    }

}
