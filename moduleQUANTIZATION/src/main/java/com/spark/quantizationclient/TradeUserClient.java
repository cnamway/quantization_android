package com.spark.quantizationclient;

import com.google.gson.Gson;
import com.spark.quantizationclient.base.QuantizationHost;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseResponseError;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.EventBusUtils;

/**
 * 用户模块
 */
public class TradeUserClient extends BaseHttpClient {

    private static TradeUserClient ucClient;

    private TradeUserClient() {
    }

    public static TradeUserClient getInstance() {
        if (ucClient == null) {
            synchronized (TradeUserClient.class) {
                ucClient = new TradeUserClient();
            }
        }
        return ucClient;
    }

    /**
     * 获取用户信息
     */
    public void userInfo() {
        EasyHttp.get(QuantizationHost.userInfoUrl)
                .baseUrl(BaseHost.QUANTIZATION_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int code = object.optInt("code");
                            if (code != BaseRequestCode.OK) {
                                if (code == BaseRequestCode.ERROR_401) {
                                    GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                                    uodateLogin(generalResult);
                                } else {
                                    BaseResponseError responseError = new Gson().fromJson(s, BaseResponseError.class);
                                    EventBusUtils.postErrorEvent(EvKey.userInfo, responseError.getCode(), responseError.getMessage(), responseError);
                                }
                            } else {
                                User user = BaseApplication.gson.fromJson(object.getString("data"), User.class);
                                EventBusUtils.postSuccessEvent(EvKey.userInfo, BaseRequestCode.OK, "", user);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        EventBusUtils.postErrorEvent(EvKey.userInfo, e.getCode(), e.getMessage());
                    }
                });
    }

}
