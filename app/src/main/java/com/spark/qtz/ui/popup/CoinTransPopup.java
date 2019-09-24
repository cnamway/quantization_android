package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.ui.PointLengthFilter;
import com.spark.qtz.ui.toast.Toasty;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.base.Constant;
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
public class CoinTransPopup extends BottomPopupView {
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
    @BindView(R.id.down_from)
    ImageView mDownFrom;
    @BindView(R.id.down_to)
    ImageView mDownTo;
    @BindView(R.id.down_pull)
    ImageView mDownPull;

    private int accountType;//0 - 币币账户 1 - 法币账户 2 - 合约账户
    private int from, to;//0 - 币币账户 1 - 法币账户 2 - 合约账户
    private String balance;
    private CoinSupportBean mCoinSupportBean;
    private Context mContext;
    private String[] b2bList, c2cList;
    private String[] coinList;
    private String[] accountList;
    private PointLengthFilter mNumFilter;
    private boolean isSingleCoin = false;
    private String singleCoinName;

    public CoinTransPopup(@NonNull Context context, int accountType, CoinSupportBean coinSupportBean) {
        super(context);
        this.mContext = context;
        this.accountType = accountType;
        this.mCoinSupportBean = coinSupportBean;
    }

    public CoinTransPopup(@NonNull Context context, int accountType, CoinSupportBean coinSupportBean, String coinName) {
        super(context);
        isSingleCoin = true;
        this.mContext = context;
        this.accountType = accountType;
        this.mCoinSupportBean = coinSupportBean;
        this.singleCoinName = coinName;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_coin_trans;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        b2bList = new String[mCoinSupportBean.getData().size()];
        for (int i = 0; i < mCoinSupportBean.getData().size(); i++) {
            b2bList[i] = mCoinSupportBean.getData().get(i).getCoinName();
        }

        c2cList = new String[Constant.lcCoinPairThumbBeanList.size()];
        for (int i = 0; i < Constant.lcCoinPairThumbBeanList.size(); i++) {
            c2cList[i] = Constant.lcCoinPairThumbBeanList.get(i);
        }

        intersect(b2bList, c2cList);

        accountList = new String[2];
        switch (accountType) {
            case 0:
                mTransFromTv.setText(mContext.getString(R.string.b2b_account));
                mTransFromTo.setText(mContext.getString(R.string.c2c_account));
                accountList[0] = mContext.getString(R.string.c2c_account);
                accountList[1] = mContext.getString(R.string.cfd_account);
                from = 0;
                to = 1;
                if (isSingleCoin) {
                    mTransSymbol.setText(singleCoinName);
                    mDownPull.setVisibility(GONE);
                } else {
                    mTransSymbol.setText(coinList[0]);
                    mDownPull.setVisibility(VISIBLE);
                }
                break;
            case 1:
                mTransFromTv.setText(mContext.getString(R.string.c2c_account));
                mTransFromTo.setText(mContext.getString(R.string.b2b_account));
                accountList[0] = mContext.getString(R.string.b2b_account);
                accountList[1] = mContext.getString(R.string.cfd_account);
                from = 1;
                to = 0;
                if (isSingleCoin) {
                    mTransSymbol.setText(singleCoinName);
                    mDownPull.setVisibility(GONE);
                } else {
                    mTransSymbol.setText(coinList[0]);
                    mDownPull.setVisibility(VISIBLE);
                }
                break;
            case 2:
                mTransFromTv.setText(mContext.getString(R.string.cfd_account));
                mTransFromTo.setText(mContext.getString(R.string.b2b_account));
                accountList[0] = mContext.getString(R.string.b2b_account);
                accountList[1] = mContext.getString(R.string.c2c_account);
                from = 2;
                to = 0;
                mTransSymbol.setText("USDT");
                mDownPull.setVisibility(GONE);
                break;
        }

        mNumFilter = new PointLengthFilter(16);
        mTransNum.setFilters(new InputFilter[]{mNumFilter});
        if (isSingleCoin) {
            initTo(mTransFromTo.getText().toString());
            initIsSingle(mTransFromTv.getText().toString());
        } else {
            getCoinInfo();
        }
    }

