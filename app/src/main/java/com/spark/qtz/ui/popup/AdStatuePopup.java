package com.spark.qtz.ui.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.spark.qtz.App;
import com.spark.qtz.R;
import com.spark.qtz.ui.popup.impl.OnPositionChooseListener;
import com.spark.otcclient.pojo.AdSelfDownFindResult;
import com.spark.otcclient.pojo.AdSelfUpFindResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.spark.mvvm.utils.MathUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/6/3
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AdStatuePopup extends BottomPopupView {
    @BindView(R.id.statue_tv)
    TextView mStatueTv;
    @BindView(R.id.ad_limit)
    TextView mAdLimit;
    @BindView(R.id.remaining_num)
    TextView mRemainingNum;
    @BindView(R.id.btn_update)
    TextView mBtnUpdate;
    @BindView(R.id.btn_statue)
    TextView mBtnStatue;
    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;
    @BindView(R.id.btn_delete)
    TextView mBtnDelete;

    private OnPositionChooseListener mOnPositionChooseListener;
    private AdSelfUpFindResult.DataBean upBean;
    private AdSelfDownFindResult.DataBean.RecordsBean downBean;
    private int type;
    private Context mContext;

    /**
     * 上架中
     *
     * @param context
     * @param upBean
     * @param mOnPositionChooseListener
     */
    public AdStatuePopup(@NonNull Context context, AdSelfUpFindResult.DataBean upBean, OnPositionChooseListener mOnPositionChooseListener) {
        super(context);
        this.mContext = context;
        this.mOnPositionChooseListener = mOnPositionChooseListener;
        this.upBean = upBean;
        type = 0;
    }


    /**
     * 已下架
     *
     * @param context
     * @param downBean
     * @param mOnPositionChooseListener
     */
    public AdStatuePopup(@NonNull Context context, AdSelfDownFindResult.DataBean.RecordsBean downBean, OnPositionChooseListener mOnPositionChooseListener) {
        super(context);
        this.mContext = context;
        this.mOnPositionChooseListener = mOnPositionChooseListener;
        this.downBean = downBean;
        type = 1;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_ad_statue;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        switch (type) {
            //上架中
            case 0:
                mStatueTv.setText(upBean.getAdvertiseType() == 0 ? App.getInstance().getApplicationContext().getString(R.string.buy2) : App.getInstance().getApplicationContext().getString(R.string.sell2));
                mStatueTv.setTextColor(upBean.getAdvertiseType() == 0 ?
                        ContextCompat.getColor(mContext, R.color.green) :
                        ContextCompat.getColor(mContext, R.color.orange));
                mAdLimit.setText(MathUtils.getRundNumber(upBean.getMinLimit(), 2, null)
                        + " - " +
                        MathUtils.getRundNumber(upBean.getMaxLimit(), 2, null) + " CNY");
                mRemainingNum.setText(upBean.getRemainAmount() + " " + upBean.getCoinName());
                mBtnUpdate.setText(App.getInstance().getApplicationContext().getString(R.string.str_ad_remove));
                mBtnStatue.setVisibility(GONE);
                mBtnDelete.setVisibility(GONE);
                break;
            //已下架
            case 1:
                mStatueTv.setText(downBean.getAdvertiseType() == 0 ? App.getInstance().getApplicationContext().getString(R.string.buy2) : App.getInstance().getApplicationContext().getString(R.string.sell2));
                mStatueTv.setTextColor(downBean.getAdvertiseType() == 0 ?
                        ContextCompat.getColor(mContext, R.color.green) :
                        ContextCompat.getColor(mContext, R.color.orange));
                mAdLimit.setText(MathUtils.getRundNumber(downBean.getMinLimit(), 2, null)
                        + " - " +
                        MathUtils.getRundNumber(downBean.getMaxLimit(), 2, null) + " CNY");
                mRemainingNum.setText(downBean.getRemainAmount() + " " + downBean.getCoinName());
                mBtnUpdate.setText(App.getInstance().getApplicationContext().getString(R.string.str_update));
                mBtnStatue.setText(App.getInstance().getApplicationContext().getString(R.string.str_shelves));
                mBtnDelete.setVisibility(VISIBLE);
                mBtnDelete.setText(App.getInstance().getApplicationContext().getString(R.string.delete));
                break;
        }
    }

    @OnClick({R.id.btn_update,
            R.id.btn_statue,
            R.id.btn_cancel,
            R.id.btn_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                dismiss();
                mOnPositionChooseListener.onChoosePosition(0);
                break;
            case R.id.btn_statue:
                dismiss();
                mOnPositionChooseListener.onChoosePosition(1);
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_delete:
                dismiss();
                mOnPositionChooseListener.onChoosePosition(2);
                break;
        }
    }


}

