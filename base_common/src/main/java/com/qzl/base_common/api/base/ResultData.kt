package com.qzl.base_common.api.base

import com.qzl.mvvm.api.base.ApiException

/**
 * @desc
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/13 22:28
 */
data class ResultData<T>(val success: Boolean, val code: Int, val message: String, val data: T) {

    companion object {
        const val CODE_SUCCESS = 10000
    }

    fun apiData(): T {
        //如果是成功的code，我们就返回数据，否则抛出异常
        when (code) {
            CODE_SUCCESS -> {
                return data
            }
            else -> {
                throw ApiException(code, message)
            }
        }
    }
}