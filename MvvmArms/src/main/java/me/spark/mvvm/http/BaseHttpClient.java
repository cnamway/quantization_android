package me.spark.mvvm.http;

import com.zhouyou.http.exception.ApiException;

import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BaseHttpClient {
    /**
     * 异常
     *
     * @param origin
     * @param e
     */
    public void postException(String origin, Exception e) {
        EventBusUtils.postErrorEvent(origin, BaseRequestCode.ExceptionOfJson, e.getMessage());
    }

    public void postException(String origin, Exception e, int type) {
        EventBusUtils.postErrorEvent(origin, BaseRequestCode.ExceptionOfJson, e.getMessage(), type);
    }

    /**
     * 报错
     *
     * @param origin
     * @param e
     */
    public void postError(String origin, ApiException e) {
        LogUtils.e("postError",origin + "-" + e.getCode() + "-" + e.getMessage());
        EventBusUtils.postErrorEvent(origin, e.getCode(), e.getMessage());
    }

    public void postError(String origin, ApiException e, int type) {
        LogUtils.e("postError",origin + "-" + e.getCode() + "-" + e.getMessage());
        EventBusUtils.postErrorEvent(origin, e.getCode(), e.getMessage(), type);
    }

    /**
     * 登录失效
     */
    public void uodateLogin(GeneralResult generalResult) {
        LogUtils.e("uodateLogin","------------401 正在重新登录");
        EventBusUtils.postErrorEvent(EvKey.logout_fail_401, generalResult.getCode(), generalResult.getMessage(), generalResult);
    }
}
