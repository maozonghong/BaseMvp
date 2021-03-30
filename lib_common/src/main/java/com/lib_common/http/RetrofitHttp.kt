package com.lib_common.http

import com.lib_common.base.BaseConfig
import com.rxjava.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
Created by maozonghong

on 3/30/21
 **/

 class RetrofitHttp {

    companion object{

        const val CONNECT_TIMEOUT=60

        const val READ_TIMEOUT=60

        const val WRITE_TIMEOUT=60

        lateinit var okHttpClient:OkHttpClient

        lateinit var retrofit:Retrofit

        lateinit var httpInterceptor:HttpInterceptor

        @JvmStatic
        fun provideOkHttpClient(interceptor: HttpInterceptor): OkHttpClient {
            if (okHttpClient == null) {
                okHttpClient = OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT.toLong(),
                        TimeUnit.SECONDS).readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS).addInterceptor(interceptor) //添加拦截器
                .retryOnConnectionFailure(true).build()
            }
            return okHttpClient
        }


        @JvmStatic
        fun provideRetrofit(): Retrofit {
            //拦截器
            if (httpInterceptor == null) {
                httpInterceptor = HttpInterceptor()
            }
            //请求
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BaseConfig.BASE_URL)
                        .client(provideOkHttpClient(httpInterceptor))
                        .addConverterFactory(GsonConverterFactory.create()) //添加数据解析
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RXJava返回
                        .build()
            }
            return retrofit
        }
    }
}