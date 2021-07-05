package com.qzl.mvvm.api

import com.qzl.base_common.api.ApiService
import com.qzl.mvvm.api.base.BaseRetrofitClient

/**
 * @desc 创建retrofit 已经提供API  Service
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/13 22:18
 */
object RetrofitClient : BaseRetrofitClient() {
    //创建service
    val apiService = retrofit.create(ApiService::class.java)
}