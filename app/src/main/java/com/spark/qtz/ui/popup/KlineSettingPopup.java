package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.AttachPopupView;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnKlineSettingListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class KlineSettingPopup extends AttachPopupView {
    @BindView(R.id.tab_primary_ma)
    TextView mTabPrimaryMa;
    @BindView(R.id.tab_primary_boll)
    TextView mTabPrimaryBoll;
    @BindView(R.id.tab_primary_hide)
    TextView mTabPrimaryHide;
    @BindView(R.id.tab_secondary_macd)
    TextView mTabSecondaryMacd;
    @BindView(R.id.tab_secondary_kdj)
    TextView mTabSecondaryKdj;
    @BindView(R.id.tab_secondary_rsi)
    TextView mTabSecondaryRsi;
    @BindView(R.id.tab_secondary_hide)
    TextView mTabSecondaryHide;

    private OnKlineSettingListener mOnKlineSettingListener;

    public KlineSettingPopup(@NonNull Context context, OnKlineSettingListener mOnKlineSettingListener) {
        super(context);
        this.mOnKlineSettingListener = mOnKlineSettingListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_kline_setting;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        mTabPrimaryMa.setSelected(true);
        mTabSecondaryKdj.setSelected(true);
    }

    @OnClick({R.id.tab_primary_ma,
            R.id.tab_primary_boll,
            R.id.tab_primary_hide,
            R.id.tab_secondary_macd,
            R.id.tab_secondary_kdj,
            R.id.tab_secondary_rsi,
            R.id.tab_secondary_hide})
    public void onClick(View view) {

        switch (view.getId()) {
            //主图 - MA
            case R.id.tab_primary_ma:
                hideAllStatue(0);
                view.setSelected(true);
                mOnKlineSettingListener.onSelectSetting(0, 0);
                break;
            //主图 - BOLL
            case R.id.tab_primary_boll:
                hideAllStatue(0);
                view.setSelected(true);
                mOnKlineSettingListener.onSelectSetting(0, 1);
                break;
            //主图 - 隐藏
            case R.id.tab_primary_hide:
                hideAllStatue(0);
                view.setSelected(true);
                mOnKlineSettingListener.onSelectSetting(0, 2);
                break;
            //副图 - MACD
            case R.id.tab_secondary_macd:
                hideAllStatue(1);
                view.setSelected(true);
                mOnKlineSettingListener.onSelectSetting(1, 0);
                break;
            //副图 - KDJ
            case R.id.tab_secondary_kdj:
                hideAllStatue(1);
                view.setSelected(true);
                mOnKlineSettingListener.onSelectSetting(1, 1);
                break;
            //副图 - RSI
            case R.id.tab_secondary_rsi:
                hideAllStatue(1);
                view.setSelected(true);
                mOnKlineSettingListener.onSelectSetting(1, 2);
                break;
            //副图 - 隐藏
            case R.id.tab_secondary_hide:
                hideAllStatue(1);
                view.setSelected(true);
                mOnKlineSettingListener.onSelectSetting(1, 3);
                break;
        }
    }

    private void hideAllStatue(int tabType) {
        switch (tabType) {
            case 0:
                mTabPrimaryMa.setSelected(false);
                mTabPrimaryBoll.setSelected(false);
                mTabPrimaryHide.setSelected(false);
                break;
            case 1:
                mTabSecondaryMacd.setSelected(false);
                mTabSecondaryKdj.setSelected(false);
                mTabSecondaryRsi.setSelected(false);
                mTabSecondaryHide.setSelected(false);
                break;
        }
    }

}
