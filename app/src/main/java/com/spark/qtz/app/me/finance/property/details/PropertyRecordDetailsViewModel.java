package com.spark.qtz.app.me.finance.property.details;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.spark.acclient.pojo.CoinTransDetailsResult;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.util.AppUtils;

import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.binding.command.BindingAction;
import me.spark.mvvm.binding.command.BindingCommand;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.StringUtils;
import me.spark.mvvm.utils.Utils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/4
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PropertyRecordDetailsViewModel extends BaseViewModel {
    public PropertyRecordDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    private CoinTransDetailsResult.DataBean.RecordsBean mDataBean;
    private Context mContext;

    public ObservableField<String> status = new ObservableField<>();
    public ObservableField<String> coinName = new ObservableField<>();
    public ObservableField<String> amount = new ObservableField<>();
    public ObservableField<String> fee = new ObservableField<>();
    public ObservableField<String> realAmount = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> subType = new ObservableField<>();
    public ObservableField<String> orderSn = new ObservableField<>();
    public ObservableField<String> createTime = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<Boolean> isAddress = new ObservableField<>();
    public ObservableField<Boolean> isSub = new ObservableField<>();

    public void init(Context context, CoinTransDetailsResult.DataBean.RecordsBean propertDetailsBean) {
        this.mContext = context;
        this.mDataBean = propertDetailsBean;
        initViewDate();
    }

    private void initViewDate() {
        status.set(mDataBean.formatStatue());
        coinName.set(App.getInstance().getString(R.string.str_trade_number) + "(" + mDataBean.getCoinName() + ")");
        amount.set(mDataBean.formatAmount());
        fee.set(mDataBean.formatFee() + " " + mDataBean.getCoinName());
        realAmount.set(DfUtils.numberFormat(mDataBean.getAmount() - mDataBean.getFee(), mDataBean.getAmount() - mDataBean.getFee() == 0 ? 0 : 4) + " " + mDataBean.getCoinName());
        type.set(mDataBean.formatType());
        subType.set(mDataBean.initType());
        orderSn.set(mDataBean.getBusinessId());
        createTime.set(mDataBean.formatDate());
        isAddress.set(StringUtils.isNotEmpty(mDataBean.getAddress()));
        if (StringUtils.isNotEmpty(mDataBean.getAddress())) {
            address.set(mDataBean.getAddress());
        }
        isSub.set(StringUtils.isNotEmpty(mDataBean.initType()));
    }

    public BindingCommand orderSnOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            AppUtils.copy2Clipboard(Utils.getContext(), orderSn.get());
        }
    });

    public BindingCommand addressOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            AppUtils.copy2Clipboard(Utils.getContext(), address.get());
        }
    });

}
