package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.spark.qtz.R;
import com.spark.qtz.ui.flowlayout.FlowLayout;
import com.spark.qtz.ui.flowlayout.TagAdapter;
import com.spark.qtz.ui.flowlayout.TagFlowLayout;
import com.spark.qtz.ui.popup.impl.OnPositionChooseListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ContractSymbolSelectPopup extends PartShadowPopupView {
    @BindView(R.id.fl_symbol)
    TagFlowLayout mFlSymbol;
    //币种切换
    private String[] mSymbol;
    private OnPositionChooseListener mOnPositionChooseListener;
    private TagAdapter mTagAdapterStatus;
    private Context mContext;

    public ContractSymbolSelectPopup(@NonNull Context context, String[] allSymbol, OnPositionChooseListener onPositionChooseListener) {
        super(context);
        this.mContext = context;
        mSymbol = allSymbol;
        mOnPositionChooseListener = onPositionChooseListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_contract_symbol_select;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTagAdapterStatus = new TagAdapter<String>(mSymbol) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv,
                        mFlSymbol, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlSymbol.setAdapter(mTagAdapterStatus);
        mFlSymbol.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mOnPositionChooseListener.onChoosePosition(position);
                dismiss();
                return false;
            }
        });
        mTagAdapterStatus.setSelectedList(0);
    }
}
