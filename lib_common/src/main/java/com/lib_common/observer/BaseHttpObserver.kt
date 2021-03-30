package com.lib_common.observer

import android.net.ParseException
import com.google.gson.JsonParseException
import com.lib_common.base.BaseResponse
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException


/**
 * Copyright (c) 2021 All rights reserved
 * 网络访问基础观察者
 * @version 1.0
 */
abstract class BaseHttpObserver<T> : BaseObserver<T>, INetWork<T> {

    private val UNAUTHORIZED = 401
    private val FORBIDDEN = 403
    private val NOT_FOUND = 404
    private val REQUEST_TIMEOUT = 408
    private val INTERNAL_SERVER_ERROR = 500
    private val BAD_GATEWAY = 502
    private val SERVICE_UNAVAILABLE = 503
    private val GATEWAY_TIMEOUT = 504

    constructor(mView: IBaseView?, needShowDialog: Boolean) : super(mView, needShowDialog)

    constructor(mView: IBaseView?) : super(mView)

    override fun onError(e: Throwable) {
        super.onError(e)
        handleException(e)
    }

    private fun handleException(e: Throwable) {
        var errorStr = ""
        if (e is HttpException) {
            errorStr = when (e.code()) {
                UNAUTHORIZED, FORBIDDEN, NOT_FOUND,
                REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR,
                BAD_GATEWAY, SERVICE_UNAVAILABLE -> "网络错误,请检查网络并重试"
                else -> "网络错误,请检查网络并重试"
            }
            onFailed(-1, errorStr)
        } else if (e is RuntimeException) {
            errorStr = "服务器异常，请稍后重试"
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            errorStr = "解析错误，请重试"
        } else if (e is ConnectException) {
            errorStr = "连接失败，请检查网络并重试"
        } else if (e is SocketTimeoutException) {
            errorStr = "连接超时，请检查网络并重试"
        } else if (e is SSLHandshakeException) {
            errorStr = "证书验证失败，请重试"
        } else if (e is HttpException) {
            errorStr = "服务器异常，请重试"
        } else {
            errorStr = "请求失败，请检查网络并重试"
        }
        onFailed(-1, errorStr)
    }

    override fun onNext(baseResponse: T) {
        super.onNext(baseResponse)
        try {
            if(baseResponse is BaseResponse){
                if(baseResponse.isResponseSuccess()){
                    onSuccess(baseResponse)
                }else{
                    onFailed(baseResponse.getResponseCode(),baseResponse.getResponseMsg())
                }
            }else{
                onSuccess(baseResponse)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}