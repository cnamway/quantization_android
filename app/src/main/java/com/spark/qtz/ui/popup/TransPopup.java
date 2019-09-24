package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.spark.acclient.FinanceClient;
import com.spark.acclient.pojo.CoinSupportBean;
import com.spark.acclient.pojo.CoinTransBean;
import com.spark.acclient.pojo.MerberWalletResult;
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
public class TransPopup extends BottomPopupView {
    @BindView(R.id.trans_order)
    TextView mTransOrder;
    @BindView(R.id.trans_from_tv)
    TextView mTransFromTv;
    @BindView(R.id.trans_from_to)
    TextView mTransFromTo;
    @BindView(R.id.trans_reverse)
    LinearLayout mTransReverse;
    @BindView(R.id.trans_symbol)
    TextView mTransSymbol;
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

    private int from = 0, to = 1;//0 - 币币账户 1 - 法币账户
    private String balance;
    private CoinSupportBean mCoinSupportBean;
    private Context mContext;
    private String[] coinList;
    private PointLengthFilter mNumFilter;

    public TransPopup(@NonNull Context context, String balance, CoinSupportBean coinSupportBean) {
        super(context);
        this.mContext = context;
        this.balance = balance;
        this.mCoinSupportBean = coinSupportBean;

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_trans;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mTransSymbol.setText(mCoinSupportBean.getData().get(0).getCoinName());
        mTransNumAvailable.setText(mContext.getString(R.string.available) + DfUtils.numberFormat(balance, 8) + " " + mCoinSupportBean.getData().get(0).getCoinName());
        coinList = new String[mCoinSupportBean.getData().size()];
        for (int i = 0; i < mCoinSupportBean.getData().size(); i++) {
            coinList[i] = mCoinSupportBean.getData().get(i).getCoinName();
        }
        mNumFilter = new PointLengthFilter(16);
        mTransNum.setFilters(new InputFilter[]{mNumFilter});
    }

    @OnClick({R.id.trans_order,
            R.id.trans_reverse,
            R.id.trans_symbol,
            R.id.trans_all,
            R.id.btn_trans,
            R.id.btn_cancel})
    public void OnClick(View view) {
        switch (view.getId()) {
            //记录
            case R.id.trans_order:
                dismiss();
                ARouter.getInstance().build(ARouterPath.ACTIVITY_ME_PROPERTY_RECORD)
                        .withInt("coinType", from)
                        .withInt("recordType", 2)
                        .navigation();
                break;
            //翻转
            case R.id.trans_reverse:
                int temp = from;
                from = to;
                to = temp;
                mTransFromTv.setText(from == 0 ? mContext.getString(R.string.b2b_account) : mContext.getString(R.string.c2c_account));
                mTransFromTo.setText(to == 1 ? mContext.getString(R.string.c2c_account) : mContext.getString(R.string.b2b_account));
                FinanceClient.getInstance().getCoinOutOtcInfo(from, mTransSymbol.getText().toString());
                break;
            //选择币种
            case R.id.trans_symbol:
                new XPopup.Builder(getContext())
                        .atView(view)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(coinList, null,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        mTransSymbol.setText(text);
                                        FinanceClient.getInstance().getCoinOutOtcInfo(from, text);
                                        requstFocus();
                                    }
                                })
                        .show();
                break;
            //全部提取
            case R.id.trans_all:
                mTransNum.setText(balance);
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

                if (Double.valueOf(mTransNum.getText().toString().trim()) > Double.valueOf(balance)) {
                    Toasty.showError(mContext.getString(R.string.trans_correct_number_hint));
                    return;
                }

                CoinTransBean coinTransBean = new CoinTransBean();
                coinTransBean.setAmount(Double.valueOf(mTransNum.getText().toString().trim()));
                coinTransBean.setCoinName(mTransSymbol.getText().toString());
                coinTransBean.setFrom(from == 0 ? "SPOT" : "OTC");
                coinTransBean.setTo(to == 1 ? "OTC" : "SPOT");
                FinanceClient.getInstance().coinTransfer(coinTransBean);
                break;
            //取消
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    public void updateTransNumAvailable(MerberWalletResult merberWalletResult) {
        balance = String.valueOf(merberWalletResult.getData().getBalance());
        mTransNumAvailable.setText(mContext.getString(R.string.available) + DfUtils.numberFormat(merberWalletResult.getData().getBalance(), 8) + " " + mTransSymbol.getText().toString());
    }

    private void requstFocus() {
        mTransNum.setFocusable(true);
        mTransNum.requestFocus();
        InputMethodManager mSoftInputManager = (InputMethodManager) mTransNum.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mSoftInputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }
}

