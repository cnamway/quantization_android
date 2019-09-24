package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.modulecfd.CfdClient;
import com.example.modulecfd.pojo.CfdOrderPostBean;
import com.example.modulecfd.pojo.CfdPositionResult;
import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.App;
import com.spark.qtz.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.LogUtils;
import me.spark.mvvm.utils.SpanUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PositionCloseOnekeyPopup extends BottomPopupView {
    @BindView(R.id.profit_num)
    TextView mProfitNum;
    @BindView(R.id.loss_num)
    TextView mLossNum;
    @BindView(R.id.confirm)
    TextView mConfirm;
    @BindView(R.id.cancel)
    TextView mCancel;

    private Context mContext;
    private int mProfit, mLoss;
    private List<CfdPositionResult.DataBean> mDataBeanList;

    public PositionCloseOnekeyPopup(@NonNull Context context, int profit, int loss, List<CfdPositionResult.DataBean> dataBeanList) {
        super(context);
        this.mContext = context;
        this.mProfit = profit;
        this.mLoss = loss;
        this.mDataBeanList = dataBeanList;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_postion_close_one_key;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        CharSequence profitText = new SpanUtils()
                .append(String.valueOf(mProfit))
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.green))
                .append(App.getInstance().getString(R.string.str_count))
                .create();
        CharSequence lossText = new SpanUtils()
                .append(String.valueOf(mLoss))
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.red))
                .append(App.getInstance().getString(R.string.str_count))
                .create();
        mProfitNum.setText(profitText);
        mLossNum.setText(lossText);
    }

    @OnClick({R.id.confirm,
            R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                CfdOrderPostBean cfdOrderPostBean = new CfdOrderPostBean();
                StringBuffer stringBuffer = new StringBuffer();
                for (CfdPositionResult.DataBean dataBean : mDataBeanList) {
                    stringBuffer.append(",").append(dataBean.getId());
                }
                LogUtils.e("ccs",stringBuffer.toString().substring(1));

                cfdOrderPostBean.setCloseType(2);
                cfdOrderPostBean.setId(stringBuffer.toString().substring(1));
                CfdClient.getInstance().orderClose(cfdOrderPostBean);
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
