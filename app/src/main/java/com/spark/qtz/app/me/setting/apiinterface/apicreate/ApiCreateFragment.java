package com.spark.qtz.app.me.setting.apiinterface.apicreate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.databinding.FragmentApiCreateBinding;

import me.spark.mvvm.base.BaseFragment;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/29
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ApiCreateFragment extends BaseFragment<FragmentApiCreateBinding, ApiCreateViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_api_create;
    }

    @Override
    public int initVariableId() {
        return BR.apiCreateViewModel;
    }
}
