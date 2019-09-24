package com.spark.qtz.app.quotes;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.lxj.xpopup.XPopup;
import com.spark.acclient.pojo.CoinWithdrawAddressResult;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.FragmentQuotesBinding;
import com.spark.qtz.ui.popup.DatePickerPopup;
import com.spark.qtz.ui.popup.IDPopup;
import com.spark.qtz.ui.popup.impl.OnDatePickerListener;
import com.spark.qtz.ui.popup.impl.OnEtContentListener;
import com.spark.wsclient.pojo.B2BThumbBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseFragment;
import me.spark.mvvm.base.Constant;
import me.spark.mvvm.utils.DateUtils;
import me.spark.mvvm.utils.DfUtils;
import me.spark.mvvm.utils.LogUtils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/18
 * 描    述：运营统计
 * 修订历史：
 * ================================================
 */
public class QuotesFragment extends BaseFragment<FragmentQuotesBinding, QuotesViewModel> {
    private TitleBean mTitleModel;
    private boolean isChartInt = false;
    private ArrayList<Entry> yValues = new ArrayList<>();
    private ArrayList<Entry> valuesTemp = new ArrayList<>();
    private LineDataSet mLineDataSetDate;
    //设置x轴的数据
    ArrayList<String> xValues = new ArrayList<>();
    private XAxis xAxis;      //X轴
    private YAxis leftAxis;   //左边Y轴
    private List<CoinWithdrawAddressResult.DataBean> dataBeanList;
    private String ids;
    private String dateTime;
    private IDPopup mIDPopup;
    private boolean isAll = true;//是否总用户
    private List<B2BThumbBean.DateBean> pointList;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_quotes;
    }

    @Override
    public int initVariableId() {
        return BR.quotesViewModel;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        viewModel.isVisible2User = !hidden;
    }

    @Override
    public void initView() {
        super.initView();
        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleNameLeft(getResources().getString(R.string.str_index_2));
        mTitleModel.setShowLeftImg(false);
        binding.title.setViewTitle(mTitleModel);
        initChart();
        binding.llToday.setSelected(true);
        dateTime = DateUtils.formatDate("yyyy-MM-dd", new Date().getTime());
        viewModel.dateTime.set(dateTime);
        binding.llTabEmex.setSelected(true);
//        binding.llTabEmex.setPressed(true);
        binding.llTabQuotes.setSelected(false);
//        binding.llTabQuotes.setPressed(false);
    }

    @Override
    public void initData() {
//        if (!binding.quotesRoot.isLoadingCurrentState())
//            binding.quotesRoot.showLoading();
        viewModel.initChat();
        getIDData();
    }

    /**
     * 初始化用户ID列表
     */
    private void getIDData() {
        dataBeanList = new ArrayList<>();
        CoinWithdrawAddressResult.DataBean dataBean;
        for (int i = 0; i < 18; i++) {
            dataBean = new CoinWithdrawAddressResult.DataBean();
            dataBean.setCoinId("" + new Random().nextInt(1000000));
            dataBeanList.add(dataBean);
        }
        ids = dataBeanList.get(0).getCoinId();
        viewModel.ids.set(ids);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.clickPosition.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer index) {
                if (index == 0) {
                    isAll = true;
                } else {
                    isAll = false;
                }
                setSelection(index);
            }
        });
        viewModel.uc.closeValue.observe(this, new Observer<List<B2BThumbBean.DateBean>>() {
            @Override
            public void onChanged(@Nullable List<B2BThumbBean.DateBean> list) {
                if (!isChartInt) return;
                if (list == null) return;
                pointList = list;
                setChartDate(pointList);
            }
        });
    }

    private void initChart() {
        // background color
        binding.lineChart.setBackgroundColor(Color.TRANSPARENT);
        binding.lineChart.setGridBackgroundColor(Color.TRANSPARENT);
        // disable description text
        binding.lineChart.getDescription().setEnabled(false);
        // enable touch gestures
        binding.lineChart.setTouchEnabled(true);
        binding.lineChart.setNoDataText("数据加载中");
        binding.lineChart.setDragEnabled(true);
        binding.lineChart.setDoubleTapToZoomEnabled(false);
        // set listeners
        binding.lineChart.setDrawGridBackground(true);

        // create marker to display box when yValues are selected
        //MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        // Set the marker to the chart
        //mv.setChartView(binding.lineChart);
        //binding.lineChart.setMarker(mv);

        // enable scaling and dragging
        binding.lineChart.setDragEnabled(true);
        binding.lineChart.setScaleEnabled(true);
        binding.lineChart.setPinchZoom(true);
        //binding.lineChart.getXAxis().setEnabled(false);
        binding.lineChart.getAxisLeft().setEnabled(false);
        binding.lineChart.getAxisRight().setEnabled(false);
        Legend l = binding.lineChart.getLegend();
        l.setForm(Legend.LegendForm.NONE);
        binding.lineChart.animateX(1500);
        isChartInt = true;
    }

    private void setChartDate(List<B2BThumbBean.DateBean> list) {
        yValues.clear();
        xValues.clear();
//        for (int i = 0; i < list.size(); i++) {
        for (int i = 0; i < 10; i++) {
            //yValues.add(new Entry(i, valuesTemp.get(i + 1).getY(), null));
            //yValues.add(new Entry(yValues.size(), Float.parseFloat(DfUtils.numberFormatDown(list.get(i).getClose(), 2)), null));
            yValues.add(new Entry(yValues.size(), Float.parseFloat(list.get(i).getClose() + ""), null));
            xValues.add(((i + 1) > 9 ? (i + 1) : "0" + (i + 1)) + ":00");
        }

        if (binding.lineChart.getData() != null && binding.lineChart.getData().getDataSetCount() > 0) {
            LogUtils.e("getXMax", mLineDataSetDate.getXMax());

            mLineDataSetDate = (LineDataSet) binding.lineChart.getData().getDataSetByIndex(0);
            mLineDataSetDate.setValues(yValues);

            if (isAll) {
                // black lines and points
                mLineDataSetDate.setColor(Color.parseColor("#64CBB2"));
                mLineDataSetDate.setCircleColor(Color.parseColor("#64CBB2"));
            } else {
                // black lines and points
                mLineDataSetDate.setColor(Color.parseColor("#69A8FF"));
                mLineDataSetDate.setCircleColor(Color.parseColor("#69A8FF"));
            }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                if (isAll) {
                    Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
                    mLineDataSetDate.setFillDrawable(drawable);
                } else {
                    Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_blue);
                    mLineDataSetDate.setFillDrawable(drawable);
                }
            } else {
                mLineDataSetDate.setFillColor(Color.TRANSPARENT);
            }

            mLineDataSetDate.notifyDataSetChanged();
            binding.lineChart.getData().notifyDataChanged();
            binding.lineChart.notifyDataSetChanged();
            binding.lineChart.invalidate();
        } else {
            // create a dataset and give it a type
            mLineDataSetDate = new LineDataSet(yValues, "");

            mLineDataSetDate.setDrawIcons(false);
            mLineDataSetDate.setDrawValues(true);
            mLineDataSetDate.setValueTextColor(Color.WHITE);

            // draw dashed line
//            mLineDataSetDate.enableDashedLine(10f, 0f, 0f);

            if (isAll) {
                // black lines and points
                mLineDataSetDate.setColor(Color.parseColor("#64CBB2"));
                mLineDataSetDate.setCircleColor(Color.parseColor("#64CBB2"));
            } else {
                // black lines and points
                mLineDataSetDate.setColor(Color.parseColor("#69A8FF"));
                mLineDataSetDate.setCircleColor(Color.parseColor("#69A8FF"));
            }

            mLineDataSetDate.setDrawCircles(true);

            // line thickness and point size
            mLineDataSetDate.setLineWidth(2f);
            mLineDataSetDate.setCircleRadius(5f);
            mLineDataSetDate.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            // draw points as solid circles
            mLineDataSetDate.setDrawCircleHole(true);

            // customize legend entry
            mLineDataSetDate.setFormLineWidth(1f);
            mLineDataSetDate.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            //折线图下方块图形
            mLineDataSetDate.setFormSize(0f);
            // text size of yValues
            mLineDataSetDate.setValueTextSize(10f);
            // draw selection line as dashed
            mLineDataSetDate.enableDashedHighlightLine(100000f, 100000f, 100000f);

            // set the filled area
            mLineDataSetDate.setDrawFilled(true);
            mLineDataSetDate.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return binding.lineChart.getAxisLeft().getAxisMinimum();
                }
            });

            //Y赋值
            mLineDataSetDate.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "" + DfUtils.numberFormatDown(value, 2);
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                if (isAll) {
                    Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
                    mLineDataSetDate.setFillDrawable(drawable);
                } else {
                    Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_blue);
                    mLineDataSetDate.setFillDrawable(drawable);
                }
            } else {
                mLineDataSetDate.setFillColor(Color.TRANSPARENT);
            }

            //X轴的设置
            xAxis = binding.lineChart.getXAxis();
            //X轴设置显示位置在底部,默认显示位置在顶部
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setEnabled(true);//是否隐藏坐标
            xAxis.setGridColor(Color.TRANSPARENT); //网格线颜色
            xAxis.setAxisLineColor(Color.parseColor("#29365C")); //X轴颜色
            xAxis.setTextColor(Color.parseColor("#29365C"));//字体颜色

            //X轴赋值
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int index = (int) value;
                    if (index >= 0 && index < xValues.size()) {
                        return xValues.get((int) value);
                    } else {
                        return "";
                    }
                }
            });

            //设置X轴的刻度数
            xAxis.setLabelCount(xValues.size(), true);
