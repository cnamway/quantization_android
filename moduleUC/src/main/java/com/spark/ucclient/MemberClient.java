package com.spark.ucclient;


import com.google.gson.Gson;
import com.spark.ucclient.base.UcHost;
import com.spark.ucclient.pojo.CommissionRecordResult;
import com.spark.ucclient.pojo.FindPageBean;
import com.spark.ucclient.pojo.InviteFriendResult;
import com.spark.ucclient.pojo.LockProoertDetailsResult;
import com.spark.ucclient.pojo.RecordPageResult;
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
import me.spark.mvvm.base.EvKey;
import me.spark.mvvm.http.BaseHttpClient;
import me.spark.mvvm.http.GeneralResult;
import me.spark.mvvm.pojo.User;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;

/**
 * 用户模块
 */
public class MemberClient extends BaseHttpClient {

    private static MemberClient ucClient;

    private MemberClient() {
    }

    public static MemberClient getInstance() {
        if (ucClient == null) {
            synchronized (MemberClient.class) {
                if (ucClient == null) {
                    ucClient = new MemberClient();
                }
            }
        }
        return ucClient;
    }


    /**
     * 获取用户信息
     */
    public void userInfo() {
        EasyHttp.post(UcHost.userInfoUrl)
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            LogUtils.e("userInfo", s);
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

    /**
     * 邀请好友详情
     */
    public void getInviteDetails() {
        EasyHttp.get(UcHost.inviteDetilsUrl)
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("getInviteDetails", s);
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                InviteFriendResult inviteFriendResult = BaseApplication.gson.fromJson(s, InviteFriendResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.inviteDetails, generalResult.getCode(), generalResult.getMessage(), inviteFriendResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.inviteDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.inviteDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.inviteDetails, e);
                    }
                });
    }


    public void getCommissionRecord(int page) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(page);
        findPageBean.setPageSize(10);


        EasyHttp.post(UcHost.commissionRecordUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                CommissionRecordResult commissionRecordResult = BaseApplication.gson.fromJson(s, CommissionRecordResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.commissionRecord, generalResult.getCode(), generalResult.getMessage(), commissionRecordResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.commissionRecord, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.commissionRecord, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.commissionRecord, e);
                    }
                });
    }

    public void getRecordPage(int page) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(page);
        findPageBean.setPageSize(10);
        findPageBean.setSortFields("createTime_d");

        EasyHttp.post(UcHost.recordPageUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                RecordPageResult recordPageResult = BaseApplication.gson.fromJson(s, RecordPageResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.recordPage, generalResult.getCode(), generalResult.getMessage(), recordPageResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.recordPage, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.recordPage, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.recordPage, e);
                    }
                });
    }

    /**
     * 锁仓记录
     *
     * @param pageIndex
     * @param coinName
     */
    public void getProperyLockDetails(int pageIndex, final String coinName) {
        FindPageBean findPageBean = new FindPageBean();
        findPageBean.setPageIndex(pageIndex);
        findPageBean.setPageSize(10);
        findPageBean.setSortFields("createTime_d");

        List<FindPageBean.QueryListBean> queryList = new ArrayList<>();

        FindPageBean.QueryListBean queryListBean = new FindPageBean.QueryListBean();
        queryListBean.setJoin("and");
        queryListBean.setKey("coinId");
        queryListBean.setOper("=");
        queryListBean.setValue(coinName);
        queryList.add(queryListBean);

        findPageBean.setQueryList(queryList);

        EasyHttp.post(UcHost.lockPositionRecordUrl)
                .baseUrl(BaseHost.UC_HOST)
                .headers("Content-Type", "application/json")
                .upJson(BaseApplication.gson.toJson(findPageBean))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                LockProoertDetailsResult lockProoertDetailsResult = BaseApplication.gson.fromJson(s, LockProoertDetailsResult.class);
                                EventBusUtils.postSuccessEvent(EvKey.propertyLockDetails, generalResult.getCode(), generalResult.getMessage(), lockProoertDetailsResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.propertyLockDetails, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.propertyLockDetails, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.propertyLockDetails, e);
                    }
                });
    }

    /**
     * 修改用户名
     *
     * @param nickName
     */
    public void modifyNickName(String nickName) {
        EasyHttp.get(UcHost.updateUserNameUrl)
                .baseUrl(BaseHost.UC_HOST)
                .params("username", nickName)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.modifyUserName, generalResult.getCode(), generalResult.getMessage());
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.modifyUserName, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.modifyUserName, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.modifyUserName, e);
                    }
                });
    }

    /**
     * 用户名修改校验
     */
    public void userNameChangeCheck() {
        EasyHttp.get(UcHost.usernameChangeCheck)
                .baseUrl(BaseHost.UC_HOST)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            GeneralResult generalResult = BaseApplication.gson.fromJson(s, GeneralResult.class);
                            if (generalResult.getCode() == BaseRequestCode.OK) {
                                EventBusUtils.postSuccessEvent(EvKey.usernameChangeCheck, generalResult.getCode(), generalResult.getMessage(), generalResult);
                            } else {
                                if (generalResult.getCode() == BaseRequestCode.ERROR_401) {
                                    uodateLogin(generalResult);
                                } else {
                                    EventBusUtils.postErrorEvent(EvKey.usernameChangeCheck, generalResult.getCode(), generalResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            postException(EvKey.usernameChangeCheck, e);
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        postError(EvKey.usernameChangeCheck, e);
                    }
                });
    }

}