    @OnClick({R.id.trans_order,
            R.id.trans_reverse,
            R.id.trans_symbol,
            R.id.trans_all,
            R.id.ll_coin_choose,
            R.id.btn_cancel,
            R.id.ll_from,
            R.id.ll_to,
            R.id.btn_trans})
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
            case R.id.ll_from:
                mBtnTrans.setEnabled(true);
                if (mDownFrom.getVisibility() != VISIBLE) return;
                new XPopup.Builder(getContext())
                        .atView(mDownFrom)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(accountList, null,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        mTransFromTv.setText(text);
                                        if (text.contains(mContext.getString(R.string.cfd_account))
                                                || mTransFromTo.getText().toString().contains(mContext.getString(R.string.cfd_account))) {
                                            mTransSymbol.setText("USDT");
                                            mDownPull.setVisibility(GONE);
                                            initFrom(text);
                                            getCoinInfo();
                                            requstFocus();
                                        } else {
                                            if (isSingleCoin) {
                                                initTo(mTransFromTo.getText().toString());
                                                initIsSingle(mTransFromTv.getText().toString());
                                            } else {
                                                mTransSymbol.setText(coinList[0]);
                                                mDownPull.setVisibility(VISIBLE);
                                                initFrom(text);
                                                getCoinInfo();
                                                requstFocus();
                                            }
                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.ll_to:
                if (mDownTo.getVisibility() != VISIBLE) return;
                new XPopup.Builder(getContext())
                        .atView(mDownTo)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(accountList, null,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        mTransFromTo.setText(text);
                                        if (text.contains(mContext.getString(R.string.cfd_account))
                                                || mTransFromTv.getText().toString().contains(mContext.getString(R.string.cfd_account))) {
                                            mTransSymbol.setText("USDT");
                                            mDownPull.setVisibility(GONE);
                                            initTo(text);
                                            getCoinInfo();
                                            requstFocus();
                                        } else {
                                            if (isSingleCoin) {
                                                initTo(mTransFromTo.getText().toString());
                                                initIsSingle(mTransFromTv.getText().toString());
                                            } else {
                                                mTransSymbol.setText(coinList[0]);
                                                mDownPull.setVisibility(VISIBLE);
                                                initTo(text);
                                                getCoinInfo();
                                                requstFocus();
                                            }
                                        }
                                    }
                                })
                        .show();
                break;
            //翻转
            case R.id.trans_reverse:
                mBtnTrans.setEnabled(true);
                String fromText = mTransFromTv.getText().toString();
                String toText = mTransFromTo.getText().toString();
                switch (accountType) {
                    case 0:
                        if (mTransFromTv.getText().toString().equals(mContext.getString(R.string.b2b_account))) {
                            mTransFromTv.setText(toText);
                            mDownFrom.setVisibility(VISIBLE);
                            mTransFromTo.setText(mContext.getString(R.string.b2b_account));
                            mDownTo.setVisibility(GONE);
                        } else {
                            mTransFromTv.setText(mContext.getString(R.string.b2b_account));
                            mDownFrom.setVisibility(GONE);
                            mTransFromTo.setText(fromText);
                            mDownTo.setVisibility(VISIBLE);
                        }
                        break;
                    case 1:
                        if (mTransFromTv.getText().toString().equals(mContext.getString(R.string.c2c_account))) {
                            mTransFromTv.setText(toText);
                            mDownFrom.setVisibility(VISIBLE);
                            mTransFromTo.setText(mContext.getString(R.string.c2c_account));
                            mDownTo.setVisibility(GONE);
                        } else {
                            mTransFromTv.setText(mContext.getString(R.string.c2c_account));
                            mDownFrom.setVisibility(GONE);
                            mTransFromTo.setText(fromText);
                            mDownTo.setVisibility(VISIBLE);
                        }
                        break;
                    case 2:
                        if (mTransFromTv.getText().toString().equals(mContext.getString(R.string.cfd_account))) {
                            mTransFromTv.setText(toText);
                            mTransFromTo.setText(mContext.getString(R.string.cfd_account));
                            mDownFrom.setVisibility(VISIBLE);
                            mDownTo.setVisibility(GONE);
                        } else {
                            mTransFromTv.setText(mContext.getString(R.string.cfd_account));
                            mDownFrom.setVisibility(GONE);
                            mTransFromTo.setText(fromText);
                            mDownTo.setVisibility(VISIBLE);
                        }
                        break;
                }