//            xAxis.setAxisMinimum(0f);
//            xAxis.setGranularity(1f);

            //Y轴的设置
            leftAxis = binding.lineChart.getAxisLeft();
            //保证Y轴从0开始，不然会上移一点
            leftAxis.setAxisMinimum(0f);
            leftAxis.setEnabled(true);//是否隐藏坐标
            leftAxis.setGridColor(Color.parseColor("#29365C")); //网格线颜色
            leftAxis.setAxisLineColor(Color.parseColor("#29365C")); //Y轴颜色
            leftAxis.setTextColor(Color.WHITE);//字体颜色

            binding.lineChart.setScaleXEnabled(true);//x轴缩放
            binding.lineChart.setScaleYEnabled(true);//x轴缩放

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(mLineDataSetDate); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            binding.lineChart.setData(data);
            binding.lineChart.invalidate();
        }
    }

    private void setSelection(int index) {
        try {
            clearChartData();
            // 开启一个Fragment事务
            //transaction = fragmentManager.beginTransaction();
            // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
            //hideFragments(transaction);
            switch (index) {
                //总用户
                case 0:
                    binding.llTabEmex.setSelected(true);
//                    binding.llTabEmex.setPressed(true);
                    binding.llTabQuotes.setSelected(false);
//                    binding.llTabQuotes.setPressed(false);
                    Constant.isIndexVisiable = true;
                    break;
                //单用户
                case 1:
                    binding.llTabEmex.setSelected(false);
//                    binding.llTabEmex.setPressed(false);
                    binding.llTabQuotes.setSelected(true);
//                    binding.llTabQuotes.setPressed(true);
                    Constant.isQoutesVisiable = true;
                    break;
            }
            setChartDate(pointList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //清除数据
    private void clearChartData() {
        if (binding.lineChart != null) {
            binding.lineChart.clear();
        }
    }

    @OnClick({R.id.llToday
            , R.id.llYesterday
            , R.id.llID
            , R.id.llCalendar})
    public void OnClick(final View view) {
        switch (view.getId()) {
            //今天
            case R.id.llToday:
                binding.llToday.setSelected(true);
                binding.llYesterday.setSelected(false);
                dateTime = DateUtils.formatDate("yyyy-MM-dd", new Date().getTime());
                viewModel.dateTime.set(dateTime);
                break;
            //昨天
            case R.id.llYesterday:
                binding.llToday.setSelected(false);
                binding.llYesterday.setSelected(true);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                dateTime = DateUtils.formatDate("yyyy-MM-dd", calendar.getTime().getTime());
                viewModel.dateTime.set(dateTime);
                break;
            //ID
            case R.id.llID:
                new XPopup.Builder(getContext())
                        .atView(view)
                        .asCustom(new IDPopup(getContext(), ids, dataBeanList, new OnEtContentListener() {
                            @Override
                            public void onCEtContentInput(String content) {
                                ids = content;
                                viewModel.ids.set(ids);
                            }
                        }))
                        .show();
                break;
            //日历
            case R.id.llCalendar:
                new XPopup.Builder(getContext())
                        .asCustom(new DatePickerPopup(getContext(), new OnDatePickerListener() {
                            @Override
                            public void onConfirm(String year, String month, String day) {
                                dateTime = year + "-" + month + "-" + day;
                                viewModel.dateTime.set(dateTime);
                            }
                        }))
                        .show();
                break;
        }
    }
}
