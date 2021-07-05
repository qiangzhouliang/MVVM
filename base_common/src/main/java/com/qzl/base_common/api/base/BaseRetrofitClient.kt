package com.qzl.mvvm.api.base

import com.qzl.base_common.api.interceptor.HeaderInterceptor
import com.qzl.base_common.api.retrofit_converter.IGsonFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @desc 基础网络请求配置客户端
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/14 19:51
 */
open class BaseRetrofitClient {

    //创建网络请求客户端
    val okHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .callTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(30, TimeUnit.SECONDS)//超时时间15S
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    //创建retrofit基础信息
    val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BaseApi.BASE_URL)
                .addConverterFactory(IGsonFactory.create())
//        .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }
}