                if (fromText.contains(mContext.getString(R.string.cfd_account))
                        || toText.contains(mContext.getString(R.string.cfd_account))) {
                    mTransSymbol.setText("USDT");
                    mDownPull.setVisibility(GONE);
                    initFrom(mTransFromTv.getText().toString());
                    initTo(mTransFromTo.getText().toString());
                    getCoinInfo();
                } else {
                    if (isSingleCoin) {
                        initTo(mTransFromTo.getText().toString());
                        initIsSingle(mTransFromTv.getText().toString());
                    } else {
                        mDownPull.setVisibility(VISIBLE);
                        initFrom(mTransFromTv.getText().toString());
                        initTo(mTransFromTo.getText().toString());
                        getCoinInfo();
                    }
                }


                break;
            //选择币种
            case R.id.ll_coin_choose:
                if (mDownPull.getVisibility() != VISIBLE) return;
                new XPopup.Builder(getContext())
                        .atView(view)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(coinList, null,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        mTransSymbol.setText(text);
                                        getCoinInfo();
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
                switch (from) {
                    case 0:
                        coinTransBean.setFrom("SPOT");
                        break;
                    case 1:
                        coinTransBean.setFrom("OTC");
                        break;
                    case 2:
                        coinTransBean.setFrom("CFD");
                        break;
                }
                switch (to) {
                    case 0:
                        coinTransBean.setTo("SPOT");
                        break;
                    case 1:
                        coinTransBean.setTo("OTC");
                        break;
                    case 2:
                        coinTransBean.setTo("CFD");
                        break;
                }
                FinanceClient.getInstance().coinTransfer(coinTransBean);
                break;
            //取消
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    private void getCoinInfo() {
        mTransNumAvailable.setText(mContext.getString(R.string.available) + "--" + mTransSymbol.getText().toString());
        String type = "";
        switch (from) {
            case 0:
                type = "/SPOT/";
                break;
            case 1:
                type = "/OTC/";
                break;
            case 2:
                type = "/CFD/";
                break;
        }
        FinanceClient.getInstance().getCoinOutOtcInfo(type, mTransSymbol.getText().toString());
    }

    private void initFrom(String text) {
        if (text.equals(mContext.getString(R.string.b2b_account))) {
            from = 0;
        } else if (text.equals(mContext.getString(R.string.c2c_account))) {
            from = 1;
        } else if (text.equals(mContext.getString(R.string.cfd_account))) {
            from = 2;
        }
    }

    private void initTo(String text) {
        if (text.equals(mContext.getString(R.string.b2b_account))) {
            to = 0;
        } else if (text.equals(mContext.getString(R.string.c2c_account))) {
            to = 1;
        } else if (text.equals(mContext.getString(R.string.cfd_account))) {
            to = 2;
        }
    }

    private void initIsSingle(String text) {
        mTransSymbol.setText(singleCoinName);
        mDownPull.setVisibility(GONE);
        if (text.contains(mContext.getString(R.string.b2b_account))) {
            if (Arrays.asList(b2bList).contains(singleCoinName)) {
                initFrom(text);
                getCoinInfo();
                requstFocus();
            } else {
                mBtnTrans.setEnabled(false);
                mBtnTrans.setText(App.getInstance().getApplicationContext().getString(R.string.str_b2b_account_no_support));
            }
        } else if (text.contains(mContext.getString(R.string.c2c_account))) {
            if (Arrays.asList(c2cList).contains(singleCoinName)) {
                initFrom(text);
                getCoinInfo();
                requstFocus();
            } else {
                mBtnTrans.setEnabled(false);
                mBtnTrans.setText(App.getInstance().getApplicationContext().getString(R.string.str_c2c_account_no_support));
            }
        }
    }


    public void updateTransNumAvailable(MerberWalletResult merberWalletResult) {
        balance = DfUtils.numberFormatDown(merberWalletResult.getData().getBalance(), 8);
        mTransNumAvailable.setText(mContext.getString(R.string.available) + balance + " " + mTransSymbol.getText().toString());
    }

    private void requstFocus() {
        mTransNum.setFocusable(true);
        mTransNum.requestFocus();
        InputMethodManager mSoftInputManager = (InputMethodManager) mTransNum.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mSoftInputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    //交集(注意结果集中若使用LinkedList添加，则需要判断是否包含该元素，否则其中会包含重复的元素)
    private void intersect(String[] arr1, String[] arr2) {
        List<String> l = new LinkedList<>();
        Set<String> common = new HashSet<>();
        for (String str : arr1) {
            if (!l.contains(str)) {
                l.add(str);
            }
        }
        for (String str : arr2) {
            if (l.contains(str)) {
                common.add(str);
            }
        }
        String[] result = {};
        coinList = common.toArray(result);
    }
}

