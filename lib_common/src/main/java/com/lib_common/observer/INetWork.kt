package com.lib_common.observer


/**
 * @author Scott
 * 网络请求接口
 */
interface INetWork<T> {
    //成功回调
    fun onSuccess(response: T)

    //失败回调
    fun onFailed(code:Int, message:String?)
}