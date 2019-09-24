package me.spark.mvvm.http.impl;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/5/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface OnRequestListener<T> {
    void onSuccess(T t);

    void onFail(String message);
}
