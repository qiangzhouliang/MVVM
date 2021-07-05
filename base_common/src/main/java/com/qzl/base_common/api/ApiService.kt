package com.qzl.base_common.api

import com.qzl.bas_model.OnSellData
import com.qzl.base_common.api.base.ResultData
import com.qzl.mvvm.api.base.BaseApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @desc
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/13 22:20
 */
interface ApiService : BaseApi {

    @GET("onSell/{page}")
    suspend fun getOnsellList(@Path("page") page: Int, @QueryMap paramMap: Map<String, String>): ResultData<OnSellData>
}