package com.qzl.mvvm.taobao

import com.qzl.mvvm.api.RetrofitClient

/**
 * @desc负责获取数据请求 mode层
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/13 21:42
 */
class OnSellRepository {
    suspend fun getOnSellList(paramMap: Map<String, String>) =
            RetrofitClient.apiService.getOnsellList(1, paramMap).apiData()
}