package com.lib_common.http

import android.util.Log
import com.rxjava.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import okio.IOException
import java.nio.charset.Charset


/**
Created by maozonghong

on 3/30/21
 **/
class HttpInterceptor:Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        // LogUtils.d( "device_id="+BaseApplication.device);
        //添加请求头
        // LogUtils.d( "device_id="+BaseApplication.device);
        //添加请求头
        val updateRequest: Request = request.newBuilder().header("token", "111")
                .addHeader("device_id", "88888")
                .addHeader("deviceType", "Android")
                .addHeader("screenSize", "y:1080"
                        + "x:720")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build()

        val response = chain.proceed(updateRequest)


        if (BuildConfig.DEBUG) {
            //添加打印服务器返回的数据
            Log.e(updateRequest.url.toUri().rawPath,"请求参数：${getRequestInfo(updateRequest)}," +
                    "响应:${getResponseInfo(response)}")
        }

        return response
    }


    /**
     * 打印请求消息
     *
     * @param request 请求的对象
     */
    private fun getRequestInfo(request: Request?): String? {
        var str = ""
        if (request == null) {
            return str
        }
        val requestBody = request.body?: return str
        try {
            val bufferedSink = Buffer()
            requestBody.writeTo(bufferedSink)
            val charset: Charset = Charset.forName("utf-8")
            str = bufferedSink.readString(charset)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return str
    }

    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private fun getResponseInfo(response: Response?): String? {
        var str = ""
        if (response == null || !response.isSuccessful) {
            return str
        }
        val responseBody = response.body
        val contentLength = responseBody!!.contentLength()
        val source = responseBody.source()
        try {
            source.request(Long.MAX_VALUE) // Buffer the entire body.
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val buffer: Buffer = source.buffer()
        val charset: Charset = Charset.forName("utf-8")
        if (contentLength != 0L) {
            str = buffer.clone().readString(charset)
        }
        return str
    }
}