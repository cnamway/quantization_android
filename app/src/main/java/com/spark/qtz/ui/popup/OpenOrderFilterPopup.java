package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.spark.qtz.R;
import com.spark.qtz.ui.flowlayout.FlowLayout;
import com.spark.qtz.ui.flowlayout.TagAdapter;
import com.spark.qtz.ui.flowlayout.TagFlowLayout;
import com.spark.qtz.ui.popup.impl.OnOpenOrderFilterListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.base.Constant;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OpenOrderFilterPopup extends PartShadowPopupView {
    @BindView(R.id.symbol)
    TextView mSymbol;
    @BindView(R.id.fl_status)
    TagFlowLayout mFlStatus;
    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;
    @BindView(R.id.btn_ensure)
    TextView mBtnEnsure;

    private OnOpenOrderFilterListener mOnOpenOrderFilterListener;

    private String[] mStatus;
    private TagAdapter mTagAdapterStatus;
    private String symbol, selectStatus = "";
    private Context mContext;

    public OpenOrderFilterPopup(@NonNull Context context, String symbol, OnOpenOrderFilterListener onOpenOrderFilterListener) {
        super(context);
        this.symbol = symbol;
        this.mContext = context;
        this.mOnOpenOrderFilterListener = onOpenOrderFilterListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_open_order_filter;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mStatus = getResources().getStringArray(R.array.open_order_filter_tab);

        mSymbol.setText(symbol);
        mTagAdapterStatus = new TagAdapter<String>(mStatus) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv,
                        mFlStatus, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlStatus.setAdapter(mTagAdapterStatus);
        mFlStatus.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                switch (position) {
                    case 0:
                        selectStatus = "";
                        break;
                    case 1:
                        selectStatus = "0";
                        break;
                    case 2:
                        selectStatus = "1";
                        break;
                }
                return false;
            }
        });
        mTagAdapterStatus.setSelectedList(0);
    }

    @OnClick({R.id.btn_cancel,
            R.id.btn_ensure,
            R.id.symbol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.symbol:
                new XPopup.Builder(mContext)
                        .maxHeight((int) (XPopupUtils.getWindowHeight(mContext) * .85f))
                        .asBottomList(mContext.getString(R.string.coin_type_hint), Constant.allThumbSymbol,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        mSymbol.setText(text);
                                    }
                                })
                        .show();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ensure:
                mOnOpenOrderFilterListener.onLcSelect(mSymbol.getText().toString(), selectStatus);
                dismiss();
                break;
        }
    }
}
