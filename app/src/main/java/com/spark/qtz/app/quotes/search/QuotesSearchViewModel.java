package com.spark.qtz.app.quotes.search;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import me.spark.mvvm.base.BaseViewModel;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/30
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QuotesSearchViewModel extends BaseViewModel{
    public ObservableField<String> quotesInput = new ObservableField<>();

    public QuotesSearchViewModel(@NonNull Application application) {
        super(application);
    }
}
