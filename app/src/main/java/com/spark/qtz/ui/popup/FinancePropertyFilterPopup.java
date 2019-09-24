package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.spark.acclient.pojo.PropertyDetailsTypeResult;
import com.spark.qtz.R;
import com.spark.qtz.ui.flowlayout.FlowLayout;
import com.spark.qtz.ui.flowlayout.TagAdapter;
import com.spark.qtz.ui.flowlayout.TagFlowLayout;
import com.spark.qtz.ui.popup.impl.OnDatePickerListener;
import com.spark.qtz.ui.popup.impl.OnFinanceFilterListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class FinancePropertyFilterPopup extends PartShadowPopupView {
    @BindView(R.id.fl_ad_type)
    TagFlowLayout mFlAdType;
    @BindView(R.id.fl_status)
    TagFlowLayout mFlStatus;
    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;
    @BindView(R.id.btn_ensure)
    TextView mBtnEnsure;
    @BindView(R.id.minLimit)
    EditText mMinLimit;
    @BindView(R.id.maxLimit)
    EditText mMaxLimit;
    @BindView(R.id.llStartTime)
    LinearLayout llStartTime;
    @BindView(R.id.llEndTime)
    LinearLayout llEndTime;
    @BindView(R.id.startTime)
    TextView mStartTime;
    @BindView(R.id.endTime)
    TextView mEndTime;

    private TagAdapter mTagAdapterAdType, mTagAdapterStatus;
    private Context mContext;
    private List<PropertyDetailsTypeResult.DataBean> mainTypeList;
    private List<PropertyDetailsTypeResult.DataBean> subTypeList;
    private OnFinanceFilterListener mOnLcFilterListener;
    private String selectType, selectSubType, minLimit = "", maxLimit = "";
    private String startTime, endTime;

    public FinancePropertyFilterPopup(@NonNull Context context, List<PropertyDetailsTypeResult.DataBean> mainTypeList, List<PropertyDetailsTypeResult.DataBean> subTypeList, OnFinanceFilterListener onLcFilterListener) {
        super(context);
        this.mContext = context;
        this.mainTypeList = mainTypeList;
        this.subTypeList = subTypeList;
        this.mOnLcFilterListener = onLcFilterListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_finance_property_filter;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTagAdapterAdType = new TagAdapter<PropertyDetailsTypeResult.DataBean>(mainTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, PropertyDetailsTypeResult.DataBean s) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv, mFlAdType, false);
                tv.setText(s.getDescript());
                return tv;
            }
        };
        mFlAdType.setAdapter(mTagAdapterAdType);
        mFlAdType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                selectType = mainTypeList.get(position).getValue();
                return false;
            }
        });
        mTagAdapterAdType.setSelectedList(0);

        mTagAdapterStatus = new TagAdapter<PropertyDetailsTypeResult.DataBean>(subTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, PropertyDetailsTypeResult.DataBean s) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv, mFlStatus, false);
                tv.setText(s.getDescript());
                return tv;
            }
        };
        mFlStatus.setAdapter(mTagAdapterStatus);
        mFlStatus.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                selectSubType = subTypeList.get(position).getValue();
                return false;
            }
        });
        mTagAdapterStatus.setSelectedList(0);
    }

    @OnClick({R.id.btn_cancel
            , R.id.btn_ensure
            , R.id.llStartTime
            , R.id.llEndTime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ensure:
                if (TextUtils.isEmpty(mMinLimit.getText().toString().trim())) {
                    minLimit = "";
                } else {
                    minLimit = String.valueOf(Double.valueOf(mMinLimit.getText().toString().trim()));
                }

                if (TextUtils.isEmpty(mMaxLimit.getText().toString().trim())) {
                    maxLimit = "";
                } else {
                    maxLimit = String.valueOf(Double.valueOf(mMaxLimit.getText().toString().trim()));
                }
                mOnLcFilterListener.onSelect(selectType, selectSubType, minLimit, maxLimit, startTime, endTime);
                dismiss();
                break;
            case R.id.llStartTime:
                new XPopup.Builder(getContext())
                        .asCustom(new DatePickerPopup(getContext(), new OnDatePickerListener() {
                            @Override
                            public void onConfirm(String year, String month, String day) {
                                startTime = year + "-" + month + "-" + day + " 00:00:00";
                                mStartTime.setText(startTime);
                            }
                        }))
                        .show();
                break;
            case R.id.llEndTime:
                new XPopup.Builder(getContext())
                        .asCustom(new DatePickerPopup(getContext(), new OnDatePickerListener() {
                            @Override
                            public void onConfirm(String year, String month, String day) {
                                endTime = year + "-" + month + "-" + day + " 23:59:59";
                                mEndTime.setText(endTime);
                            }
                        }))
                        .show();
                break;
        }
    }
}
