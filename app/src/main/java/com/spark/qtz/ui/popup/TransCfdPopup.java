package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.core.BottomPopupView;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinTransBean;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.PointLengthFilter;
import com.spark.qtz.ui.toast.Toasty;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.DfUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/4
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class TransCfdPopup extends BottomPopupView {
    @BindView(R.id.trans_order)
    TextView mTransOrder;
    @BindView(R.id.trans_from_tv)
    TextView mTransFromTv;
    @BindView(R.id.trans_from_to)
    TextView mTransFromTo;
    @BindView(R.id.trans_reverse)
    LinearLayout mTransReverse;
    @BindView(R.id.trans_num)
    EditText mTransNum;
    @BindView(R.id.trans_all)
    TextView mTransAll;
    @BindView(R.id.trans_num_available)
    TextView mTransNumAvailable;
    @BindView(R.id.btn_trans)
    TextView mBtnTrans;
    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;

    private int from = 0, to = 1;//0 - 合约账户 1 - 币币账户
    private String mBalanceCfd, mBalanceSpot;
    private Context mContext;
    private PointLengthFilter mNumFilter;

    public TransCfdPopup(@NonNull Context context, String balanceSpot, String balanceCfd) {
        super(context);
        this.mContext = context;
        this.mBalanceSpot = balanceSpot;
        this.mBalanceCfd = balanceCfd;

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_trans_cfd;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mTransNumAvailable.setText(mContext.getString(R.string.available) + DfUtils.numberFormat(mBalanceCfd, 8) + " USDT");
        mNumFilter = new PointLengthFilter(16);
        mTransNum.setFilters(new InputFilter[]{mNumFilter});
    }

    @OnClick({R.id.trans_order,
            R.id.trans_reverse,
            R.id.trans_all,
            R.id.btn_trans,
            R.id.btn_cancel})
    public void OnClick(View view) {
        switch (view.getId()) {
            //记录
            case R.id.trans_order:
                dismiss();
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_RECORD)
                        .withInt("coinType", 2)
                        .withInt("recordType", 2)
                        .navigation();
                break;
            //翻转
            case R.id.trans_reverse:
                int temp = from;
                from = to;
                to = temp;
                mTransFromTv.setText(from == 0 ? mContext.getString(R.string.cfd_account) : mContext.getString(R.string.b2b_account));
                mTransFromTo.setText(to == 1 ? mContext.getString(R.string.b2b_account) : mContext.getString(R.string.cfd_account));
                mTransNumAvailable.setText(mContext.getString(R.string.available) + DfUtils.numberFormat((from == 0 ? mBalanceCfd : mBalanceSpot), 8) + " USDT");
                break;
            //全部提取
            case R.id.trans_all:
                mTransNum.setText(from == 0 ? mBalanceCfd : mBalanceSpot);
                break;
            //划转
            case R.id.btn_trans:
                if (TextUtils.isEmpty(mTransNum.getText().toString().trim())) {
                    Toasty.showError(mContext.getString(R.string.trans_number_hint));
                    return;
                }

                if (Double.valueOf(mTransNum.getText().toString().trim()) == 0) {
                    Toasty.showError(mContext.getString(R.string.trans_cannot_0));
                    return;
                }

                if (Double.valueOf(mTransNum.getText().toString().trim()) > Double.valueOf(from == 0 ? mBalanceCfd : mBalanceSpot)) {
                    Toasty.showError(mContext.getString(R.string.trans_correct_number_hint));
                    return;
                }

                CoinTransBean coinTransBean = new CoinTransBean();
                coinTransBean.setAmount(Double.valueOf(mTransNum.getText().toString().trim()));
                coinTransBean.setCoinName("USDT");
                coinTransBean.setFrom(from == 1 ? "SPOT" : "CFD");
                coinTransBean.setTo(to == 0 ? "CFD" : "SPOT");
                FinanceClient.getInstance().coinCFfdTransfer(coinTransBean);
                break;
            //取消
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
}

