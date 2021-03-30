package com.lib_common.observer;

import android.content.Context;

import androidx.annotation.StringRes;

/**
 * 全局基础View
 */
public interface IBaseView {

    void showErrorMessage(String err);

    void showErrorMessage(int idRes);

    Context getActivityContext();

    /**
     * 默认显示加载弹框
     */
    void showDialog();

    /**
     * 显示加载弹框
     *
     * @param msg 自定义弹框提示信息
     */
    void showDialog(String msg);

    /**
     * 隐藏弹框
     */
    void hideDialog();

    /**
     * 显示短时间Toast
     */
    void shortShow(@StringRes int strId);

    /**
     * 显示短时间Toast
     */
    void shortShow(String msg);
}
