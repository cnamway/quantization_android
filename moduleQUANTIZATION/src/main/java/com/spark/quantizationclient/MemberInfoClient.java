package com.spark.quantizationclient;

import com.google.gson.Gson;
import com.spark.quantizationclient.base.QuantizationHost;
import com.spark.quantizationclient.pojo.FindPageBean;
import com.spark.quantizationclient.pojo.IndexAccountResult;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseHost;
import me.spark.mvvm.base.BaseRequestCode;
import me.spark.mvvm.base.BaseResponseError;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * 用户模块
 */
public class MemberInfoClient extends BaseHttpClient {

    private static MemberInfoClient ucClient;

    private MemberInfoClient() {
    }

    public static MemberInfoClient getInstance() {
        if (ucClient == null) {
            synchronized (MemberInfoClient.class) {
                ucClient = new MemberInfoClient();
            }
        }
        return ucClient;
    }

    /**
     * 账户监控
     *
     * @param pageIndex
     * @param id
     */
    public void getList(int pageIndex, final String id) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(Constant.COMMON_PAGE_SIZE);
        findPageBean.setSortFields("updateTime_d");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        if (StringUtils.isNotEmpty(id)){
            FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
            queryListBean.setJoin("and");
            queryListBean.setKey("id");
            queryListBean.setOper(":");
            queryListBean.setValue(id);
            queryList.add(queryListBean);
        }

        findPageBean.setQueryList(queryList);

        EasyHttp.post(QuantizationHost.memberListUrl)
                .baseUrl(BaseHost.QUANTIZATION_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                IndexAccountResult result = BaseApplication.gson.fromJson(s, IndexAccountResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.memberListUrl, generalResult.getCode(), generalResult.getMessage(), result);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.memberListUrl, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.memberListUrl, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.memberListUrl, e);
                    }
                });
    }


}
