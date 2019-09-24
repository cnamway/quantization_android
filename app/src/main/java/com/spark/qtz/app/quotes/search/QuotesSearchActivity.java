package com.spark.qtz.app.quotes.search;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.base.ARouterPath;
import com.spark.qtz.databinding.ActivityQuotesSearchBinding;
import com.spark.qtz.ui.toast.Toasty;
import com.spark.qtz.util.StatueBarUtils;

import butterknife.OnClick;
import me.spark.mvvm.base.BaseActivity;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/30
 * 描    述：
 * 修订历史：
 * ================================================
 */
@Route(path = ARouterPath.ACTIVITY_QUOTES_SERACH)
public class QuotesSearchActivity extends BaseActivity<ActivityQuotesSearchBinding, QuotesSearchViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_quotes_search;
    }

    @Override
    public int initVariableId() {
        return BR.quotesSearchViewModel;
    }


    @Override
    public void initView() {
        super.initView();
        StatueBarUtils.setStatusBarLightMode(this, true);
        StatueBarUtils.addMarginTopEqualStatusBarHeight(binding.fakeStatusBar);
        StatueBarUtils.setStatusBarColor(this, Color.WHITE);

        initInputListener();
    }

    private void initInputListener() {
        binding.quotesInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(viewModel.quotesInput.get())) {
                        Toasty.showError(getString(R.string.search_not_null));
                        return true;
                    }
                    showKeyboard(false);
                    Toasty.showSuccess(viewModel.quotesInput.get());
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.title_back, R.id.quotes_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.quotes_search:
                quotesSerach();
                break;

        }
    }

    private void quotesSerach() {

    }

}
