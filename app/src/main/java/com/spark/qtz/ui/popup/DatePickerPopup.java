package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnDatePickerListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：日期选择器
 * 修订历史：
 * ================================================
 */
public class DatePickerPopup extends BottomPopupView {

    @BindView(R.id.mDatePicker)
    DatePicker mDatePicker;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.ensure)
    TextView ensure;

    private OnDatePickerListener onB2BBuyListener;

    public DatePickerPopup(@NonNull Context context, OnDatePickerListener onB2BBuyListener) {
        super(context);
        this.onB2BBuyListener = onB2BBuyListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_date_picker;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ensure,
            R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ensure:
                dismiss();
                String year = String.valueOf(mDatePicker.getYear());
                String month = String.valueOf((mDatePicker.getMonth() + 1) < 10 ? "0" + (mDatePicker.getMonth() + 1) : (mDatePicker.getMonth() + 1));
                String day = String.valueOf(mDatePicker.getDayOfMonth() < 10 ? "0" + mDatePicker.getDayOfMonth() : mDatePicker.getDayOfMonth());
                onB2BBuyListener.onConfirm(year, month, day);
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
