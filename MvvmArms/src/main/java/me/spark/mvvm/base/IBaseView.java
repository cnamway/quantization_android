package me.spark.mvvm.base;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/15
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface IBaseView {
    /**
     * 懒加载
     */
    void loadLazyData();

    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化界面
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
