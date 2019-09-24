package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinExtractSubmitBean;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.acclient.pojo.MerberWalletResult;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.popup.impl.OnCoinExtractListener;
import com.spark.qtz.ui.toast.Toasty;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.MathUtils;
import me.spark.mvvm.utils.StringUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoinExtractPopup extends BottomPopupView {
    @BindView(R.id.choose_coin_order)
    TextView mChooseCoinOrder;
    @BindView(R.id.coin_extract_scan)
    ImageView mCoinExtractScan;
    @BindView(R.id.coin_extract_address_choose)
    ImageView mCoinExtractAddressChoose;
    @BindView(R.id.coin_extract_number)
    EditText mCoinExtractNumber;
    @BindView(R.id.coin_extract_choose_all)
    TextView mCoinExtractChooseAll;
    @BindView(R.id.coin_extract_available)
    TextView mCoinExtractAvailable;
    @BindView(R.id.coin_extract_service_charge)
    TextView mCoinExtractServiceCharge;
    @BindView(R.id.coin_extract_notice)
    ImageView mCoinExtractNotice;
    @BindView(R.id.coin_extract_submit)
    TextView mCoinExtractSubmit;
    @BindView(R.id.coin_extract_cancel)
    TextView mCoinExtractCancel;
    @BindView(R.id.tip_1)
    TextView mTip1;
    @BindView(R.id.tip_2)
    TextView mTip2;
    @BindView(R.id.coin_extract_address)
    TextView mCoinExtractAddress;
    @BindView(R.id.coin_extract_tag)
    EditText mCoinExtractTag;

    private Context mContext;
    private MerberWalletResult mMerberWalletResult;
    private CoinSupportBean.DataBean mCoinSupportBean;
    private OnCoinExtractListener mOnCoinExtractListener;

    public CoinExtractPopup(@NonNull Context context, MerberWalletResult merberWalletResult, CoinSupportBean.DataBean coinSupportBean, OnCoinExtractListener onCoinExtractListener) {
        super(context);
        this.mContext = context;
        this.mMerberWalletResult = merberWalletResult;
        this.mCoinSupportBean = coinSupportBean;
        this.mOnCoinExtractListener = onCoinExtractListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_extract_coin;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mCoinExtractAvailable.setText(mContext.getString(R.string.available) + DfUtils.numberFormatDown(mMerberWalletResult.getData().getBalance(), 8) + " " + mMerberWalletResult.getData().getCoinId());
        mTip1.setText(mContext.getString(R.string.low_limit) + mCoinSupportBean.getMinWithdrawAmount() + " " + mCoinSupportBean.getCoinName());
        //1 - 固定金额 2 - 按比列
        if (mCoinSupportBean.getWithdrawFeeType() == 1) {
            mCoinExtractServiceCharge.setText(DfUtils.numberFormat(mCoinSupportBean.getWithdrawFee(), 8));
            mTip2.setVisibility(GONE);
            mCoinExtractNotice.setVisibility(GONE);
        }else {
            mTip2.setText(mContext.getString(R.string.fee) + mCoinSupportBean.getMinWithdrawFee() + " - " + mCoinSupportBean.getMaxWithdrawFee() + " " + mCoinSupportBean.getCoinName());
        }
        mCoinExtractNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(s.toString())) {
                    mCoinExtractServiceCharge.setText(mCoinSupportBean.getWithdrawFeeType() == 1 ?
                            DfUtils.numberFormat(mCoinSupportBean.getWithdrawFee(), 8) :
                            MathUtils.getBigDecimalMultiplyWithScale(s.toString(),String.valueOf(mCoinSupportBean.getWithdrawFee()),8));
                } else {
                    mCoinExtractServiceCharge.setText(mCoinSupportBean.getWithdrawFeeType() == 1 ?
                            DfUtils.numberFormatDown(mCoinSupportBean.getWithdrawFee(), 8) :
                            "0");
                }
            }
        });
    }

    @OnClick({R.id.coin_extract_notice,
            R.id.coin_extract_cancel,
            R.id.coin_extract_address_choose,
            R.id.coin_extract_submit,
            R.id.coin_extract_choose_all,
            R.id.choose_coin_order})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.coin_extract_notice:
                new XPopup.Builder(mContext)
                        .atView(view)
                        .asCustom(new CoinExtractNoticePopup(mContext, mCoinSupportBean))
                        .show();
                break;
            case R.id.coin_extract_cancel:
                dismiss();
                break;
            case R.id.coin_extract_address_choose:
                FinanceClient.getInstance().getWithdrawAddress(mMerberWalletResult.getData().getCoinId());
                break;
            case R.id.coin_extract_submit:
                if (TextUtils.isEmpty(mCoinExtractAddress.getText().toString().trim())) {
                    Toasty.showError(mContext.getString(R.string.mention_address_cannot_empty));
                    return;
                }
                if (TextUtils.isEmpty(mCoinExtractTag.getText().toString().trim())) {
                    Toasty.showError(mContext.getString(R.string.label_cannot_empty));
                    return;
                }
                if (TextUtils.isEmpty(mCoinExtractNumber.getText().toString().trim())) {
                    Toasty.showError(mContext.getString(R.string.number_cannot_empty));
                    return;
                }

                CoinExtractSubmitBean coinExtractSubmitBean = new CoinExtractSubmitBean();
                coinExtractSubmitBean.setAddress(mCoinExtractAddress.getText().toString().trim());
                coinExtractSubmitBean.setTag(mCoinExtractTag.getText().toString().trim());
                coinExtractSubmitBean.setAmount(String.valueOf(Double.valueOf(mCoinExtractNumber.getText().toString().trim())));
                coinExtractSubmitBean.setCoinName(mCoinSupportBean.getCoinName());
                coinExtractSubmitBean.setServiceCharge(mCoinExtractServiceCharge.getText().toString());

                mOnCoinExtractListener.onReceiveCoinExtract(coinExtractSubmitBean);
                dismiss();
                break;
            case R.id.coin_extract_choose_all:
                mCoinExtractNumber.setText(DfUtils.numberFormatDown(mMerberWalletResult.getData().getBalance(), 8));
                break;
            case R.id.choose_coin_order:
                dismiss();
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_RECORD)
                        .withInt("recordType", 1)
                        .navigation();
                break;
        }
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(mContext) * .85f);
    }

    public void updateAddress(String address) {
        requstFocus();
        mCoinExtractAddress.setText(address);
    }


    private void requstFocus() {
        mCoinExtractNumber.setFocusable(true);
        mCoinExtractNumber.requestFocus();
        InputMethodManager mSoftInputManager = (InputMethodManager) mCoinExtractNumber.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mSoftInputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }
}

