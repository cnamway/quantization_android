package com.spark.otcclient;

import com.spark.otcclient.base.OtcHost;
import com.spark.otcclient.pojo.ChatListResult;
import com.spark.otcclient.pojo.FindPageBean;
import com.zhouyou.http.EasyHttp;
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

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ChatClient extends BaseHttpClient {
    private static ChatClient sChatClient;

    private ChatClient() {
    }

    public static ChatClient getInstance() {
        synchronized (ChatClient.class) {
            if (sChatClient == null) {
                sChatClient = new ChatClient();
            }
        }
        return sChatClient;
    }

    /**
     * 获取历史聊天记录
     *
     * @param pageIndex
     * @param orderId
     */
    public void getHistoyList(int pageIndex, String orderId) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(20);
        findPageBean.setSortFields("sendTime_d");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();
        findPageBean.setQueryList(queryList);

        EasyHttp.post(OtcHost.getTradeChatListUrl + orderId)
                .baseUrl(BaseHost.OTC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                ChatListResult chatListResult = BaseApplication.gson.fromJson(s, ChatListResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.chatList, generalResult.getCode(), generalResult.getMessage(), chatListResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.chatList, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.chatList, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.chatList, e);
                    }
                });
    }

}
