package com.spark.qtz.api;

import com.spark.qtz.App;
import com.spark.qtz.api.pojo.AnnounceBean;
import com.spark.qtz.api.pojo.ArticleListBean;
import com.spark.qtz.api.pojo.BannerBean;
import com.spark.qtz.api.pojo.Coin2ConvertCurrencyResult;
import com.spark.qtz.api.pojo.PromotionCodeLimitResult;
import com.spark.qtz.api.pojo.UpdateBean;
import com.spark.ucclient.pojo.FindPageBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.cache.model.CacheResult;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LanguageSPUtil;
import me.spark.mvvm.utils.Utils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/3
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AppClient extends BaseHttpClient {
    private static AppClient sAppClient;

    private AppClient() {
    }

    public static AppClient getInstance() {
        if (sAppClient == null) {
            synchronized (AppClient.class) {
                if (sAppClient == null) {
                    sAppClient = new AppClient();
                }
            }
        }
        return sAppClient;
    }

    /**
     * banner图
     */
    public void getBanner() {
        EasyHttp.get(AppHost.bannerUrl)
                .baseUrl(BaseHost.CMS_HOST)
                .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                .cacheKey("banner")
                .execute(new SimpleCallBack<CacheResult<String>>() {
                    @Override
                    public void onSuccess(CacheResult<String> stringCacheResult) {
                        try {
                            BannerBean bannerBean = App.gson.fromJson(stringCacheResult.data, BannerBean.class);
                            if (bannerBean.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.banner, bannerBean.getCode(), bannerBean.getMessage(), bannerBean);
                            } else {
                                EventBusUtils.postErrorEvent(EvKey.banner, bannerBean.getCode(), bannerBean.getMessage());
                            }
                        } catch (Exception e) {
                            postException(EvKey.banner, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.banner, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 公告
     */
    public void getAnnounce() {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(1);
        findPageBean.setPageSize(30);

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        FindPageBean.QueryListBean queryListBean1 = new FindPageBean.QueryListBean();
        queryListBean1.setJoin("and");
        queryListBean1.setKey("sysLanguage");
        queryListBean1.setOper("=");
        queryListBean1.setValue(LanguageSPUtil.getInstance(Utils.getContext()).getSelectLanguage() == 0 ? "CN" : "EN");
        queryList.add(queryListBean1);

        findPageBean.setQueryList(queryList);

        EasyHttp.post(AppHost.announceUrl)
                .baseUrl(BaseHost.CMS_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                .cacheKey("announce")
                .execute(new SimpleCallBack<CacheResult<String>>() {
                    @Override
                    public void onSuccess(CacheResult<String> s) {
                        try {
                            AnnounceBean announceBean = App.gson.fromJson(s.data, AnnounceBean.class);
                            if (announceBean.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.announce, announceBean.getCode(), announceBean.getMessage(), announceBean);
                            } else {
                                EventBusUtils.postErrorEvent(EvKey.announce, announceBean.getCode(), announceBean.getMessage());
                            }
                        } catch (Exception e) {
                            postException(EvKey.announce, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.announce, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * getArticleList
     */
    public void getArticleList() {
        EasyHttp.get(AppHost.articleListUrl)
                .baseUrl(BaseHost.CMS_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ArticleListBean articleListBean = App.gson.fromJson(s, ArticleListBean.class);
                            if (articleListBean.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.articleList, articleListBean.getCode(), articleListBean.getMessage(), articleListBean);
                            } else {
                                EventBusUtils.postErrorEvent(EvKey.articleList, articleListBean.getCode(), articleListBean.getMessage());
                            }
                        } catch (Exception e) {
                            postException(EvKey.articleList, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.articleList, e.getCode(), e.getMessage());
                    }
                });
    }

    /**
     * 获取数字货币->法币的汇率
     *
     * @param coin
     * @param convertCurrency
     */
    public void getCoin2ConvertCurrency(String coin, String convertCurrency) {
        EasyHttp.get(AppHost.coin2convertCurrencyUrl + coin + "/" + convertCurrency)
                .baseUrl(BaseHost.PRICE_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                Coin2ConvertCurrencyResult coin2ConvertCurrencyResult = BaseApplication.gson.fromJson(s, Coin2ConvertCurrencyResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.coin2ConvertCurrency, BaseRequestCode.OK, generalResult.getMessage(), coin2ConvertCurrencyResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.coin2ConvertCurrency, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.coin2ConvertCurrency, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.coin2ConvertCurrency, e);
                    }
                });
    }

    /**
     * 获取货币A->货币B的汇率
     *
     * @param convertCurrencyFrom
     * @param convertCurrencyTo
     */
    public void getConvertCurrency2ConvertCurrency(String convertCurrencyFrom, String convertCurrencyTo) {
        EasyHttp.get(AppHost.convertCurrency2convertCurrencyUrl + convertCurrencyFrom + "/" + convertCurrencyTo)
                .baseUrl(BaseHost.PRICE_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                Coin2ConvertCurrencyResult coin2ConvertCurrencyResult = BaseApplication.gson.fromJson(s, Coin2ConvertCurrencyResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.convertCurrency2ConvertCurrency, BaseRequestCode.OK, generalResult.getMessage(), coin2ConvertCurrencyResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.convertCurrency2ConvertCurrency, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.convertCurrency2ConvertCurrency, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.convertCurrency2ConvertCurrency, e);
                    }
                });
    }

    /**
     * 是否强制输入邀请码
     */
    public void checkPromotionLimit() {
        EasyHttp.get(AppHost.promotionLimitUrl)
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                PromotionCodeLimitResult promotionCodeLimitResult = BaseApplication.gson.fromJson(s, PromotionCodeLimitResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.promotionCodeLimit, BaseRequestCode.OK, generalResult.getMessage(), promotionCodeLimitResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.promotionCodeLimit, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.promotionCodeLimit, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.promotionCodeLimit, e);
                    }
                });
    }

    /**
     * 邀请码验证
     *
     * @param inviteCode
     */
    public void checkPromotionCode(String inviteCode) {
        String url = (inviteCode.startsWith("A") || inviteCode.startsWith("a")) ? AppHost.promotionCodeAgentApiUrl : AppHost.promotionCodeUrl;
        String baseUrl = (inviteCode.startsWith("A") || inviteCode.startsWith("a")) ? BaseHost.AGENT_HOST : BaseHost.UC_HOST;

        EasyHttp.get(url)
                .params("inviteCode", inviteCode)
                .baseUrl(baseUrl)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.promotionCodeCheck, BaseRequestCode.OK, generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.promotionCodeCheck, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.promotionCodeCheck, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.promotionCodeCheck, e);
                    }
                });
    }

    /**
     * 检测版本更新
     */
    public void checkVersionUpdate() {
        EasyHttp.get(AppHost.updateUrl)
                .params("platform", "1")
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            UpdateBean updateBean = BaseApplication.gson.fromJson(s, UpdateBean.class);
                            if (updateBean.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.checkVersionUpdate, BaseRequestCode.OK, "",updateBean);
                            } else {
                                EventBusUtils.postErrorEvent(EvKey.checkVersionUpdate, updateBean.getCode(), "");
                            }
                        } catch (Exception e) {
                            postException(EvKey.checkVersionUpdate, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.checkVersionUpdate, e);
                    }
                });
    }


}
