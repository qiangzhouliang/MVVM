package com.qzl.mvvm.api.base

import java.lang.RuntimeException

/**
 * @desc 自定义异常
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/13 22:33
 */
data class ApiException(val code: Int, override val message: String?) : RuntimeException() {

}