package com.lib_common.base

/**
Created by maozonghong

on 3/30/21
 **/
interface BaseResponse {

    /**
     * code返回码
     */
    fun getResponseCode(): Int

    /**
     *
     * 获取返回的提示信息
     */
    fun getResponseMsg(): String?


    fun isResponseSuccess():Boolean
}