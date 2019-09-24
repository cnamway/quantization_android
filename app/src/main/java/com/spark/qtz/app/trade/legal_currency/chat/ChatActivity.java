package com.spark.qtz.app.trade.legal_currency.chat;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.App;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.app.trade.legal_currency.adapter.ChatAdapter;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.ActivityChatBinding;
import com.spark.qtz.util.StatueBarUtils;
import com.spark.otcclient.pojo.LcOrderResult;
import com.spark.wsclient.base.WsCMD;
import com.spark.wsclient.pojo.ChatBean;
import com.spark.wsclient.utils.WsConstant;
import com.spark.wsclient.utils.WsUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.spark.mvvm.base.BaseActivity;
import me.spark.mvvm.http.impl.OnRequestListener;
import me.spark.mvvm.utils.EventBusUtils;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.WebSocketRequest;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/19
 * 描    述：
 * 修订历史：
 * ================================================
 */

@Route(path = ARouterPath.ACTIVITY_TRADE_LC_CHAT)
public class ChatActivity extends BaseActivity<ActivityChatBinding, ChatViewModel> {
    @Autowired(name = "orderDetails")
    LcOrderResult.DataBean.RecordsBean orderDetailsBean;

    private TitleBean mTitleModel;
    private ChatAdapter adapter;
    private List<ChatBean> chatEntities = new ArrayList<>();
    private int pageIndex = 0;
    private LinearLayoutManager layoutManager;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_chat;
    }

    @Override
    public int initVariableId() {
        return BR.chatViewModel;
    }

    @Override
    public void initView() {
        StatueBarUtils.setStatusBarLightMode(this, true);
        //StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.chatTitle.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleName(orderDetailsBean.getTradeToUsername());
        mTitleModel.setShowTitleLine(true);
        binding.chatTitle.titleLeftImg.setImageDrawable(getResources().getDrawable(R.drawable.svg_cancel));
        mTitleModel.setShowRightTV(true);
        mTitleModel.setRightTV(App.getInstance().getString(R.string.str_order_detail));
        binding.chatTitle.setViewTitle(mTitleModel);
        setTitleListener(binding.chatTitle.titleRootLeft, binding.chatTitle.titleRootRight);

        //
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(false);
        binding.rvChat.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(ChatActivity.this, chatEntities, App.getInstance().getCurrentUser().getId());
        binding.rvChat.setAdapter(adapter);

        //下拉刷新
        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.base));
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHistoyList();
            }
        });

    }

    @Override
    protected void onTitleRightClick() {
        super.onTitleRightClick();
        finish();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.chatContent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                sendMsg(s);
            }
        });
        viewModel.uc.chatReceiveObservable.observe(this, new Observer<ChatBean>() {
            @Override
            public void onChanged(@Nullable ChatBean chatBean) {
                if (chatBean.getOrderId().equals(orderDetailsBean.getOrderSn())) {
                    updateChatView(chatBean);
                }
            }
        });
        viewModel.uc.mIsRefresh.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    pageIndex = 0;
                    getHistoyList();
                }
            }
        });
    }

    /**
     * 发送聊天
     *
     * @param msg
     */
    private void sendMsg(String msg) {
        ChatBean mChatSendBean = new ChatBean();
        mChatSendBean.setContent(msg);
        mChatSendBean.setOrderId(orderDetailsBean.getOrderSn());
        String myId = App.getInstance().getCurrentUser().getId();
        mChatSendBean.setUidFrom(myId);
        if (!myId.equals(String.valueOf(orderDetailsBean.getCustomerId()))) {
            mChatSendBean.setUidTo(String.valueOf(orderDetailsBean.getCustomerId()));
        } else {
            mChatSendBean.setUidTo(String.valueOf(orderDetailsBean.getMemberId()));
        }
        mChatSendBean.setNameFrom(App.getInstance().getCurrentUser().getUsername());
        mChatSendBean.setNameTo(orderDetailsBean.getTradeToUsername());
        mChatSendBean.setMessageType("1");
        mChatSendBean.setFromAvatar(App.getInstance().getCurrentUser().getAvatar());
        mChatSendBean.setSendTime(String.valueOf(System.currentTimeMillis()));
        mChatSendBean.setType(ChatBean.Type.RIGHT);
        updateChatView(mChatSendBean);

        WebSocketRequest mWebSocketRequest = new WebSocketRequest();
        mWebSocketRequest.setCode(WsConstant.CODE_CHAT);
        mWebSocketRequest.setCmd(WsCMD.SEND_CHAT);
        mWebSocketRequest.setBody(App.gson.toJson(WsUtils.setChatJsonMap(mChatSendBean)).getBytes());
        EventBusUtils.postEvent(mWebSocketRequest);
    }

    /**
     * 刷新聊天视图
     *
     * @param chatBean
     */
    private void updateChatView(ChatBean chatBean) {
        chatEntities.add(chatBean);
        adapter.notifyDataSetChanged();
        binding.rvChat.smoothScrollToPosition(chatEntities.size() - 1);
    }

    @Override
    public void initData() {
        super.initData();
        getHistoyList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (chatEntities != null && chatEntities.size() > 0) {
            pageIndex = 0;
            getHistoyList();
        }
    }

    /**
     * 获取历史聊天记录
     */
    private void getHistoyList() {
        binding.swipeLayout.setRefreshing(true);
        viewModel.initViewDate(++pageIndex, orderDetailsBean.getOrderSn(), new OnRequestListener<List<ChatBean>>() {
            @Override
            public void onSuccess(List<ChatBean> entityList) {
                binding.swipeLayout.setRefreshing(false);
                if (entityList == null) return;
                if (pageIndex == 1) chatEntities.clear();
                Collections.reverse(chatEntities);
                chatEntities.addAll(entityList);
                Collections.reverse(chatEntities);
                adapter.notifyDataSetChanged();
                if (pageIndex == 1) binding.rvChat.smoothScrollToPosition(chatEntities.size() - 1);
                else layoutManager.scrollToPosition(entityList.size() + 3);
            }

            @Override
            public void onFail(String message) {
                binding.swipeLayout.setRefreshing(false);
            }
        });
    }

}
