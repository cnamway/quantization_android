package com.spark.otcclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.spark.otcclient.base.OtcHost;
import com.spark.otcclient.pojo.PayListBean;
import com.spark.otcclient.pojo.PayTypeAddBean;
import com.spark.otcclient.pojo.PayTypeAddResult;
import com.spark.otcclient.pojo.UploadBase64PicEntity;
import com.spark.otcclient.pojo.UploadPicResult;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.io.File;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.CommonResult;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.utils.BitmapUtils;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/23
 * 描    述：收款方式
 * 修订历史：
 * ================================================
 */
public class PayControlClient extends BaseHttpClient {
    private static PayControlClient sPayControlClient;

    private PayControlClient() {
    }

    public static PayControlClient getInstance() {
        if (sPayControlClient == null) {
            synchronized (PayControlClient.class) {
                if (sPayControlClient == null) {
                    sPayControlClient = new PayControlClient();
                }
            }
        }
        return sPayControlClient;
    }

    /**
     * 查询用户支付配置列表
     */
    public void queryList() {
        EasyHttp.get(OtcHost.queryListUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                PayListBean payListBean = BaseApplication.gson.fromJson(s, PayListBean.class);
                                EventBusUtils.postSuccessEvent(EvKey.payList, payListBean.getCode(), payListBean.getMessage(), payListBean);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.payList, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.payList, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.payList, e);
                    }
                });
    }

    /**
     * 检查支付方式
     */
    public void payTypeCheck(PayTypeAddBean payTypeAddBean) {
        EasyHttp.post(OtcHost.payTypeCheckUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(payTypeAddBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(ApiException e) {

                    }
                });
    }

    /**
     * 添加支付方式
     */
    public void payTypeAdd(PayTypeAddBean payTypeAddBean) {
        EasyHttp.post(OtcHost.payTypeAddUrl)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(payTypeAddBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                PayTypeAddResult payTypeAddResult = BaseApplication.gson.fromJson(s, PayTypeAddResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.payTypeAdd, payTypeAddResult.getCode(), payTypeAddResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.payTypeAdd, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.payTypeAdd, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.payTypeAdd, e);
                    }
                });
    }

    /**
     * 重置支付方式
     *
     * @param id
     * @param payTypeAddBean
     */
    public void payTypeUpdate(int id, PayTypeAddBean payTypeAddBean) {
        EasyHttp.post(OtcHost.payTypeUpdateUrl + id)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(payTypeAddBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CommonResult payTypeUpdateResult = BaseApplication.gson.fromJson(s, CommonResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.payTypeUpdate, payTypeUpdateResult.getCode(), payTypeUpdateResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.payTypeUpdate, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.payTypeUpdate, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.payTypeUpdate, e);
                    }
                });
    }

    /**
     * 上传图片
     *
     * @param outputFile
     * @param width
     * @param height
     */
    public void uploadQRCode(File outputFile, int width, int height) {
        Bitmap bitmap = BitmapUtils.zoomBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()), width, height);
        String base64Data = BitmapUtils.imgToBase64(bitmap);
        bitmap.recycle();

        UploadBase64PicEntity mUploadBase64PicEntity = new UploadBase64PicEntity();
        mUploadBase64PicEntity.setBase64Data("data:image/jpeg;base64," + base64Data);
        mUploadBase64PicEntity.setOss(true);

        EasyHttp.post(OtcHost.uploadQrcode)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(mUploadBase64PicEntity))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                UploadPicResult uploadPicResult = BaseApplication.gson.fromJson(s, UploadPicResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.uploadQRCode, uploadPicResult.getCode(), uploadPicResult.getMessage(), uploadPicResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.uploadQRCode, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.uploadQRCode, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.uploadQRCode, e);
                    }
                });
    }

    /**
     * 修改支付方式状态
     *
     * @param id
     */
    public void payTypeUpdate(int id, int status) {
        EasyHttp.get(OtcHost.payTypeUpdateUrl + id + "/" + status)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CommonResult payTypeUpdateResult = BaseApplication.gson.fromJson(s, CommonResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.payTypeUpdate, payTypeUpdateResult.getCode(), payTypeUpdateResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.payTypeUpdate, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.payTypeUpdate, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.payTypeUpdate, e);
                    }
                });
    }

    /**
     * 删除支付方式
     *
     * @param id
     */
    public void payTypeDelete(int id, String pwd) {
        EasyHttp.post(OtcHost.payTypeDeleteUrl + id)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .params("pwd", pwd)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CommonResult payTypeUpdateResult = BaseApplication.gson.fromJson(s, CommonResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.payTypeUpdate, payTypeUpdateResult.getCode(), payTypeUpdateResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.payTypeUpdate, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.payTypeUpdate, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.payTypeUpdate, e);
                    }
                });
    }


}
