package me.spark.mvvm.binding.viewadapter.spinner;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/2
 * 描    述：下拉Spinner控件的键值对, 实现该接口,返回key,value值, 在xml绑定List<IKeyAndValue>
 * 修订历史：
 * ================================================
 */
public interface IKeyAndValue {
    String getKey();

    String getValue();
}
