package com.lib_common.base


import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.lib_common.observer.IBaseView
import com.lib_common.utils.RxDispatcher
import com.rxjava.rxlife.ObservableLife
import com.rxjava.rxlife.RxLife
import com.zchu.rxcache.RxCache
import com.zchu.rxcache.data.CacheResult
import com.zchu.rxcache.kotlin.rxCache
import com.zchu.rxcache.stategy.CacheStrategy
import io.reactivex.Observable
import java.lang.ref.WeakReference
import java.lang.reflect.Type


/**
 * MVP 标准版基类presenter
 */
abstract class BasePresenter<V : IBaseView> : DefaultLifecycleObserver {

    var mView: WeakReference<V>? = null

    fun attachView(view: V) {
        if (mView == null) {
            mView = WeakReference(view)
        }
    }

    open fun getView(): V? {

        return mView?.get()
    }

    lateinit var mOwner: LifecycleOwner

    override fun onCreate(owner: LifecycleOwner) {
        mOwner = owner
    }


    override fun onDestroy(owner: LifecycleOwner) {
        mView?.clear()
        mView = null
        super.onDestroy(owner)
    }

    /**
     * rxjava绑定生命周期线程切换网络访问防止内存泄漏
     *
     * @param observable observable
     *
     * @return ObservableLife
     */
    protected fun <T> lifeScheduler(observable: Observable<T>): ObservableLife<T> {
        return observable.compose(RxDispatcher.ioMain())
            .`as`(RxLife.`as`(mOwner))
    }

    /**
     * 断网缓存
     * */
//    protected inline fun <reified T> cacheLifeScheduler(
//        net: Observable<T>,
//        key: String
//    ): ObservableLife<T> {
//        val type = getType<T>(key)
//        return lifeScheduler(Observable.just(type)
//            .filter {
//                !NetWorkUtils.isNetConnect(
//                    mView?.get()
//                        ?.activityContext
//                )
//            }
//            .flatMap { t: Type ->
//                RxCache.getDefault()
//                    .load<T>(key, t)
//                    .map { s: CacheResult<T> -> cacheResult(s) }
//            }
//            .concatWith(net.compose(
//                RxCache.getDefault()
//                    .transformObservable(
//                        key,
//                        type,
//                        CacheStrategy.firstRemote()
//                    )
//            )
//                .map { s: CacheResult<T> -> cacheResult(s) })
//            .take(1)
//        )
//    }
////
//    /**
//     * 读取缓存
//     */
//    protected fun <T> cacheResult(s: CacheResult<T>): T {
//        try {
//            return CacheResult.MapFunc<T>().apply(s)
//        } catch (ignored: Exception) {
//        }
//        throw OperationException(
//            OperationException.DEFAULT_STATUS,
//            AppDelegate.appComponent.application()
//                .getString(R.string.error_connect_failed)
//        )
//    }
//
    /**
     * 缓存获取具体类型
     */
//    protected inline fun <reified T> getType(key: String): Type {
//        val type: Type
//        if (cache.getValue(key) == null) {
//            type = object : TypeToken<T>() {}.type
//            cache.put(key, type)
//        } else {
//            type = cache.getValue(key) as Type
//        }
//        return type
//    }

    fun  getData(){
//        lifeScheduler(Observable.just(1).rxCache("", CacheStrategy.cacheAndRemote())).subscribe()
    }
}