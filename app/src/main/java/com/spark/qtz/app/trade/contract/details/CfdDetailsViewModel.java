package com.spark.qtz.app.trade.contract.details;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.modulecfd.pojo.CfdCommissionResult;
import com.example.modulecfd.pojo.CfdDealResult;
import com.example.modulecfd.pojo.CfdPositionResult;
import com.example.modulecfd.pojo.CfdRevokeResult;
import com.example.modulecfd.pojo.CfdTradeOrderResult;
import com.spark.qtz.App;
import com.spark.qtz.R;

import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.base.BaseViewModel;
import me.spark.mvvm.utils.DateUtils;
import me.spark.mvvm.utils.DfUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-01
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CfdDetailsViewModel extends BaseViewModel {
    public CfdDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> profitLoss = new ObservableField<>("");
    public ObservableField<String> side = new ObservableField<>("");
    public ObservableField<Boolean> isSideUp = new ObservableField<>(true);
    public ObservableField<String> symbol = new ObservableField<>("");
    public ObservableField<String> orderId = new ObservableField<>("");
    public ObservableField<String> pendingPrice = new ObservableField<>("");
    public ObservableField<String> openPrice = new ObservableField<>("");
    public ObservableField<String> closePrice = new ObservableField<>("");
    public ObservableField<String> position = new ObservableField<>("");
    public ObservableField<String> marginPrice = new ObservableField<>("");
    public ObservableField<String> profitPrice = new ObservableField<>("");
    public ObservableField<String> lossPrice = new ObservableField<>("");
    public ObservableField<String> holdPrice = new ObservableField<>("");
    public ObservableField<String> openFee = new ObservableField<>("");
    public ObservableField<String> openType = new ObservableField<>("");
    public ObservableField<String> openTime = new ObservableField<>("");
    public ObservableField<String> closeFee = new ObservableField<>("");
    public ObservableField<String> closeType = new ObservableField<>("");
    public ObservableField<String> closeTime = new ObservableField<>("");
    public ObservableField<String> pendingTime = new ObservableField<>("");
    public ObservableField<String> withdrawalTime = new ObservableField<>("");
    public ObservableField<Boolean> isPosition = new ObservableField<>(true);


    /**
     * 仓位
     *
     * @param recordsBean
     */
    public void initPositionDetails(CfdPositionResult.DataBean recordsBean) {
        isPosition.set(true);
        profitLoss.set(recordsBean.formatProfitAndloss());
        side.set(recordsBean.formatSide());
        isSideUp.set(recordsBean.isSideUp());
        symbol.set(recordsBean.formatSymbol());
        orderId.set("NO." + recordsBean.getId());
        openPrice.set(recordsBean.formatOpenPrice());
        closePrice.set("--");
        position.set(recordsBean.getVolume() + App.getInstance().getString(R.string.hands));
        marginPrice.set(DfUtils.numberFormat(recordsBean.getMarginFee(), 4));
        profitPrice.set(DfUtils.numberFormat(recordsBean.getStopProfitPrice(), 4));
        lossPrice.set(DfUtils.numberFormat(recordsBean.getStopLossPrice(), 4));
        holdPrice.set(DfUtils.numberFormat(recordsBean.getHoldFee(), 4));
        openFee.set(DfUtils.numberFormat(recordsBean.getFee(), 4));
        openType.set(recordsBean.formatOpenType());
        openTime.set(DateUtils.formatDate("MM/dd HH:mm", recordsBean.getOpenTime()));
        closeFee.set("--");
        closeType.set("--");
        closeTime.set("--");
    }

    /**
     * 当前委托
     *
     * @param recordsBean
     */
    public void initCommissionDetails(CfdCommissionResult.DataBean.RecordsBean recordsBean) {
        isPosition.set(false);
        profitLoss.set(BaseApplication.getInstance().getString(R.string.pending_order));
        side.set(recordsBean.formatSide());
        isSideUp.set(recordsBean.isSideUp());
        symbol.set(recordsBean.formatSymbol());
        orderId.set("NO." + recordsBean.getId());
        pendingPrice.set(recordsBean.formatOpenPrice());
        position.set(recordsBean.getVolume() + BaseApplication.getInstance().getString(R.string.hands));
        marginPrice.set(DfUtils.numberFormat(recordsBean.getMarginFee(), 4));
        profitPrice.set(DfUtils.numberFormat(recordsBean.getStopProfitPrice(), 4));
        lossPrice.set(DfUtils.numberFormat(recordsBean.getStopLossPrice(), 4));
        openType.set(recordsBean.formatOpenType());
        pendingTime.set(recordsBean.formatDate());
        withdrawalTime.set("--");
    }

    /**
     * 已成交
     *
     * @param recordsBean
     */
    public void initDealDetails(CfdDealResult.DataBean.RecordsBean recordsBean) {
        isPosition.set(true);
        profitLoss.set(recordsBean.formatProfitAndloss());
        side.set(recordsBean.formatSide());
        isSideUp.set(recordsBean.isSideUp());
        symbol.set(recordsBean.formatSymbol());
        orderId.set("NO." + recordsBean.getId());
        openPrice.set(DfUtils.numberFormat(recordsBean.getOpenPrice(), 4));
        closePrice.set(DfUtils.numberFormat(recordsBean.getClosePrice(), 4));
        position.set(recordsBean.getVolume() + BaseApplication.getInstance().getString(R.string.hands));
        marginPrice.set(DfUtils.numberFormat(recordsBean.getMarginFee(), 4));
        profitPrice.set(DfUtils.numberFormat(recordsBean.getStopProfitPrice(), 4));
        lossPrice.set(DfUtils.numberFormat(recordsBean.getStopLossPrice(), 4));
        holdPrice.set(DfUtils.numberFormat(recordsBean.getHoldFee(), 4));
        openFee.set(DfUtils.numberFormat(recordsBean.getOpenFee(), 4));
        openType.set(recordsBean.formatOpenType());
        openTime.set(DateUtils.formatDate("MM/dd HH:mm", recordsBean.getOpenTime()));
        closeFee.set(DfUtils.numberFormat(recordsBean.getFee(), 4));
        //0:止盈平仓 1:止损平仓 2:自主平仓 3:强平,4:权益风险率强平
        switch (recordsBean.getCloseType()) {
            case 0:
                closeType.set(BaseApplication.getInstance().getString(R.string.take_profit_Close));
                break;
            case 1:
                closeType.set(BaseApplication.getInstance().getString(R.string.take_loss_Close));
                break;
            case 2:
                closeType.set(BaseApplication.getInstance().getString(R.string.self_close));
                break;
            case 3:
                closeType.set(BaseApplication.getInstance().getString(R.string.forced_close));
                break;
            case 4:
                closeType.set(BaseApplication.getInstance().getString(R.string.hazard_rate_forced_close));
                break;
        }
        closeTime.set(DateUtils.formatDate("MM/dd HH:mm", recordsBean.getCloseTime()));
    }

    /**
     * 已撤单
     *
     * @param recordsBean
     */
    public void initRevokeDetails(CfdRevokeResult.DataBean.RecordsBean recordsBean) {
        isPosition.set(false);
        profitLoss.set(BaseApplication.getInstance().getString(R.string.withdrawn));
        side.set(recordsBean.formatSide());
        isSideUp.set(recordsBean.isSideUp());
        symbol.set(recordsBean.formatSymbol());
        orderId.set("NO." + recordsBean.getId());
        pendingPrice.set(recordsBean.formatPrice());
        position.set(recordsBean.getVolume() + BaseApplication.getInstance().getString(R.string.hands));
        marginPrice.set(DfUtils.numberFormat(recordsBean.getMarginFee(), 4));
        profitPrice.set(recordsBean.formatStopProfitPrice());
        lossPrice.set(recordsBean.formatStopLossPrice());
        openType.set(recordsBean.formatOpenType());
        pendingTime.set(recordsBean.formatOpenDate());
        withdrawalTime.set(recordsBean.formatCloseDate());
    }

    /**
     * 合约交易明细
     *
     * @param recordsBean
     */
    public void initAccountDetails(CfdTradeOrderResult.DataBean.RecordsBean recordsBean) {
        isPosition.set(true);
        profitLoss.set(recordsBean.formatProfitAndloss());
        side.set(recordsBean.formatSide());
        isSideUp.set(recordsBean.isSideUp());
        symbol.set(recordsBean.formatSymbol());
        orderId.set("NO." + recordsBean.getId());
        openPrice.set(DfUtils.numberFormat(recordsBean.getOpenPrice(), 4));
        closePrice.set(recordsBean.getIntType() == 0 ? "--" : DfUtils.numberFormat(recordsBean.getClosePrice(), 4));
        position.set(recordsBean.getVolume() + BaseApplication.getInstance().getString(R.string.hands));
        marginPrice.set(DfUtils.numberFormat(recordsBean.getMarginFee(), 4));
        profitPrice.set(DfUtils.numberFormat(recordsBean.getStopProfitPrice(), 4));
        lossPrice.set(DfUtils.numberFormat(recordsBean.getStopLossPrice(), 4));
        holdPrice.set(DfUtils.numberFormat(recordsBean.getHoldFee(), 4));
        openFee.set(DfUtils.numberFormat(recordsBean.getOpenFee(), 4));
        openType.set((recordsBean.formatOpenType()));
        openTime.set(DateUtils.formatDate("MM/dd HH:mm", recordsBean.getOpenTime()));
        closeFee.set(recordsBean.getIntType() == 0 ? "--" : DfUtils.numberFormat(recordsBean.getFee(), 4));
        //0:止盈平仓 1:止损平仓 2:自主平仓 3:强平,4:权益风险率强平
        switch (recordsBean.getCloseType()) {
            case 0:
                closeType.set(recordsBean.getIntType() == 0 ? "--" : BaseApplication.getInstance().getString(R.string.take_profit_Close));
                break;
            case 1:
                closeType.set(recordsBean.getIntType() == 0 ? "--" : BaseApplication.getInstance().getString(R.string.take_loss_Close));
                break;
            case 2:
                closeType.set(recordsBean.getIntType() == 0 ? "--" : BaseApplication.getInstance().getString(R.string.self_close));
                break;
            case 3:
                closeType.set(recordsBean.getIntType() == 0 ? "--" : BaseApplication.getInstance().getString(R.string.forced_close));
                break;
            case 4:
                closeType.set(recordsBean.getIntType() == 0 ? "--" : BaseApplication.getInstance().getString(R.string.hazard_rate_forced_close));
                break;
        }
        closeTime.set(recordsBean.getIntType() == 0 ? "--" : DateUtils.formatDate("MM/dd HH:mm", recordsBean.getCloseTime()));
    }
}
