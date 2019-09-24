package com.spark.otcclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.spark.otcclient.base.OtcHost;
import com.spark.otcclient.pojo.AuthMerchantResult;
import com.spark.otcclient.pojo.FindPageBean;
import com.spark.otcclient.pojo.LcOrderResult;
import com.spark.otcclient.pojo.OrderAppealBean;
import com.spark.otcclient.pojo.OrderCreateBean;
import com.spark.otcclient.pojo.OrderDetailsResult;
import com.spark.otcclient.pojo.OrderPayBean;
import com.spark.otcclient.pojo.UploadBase64PicEntity;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.CommonResult;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.BitmapUtils;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LcTradeClient extends BaseHttpClient {
    private static LcTradeClient sLcTradeClient;

    private LcTradeClient() {
    }

    public static LcTradeClient getInstance() {
        if (sLcTradeClient == null) {
            synchronized (LcTradeClient.class) {
                if (sLcTradeClient == null) {
                    sLcTradeClient = new LcTradeClient();
                }
            }
        }
        return sLcTradeClient;
    }

    /**
     * 创建订单请求
     */
    public void orderCreate(OrderCreateBean orderCreateBean, final String coinName) {
        EasyHttp.post(OtcHost.orderCreateUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(orderCreateBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.orderCreate, generalResult.getCode(), generalResult.getMessage(), coinName);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.orderCreate, generalResult.getCode(), generalResult.getMessage(), coinName);
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.orderCreate, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.orderCreate, e);
                    }
                });
    }

    /**
     * 查询我的归档订单 / 查询我的在途订单
     *
     * @param pageIndex
     * @param staue     订单状态 0-已取消 1-未付款 2-已付款 3-已完成 4-申诉中
     * @param adType    订单类型 0-买入 1-卖出
     * @param coinName
     */
    public void getLcOrder(int pageIndex, String staue, String adType, String coinName) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(20);
        findPageBean.setSortFields("createTime_d");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        if (!StringUtils.isEmpty(staue)) {
            FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
            queryListBean.setJoin("and");
            queryListBean.setKey("status");
            queryListBean.setOper("=");
            queryListBean.setValue(staue);
            queryList.add(queryListBean);
        }

        if (!StringUtils.isEmpty(adType)) {
            FindPageBean.QueryListBean queryListBean2 = new FindPageBean.QueryListBean();
            queryListBean2.setJoin("and");
            queryListBean2.setKey("orderType");
            queryListBean2.setOper("=");
            queryListBean2.setValue(adType);
            queryList.add(queryListBean2);
        }

        if (!StringUtils.isEmpty(coinName)) {
            FindPageBean.QueryListBean queryListBean3 = new FindPageBean.QueryListBean();
            queryListBean3.setJoin("and");
            queryListBean3.setKey("coinName");
            queryListBean3.setOper("in");
            queryListBean3.setValue(coinName);
            queryList.add(queryListBean3);
        }

        findPageBean.setQueryList(queryList);

        EasyHttp.post((staue.equals("1") || staue.equals("2") || staue.equals("4")) ? OtcHost.lcOrderTransitPageQueryUrl : OtcHost.lcOrderQueryUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("getLcOrder",s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                LcOrderResult lcOrderResult = BaseApplication.gson.fromJson(s, LcOrderResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.lcOrderDetails, lcOrderResult.getCode(), lcOrderResult.getMessage(), lcOrderResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.lcOrderDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.lcOrderDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.lcOrderDetails, e);
                    }
                });
    }

    public void getLcOrderAll(int pageIndex,String adType, String coinName) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(20);
        findPageBean.setSortFields("createTime_d");


        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        if (!StringUtils.isEmpty(adType)) {
            FindPageBean.QueryListBean queryListBean2 = new FindPageBean.QueryListBean();
            queryListBean2.setJoin("and");
            queryListBean2.setKey("a.orderType");
            queryListBean2.setOper("=");
            queryListBean2.setValue(adType);
            queryList.add(queryListBean2);
        }

        if (!StringUtils.isEmpty(coinName)) {
            FindPageBean.QueryListBean queryListBean3 = new FindPageBean.QueryListBean();
            queryListBean3.setJoin("and");
            queryListBean3.setKey("a.coinName");
            queryListBean3.setOper("in");
            queryListBean3.setValue(coinName);
            queryList.add(queryListBean3);
        }

        findPageBean.setQueryList(queryList);

        EasyHttp.post(OtcHost.lcOrderListUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("getLcOrderAll",s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                LcOrderResult lcOrderResult = BaseApplication.gson.fromJson(s, LcOrderResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.lcOrderDetails, lcOrderResult.getCode(), lcOrderResult.getMessage(), lcOrderResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.lcOrderDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.lcOrderDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.lcOrderDetails, e);
                    }
                });
    }


    /**
     * 查询在途或归档订单详情
     *
     * @param isInTransit 是否为在途订单
     * @param orderSn
     */
    public void orderDetails(boolean isInTransit, String orderSn) {
        EasyHttp.get((isInTransit ? OtcHost.lcOrderorderInTransitDetailUrl : OtcHost.lcOrderorderIAchiveDetailUrl) + orderSn)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("orderDetails", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                OrderDetailsResult orderDetailsResult = BaseApplication.gson.fromJson(s, OrderDetailsResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.lcOrderTypeDetails, generalResult.getCode(), generalResult.getMessage(), orderDetailsResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.lcOrderTypeDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.lcOrderTypeDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.lcOrderTypeDetails, e);
                    }
                });
    }

    /**
     * 确认付款
     *
     * @param orderPayBean
     */
    public void orderPayMent(OrderPayBean orderPayBean) {
        EasyHttp.post(OtcHost.lcPaymentlUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(orderPayBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.lcOrderPayMent, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.lcOrderPayMent, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.lcOrderPayMent, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.lcOrderPayMent, e);
                    }
                });
    }

    /**
     * 取消订单
     *
     * @param orderSn
     */
    public void orderCancel(String orderSn) {
        EasyHttp.get(OtcHost.lcOrderCancelUrl + orderSn)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("orderCancel", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.lcOrderCancel, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.lcOrderCancel, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.lcOrderCancel, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.lcOrderCancel, e);
                    }
                });
    }

    /**
     * 订单放行
     *
     * @param orderSn
     */
    public void orderRelease(String orderSn, String tradePwd) {
        EasyHttp.get(OtcHost.lcOrderReleaseUrl + orderSn)
                .baseUrl(BaseHost.OTC_HOST)
                .params("tradePwd", tradePwd)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("orderRelease", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.lcOrderRelease, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.lcOrderRelease, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.lcOrderRelease, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.lcOrderRelease, e);
                    }
                });
    }

    /**
     * 图片上传
     *
     * @param outputFile
     * @param type       0 - 正面   1 - 反面  2 - 手持
     * @param height
     * @param width
     */
    public void uploadIdCardPic(File outputFile, final int type, int width, int height) {
        Bitmap bitmap = BitmapUtils.zoomBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()), width, height);
        String base64Data = BitmapUtils.imgToBase64(bitmap);
        bitmap.recycle();

        UploadBase64PicEntity mUploadBase64PicEntity = new UploadBase64PicEntity();
        mUploadBase64PicEntity.setBase64Data("data:image/jpeg;base64," + base64Data);
        mUploadBase64PicEntity.setOss(true);

        EasyHttp.post(OtcHost.uploadBase64PicUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(mUploadBase64PicEntity))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            CommonResult uploadBase64Result = null;
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                uploadBase64Result = BaseApplication.gson.fromJson(s, CommonResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.lcOrderPicupload, type, uploadBase64Result.getData());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.lcOrderPicupload, type, uploadBase64Result.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.lcOrderPicupload, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.lcOrderPicupload, e);
                    }
                });
    }


    /**
     * 订单申诉申请
     *
     * @param orderAppealBean
     */
    public void orderAppeal(OrderAppealBean orderAppealBean) {
        EasyHttp.post(OtcHost.lcOrderAppealUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(orderAppealBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("orderAppeal", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.lcOrderAppeal, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.lcOrderAppeal, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.lcOrderAppeal, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.lcOrderAppeal, e);
                    }
                });
    }

    /**
     * 查看自己认证商家信息
     */
    public void authMerchantFind() {
        EasyHttp.get(OtcHost.authMerchantFindUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("authMerchantFind", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                AuthMerchantResult authMerchantResult = BaseApplication.gson.fromJson(s, AuthMerchantResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.authMerchantFind, generalResult.getCode(), generalResult.getMessage(), authMerchantResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.authMerchantFind, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.authMerchantFind, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.authMerchantFind, e);
                    }
                });
    }
}
