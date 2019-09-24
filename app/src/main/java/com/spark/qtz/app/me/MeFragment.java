package com.spark.qtz.app.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.spark.qtz.BR;
import com.spark.qtz.R;
import com.spark.qtz.bean.TitleBean;
import com.spark.qtz.databinding.FragmentMeBinding;

import me.spark.mvvm.base.BaseFragment;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/18
 * 描    述：个人中心
 * 修订历史：
 * ================================================
 */
public class MeFragment extends BaseFragment<FragmentMeBinding, com.spark.qtz.app.me.MeViewModel> {
    private TitleBean mTitleModel;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_me;
    }

    @Override
    public int initVariableId() {
        return BR.meViewModel;
    }

    @Override
    public void initView() {
        //TitleSet
        mTitleModel = new TitleBean();
        mTitleModel.setTitleNameLeft(getResources().getString(R.string.str_index_4));
        mTitleModel.setShowLeftImg(false);
        binding.title.setViewTitle(mTitleModel);

        viewModel.initContext(getContext());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        viewModel.isVisible2User = !hidden;
    }

    @Override
    public void initViewObservable() {
    }
}
