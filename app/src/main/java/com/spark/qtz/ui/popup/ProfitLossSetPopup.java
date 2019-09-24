package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.modulecfd.CfdClient;
import com.example.modulecfd.pojo.CfdCommissionResult;
import com.example.modulecfd.pojo.CfdPositionResult;
import com.example.modulecfd.pojo.CfdProfitLossBean;
import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.R;
import com.spark.qtz.ui.PointLengthFilter;
import com.spark.qtz.ui.toast.Toasty;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ProfitLossSetPopup extends BottomPopupView {
    @BindView(R.id.profitSubtract)
    ImageView mProfitSubtract;
    @BindView(R.id.profit)
    EditText mProfit;
    @BindView(R.id.profitAdd)
    ImageView mProfitAdd;
    @BindView(R.id.llPrice)
    LinearLayout mLlPrice;
    @BindView(R.id.lossSubtract)
    ImageView mLossSubtract;
    @BindView(R.id.loss)
    EditText mLoss;
    @BindView(R.id.lossAdd)
    ImageView mLossAdd;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.confirm)
    TextView mConfirm;
    @BindView(R.id.cancel)
    TextView mCancel;
    private PointLengthFilter mPriceFilter;

    private Context mContext;
    private CfdPositionResult.DataBean mDataBean;
    private CfdCommissionResult.DataBean.RecordsBean mRecordsBean;

    private int mType;//0 - 平仓单  1 - 委托单

    public ProfitLossSetPopup(@NonNull Context context, int type, CfdPositionResult.DataBean dataBean) {
        super(context);
        this.mType = type;
        this.mContext = context;
        this.mDataBean = dataBean;
    }

    public ProfitLossSetPopup(@NonNull Context context, int type, CfdCommissionResult.DataBean.RecordsBean recordsBean) {
        super(context);
        this.mType = type;
        this.mContext = context;
        this.mRecordsBean = recordsBean;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_profit_loss_set;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mPriceFilter = new PointLengthFilter(8);
        mProfit.setFilters(new InputFilter[]{mPriceFilter});
        mLoss.setFilters(new InputFilter[]{mPriceFilter});

        if (mType == 0) {
            mProfit.setText(DfUtils.numberFormat(mDataBean.getStopProfitPrice(), 4));
            mLoss.setText(DfUtils.numberFormat(mDataBean.getStopLossPrice(), 4));
            mContent.setText(mDataBean.isSideUp() ? mContext.getString(R.string.profitLossSettingUp) : mContext.getString(R.string.profitLossSettingDown));
        } else {
            mProfit.setText(DfUtils.numberFormat(mRecordsBean.getStopProfitPrice(), 4));
            mLoss.setText(DfUtils.numberFormat(mRecordsBean.getStopLossPrice(), 4));
            mContent.setText(mRecordsBean.isSideUp() ? mContext.getString(R.string.profitLossSettingCancelUp) : mContext.getString(R.string.profitLossSettingDown));
        }

    }

    @OnClick({R.id.profitSubtract,
            R.id.profitAdd,
            R.id.lossSubtract,
            R.id.lossAdd,
            R.id.confirm,
            R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profitSubtract:
                if (!StringUtils.isEmpty(mProfit.getText().toString().trim())
                        && Double.valueOf(mProfit.getText().toString().trim()) >= 0) {
                    mProfit.setText(String.valueOf(new BigDecimal(mProfit.getText().toString().trim()).subtract(new BigDecimal(1))));
                }
                break;
            case R.id.profitAdd:
                if (!StringUtils.isEmpty(mProfit.getText().toString().trim())
                        && Double.valueOf(mProfit.getText().toString().trim()) >= 0) {
                    mProfit.setText(String.valueOf(new BigDecimal(mProfit.getText().toString().trim()).add(new BigDecimal(1))));
                }
                break;
            case R.id.lossSubtract:
                if (!StringUtils.isEmpty(mLoss.getText().toString().trim())
                        && Double.valueOf(mLoss.getText().toString().trim()) >= 0) {
                    mLoss.setText(String.valueOf(new BigDecimal(mLoss.getText().toString().trim()).subtract(new BigDecimal(1))));
                }
                break;
            case R.id.lossAdd:
                if (!StringUtils.isEmpty(mLoss.getText().toString().trim())
                        && Double.valueOf(mLoss.getText().toString().trim()) >= 0) {
                    mLoss.setText(String.valueOf(new BigDecimal(mLoss.getText().toString().trim()).add(new BigDecimal(1))));
                }
                break;
            case R.id.confirm:
                double profitValue = StringUtils.isEmpty(mProfit.getText().toString().trim()) ? 0 : Double.valueOf(mProfit.getText().toString().trim());
                double lossValue = StringUtils.isEmpty(mLoss.getText().toString().trim()) ? 0 : Double.valueOf(mLoss.getText().toString().trim());
                double _price = mType == 0 ? mDataBean.getPrice() : mRecordsBean.getPrice();
                double openPrice = mType == 0 ? mDataBean.getCurrentPrice() : mRecordsBean.getCurrentPrice();

                boolean isSideUp = (mType == 0 ? mDataBean.isSideUp() : mRecordsBean.isSideUp());

                if (isSideUp) {
                    if (profitValue != 0) {
                        if (profitValue < (_price > openPrice ? _price : openPrice)) {
                            Toasty.showError(mContext.getString(R.string.profit_loss_set_tip1));
                            return;
                        } else if (lossValue > (_price < openPrice ? _price : openPrice)) {
                            Toasty.showError(mContext.getString(R.string.profit_loss_set_tip2));
                            return;
                        }
                    }
                } else {
                    if (lossValue != 0) {
                        if (profitValue > (_price < openPrice ? _price : openPrice)) {
                            Toasty.showError(mContext.getString(R.string.profit_loss_set_tip3));
                            return;
                        } else if (lossValue < (_price > openPrice ? _price : openPrice)) {
                            Toasty.showError(mContext.getString(R.string.profit_loss_set_tip4));
                            return;
                        }
                    }
                }

                CfdProfitLossBean cfdProfitLossBean = new CfdProfitLossBean();
                if (mType == 0) {
                    cfdProfitLossBean.setPositionId(mDataBean.getId());
                } else {
                    cfdProfitLossBean.setOrderId(mRecordsBean.getOrderId());
                }
                cfdProfitLossBean.setStopProfitPrice(profitValue);
                cfdProfitLossBean.setStopLossPrice(lossValue);
                CfdClient.getInstance().orderProfitLossSet(mType, cfdProfitLossBean);
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private String compare(double a, double b) {
        return a > b ? String.valueOf(a) : String.valueOf(b);
    }
}
