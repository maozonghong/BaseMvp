package com.lib_common.observer

import androidx.annotation.CallSuper

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Copyright (c) 2021 All rights reserved
 * 基础观察者
 * @version 1.0
 */
abstract class BaseObserver<T>(val mBaseView: IBaseView?) : Observer<T> {
    //是否需要等待窗
    private var needShowDialog = true

    var t: T? = null
        private set

    /**
     * @param needShowDialog 是否需要默认等待窗
     */
    constructor(mBaseView: IBaseView?, needShowDialog: Boolean) : this(mBaseView) {
        this.needShowDialog = needShowDialog
    }

    @CallSuper
    override fun onSubscribe(d: Disposable) {
        if (mBaseView != null && needShowDialog) {
            mBaseView.showDialog()
        }
    }

    override fun onNext(result: T) {
        t = result
    }

    @CallSuper
    override fun onComplete() {
        disMissDialog()
    }

    override fun onError(e: Throwable) {
        disMissDialog()
        e.printStackTrace()
    }

    private fun disMissDialog() {
        if (mBaseView != null && needShowDialog) {
            mBaseView.hideDialog()
        }
    }

    private fun getTrace(t: Throwable): String {
        val stringWriter = StringWriter()
        val writer = PrintWriter(stringWriter)
        t.printStackTrace(writer)
        val buffer = stringWriter.buffer
        return buffer.toString()
    }
}