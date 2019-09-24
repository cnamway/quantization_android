package com.spark.qtz.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.spark.qtz.R;
import com.spark.qtz.bean.AnnounceItemBean;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019-07-25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AnnounceView extends MarqueeFactory<LinearLayout, AnnounceItemBean> {
    private LayoutInflater inflater;

    public AnnounceView(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public LinearLayout generateMarqueeItemView(AnnounceItemBean data) {
        LinearLayout mView = (LinearLayout) inflater.inflate(R.layout.view_announce, null);
        ((TextView) mView.findViewById(R.id.title)).setText(data.getTitle());
        ((TextView) mView.findViewById(R.id.time)).setText(data.getTime());
        return mView;
    }
}
