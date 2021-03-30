package com.lib_common.utils

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 网络框架线程调度工具类
 */

class RxDispatcher {
    companion object {
        @JvmStatic
        private var ioMain: Any? = null

        @JvmStatic
        private var ioIo: Any? = null

        @JvmStatic
        fun <T> ioMain(): ObservableTransformer<T, T> {
            if (ioMain == null) {
                ioMain = NormalDispatcher<T>()
            }
            return ioMain as ObservableTransformer<T, T>
        }

        @JvmStatic
        fun <T> ioIo(): ObservableTransformer<T, T> {
            if (ioIo == null) {
                ioIo = IODispatcher<T>()
            }
            return ioIo as ObservableTransformer<T, T>
        }
    }
}

private class NormalDispatcher<T> : ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
    }
}

private class IODispatcher<T> : ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
    }
}


