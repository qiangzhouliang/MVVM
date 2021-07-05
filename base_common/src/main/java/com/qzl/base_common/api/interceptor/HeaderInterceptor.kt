package com.qzl.base_common.api.interceptor

import com.qzl.base_tools.utils.StringHelper.isEmptyString
import com.qzl.base_tools.utils.StringHelper.toString
import com.qzl.base_tools.utils.aesnew.AESUtil
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.URLDecoder

/**
 * @author 强周亮(qiangzhouliang)
 * @desc 拦截器 设置请求头以及查查你成熟加密
 * @email 2538096489@qq.com
 * @time 2020/11/16 22:00
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder()
                .addHeader("authorization-code", "")
                .addHeader("Authorization", "")
                .build()
        if (request.method() == "GET") {
            val url = request.url().toString()
            if (url.contains("?")) {
                val basePath = url.substring(0, url.indexOf("?"))
                val params = url.substring(url.indexOf("?") + 1)
                val parameterArr = params.split("&".toRegex()).toTypedArray()
                val newBuilder = HttpUrl.parse(basePath)!!.newBuilder()
                for (parm in parameterArr) {
                    if (!isEmptyString(parm)) {
                        val keys = parm.split("=".toRegex()).toTypedArray()
                        if (keys.size > 1) {
                            //拦截器拿到的汉字，已经被编过码了,所以要转回去
                            val result = URLDecoder.decode(keys[1], "utf-8")
                            if (isGetExclude(url)) {
                                newBuilder.addEncodedQueryParameter(keys[0], result)
                            } else {
//                                var value = AESUtil.encrypt(toString(result))
                                val value = toString(result)
                                newBuilder.addQueryParameter(keys[0], value)
                            }
                        } else {
                            newBuilder.addQueryParameter(keys[0], "")
                        }
                    }
                }
                request = request.newBuilder().url(newBuilder.build()).build()
            }
        } else {
            val url = request.url().url().path
            //参数就要针对body做操作,我这里针对From表单做操作
            if (request.body() is FormBody) {
                // 构造新的请求表单
                val builder = FormBody.Builder()
                val body = request.body() as FormBody?
                //将以前的参数添加
                for (i in 0 until body!!.size()) {
                    val value = URLDecoder.decode(body.encodedValue(i))
                    if (!isExclude(url)) {
                        try {
                            builder.add(body.encodedName(i), AESUtil.encrypt(toString(value)))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        builder.add(body.encodedName(i), value)
                    }
                }
                request = request.newBuilder().post(builder.build()).build() //构造新的请求体
            }
        }
        return chain.proceed(request)
    }

    companion object {
        private fun isGetExclude(path: String): Boolean {
            return path.indexOf("reverse_geocoding/v3") != -1
        }

        private fun isExclude(path: String): Boolean {
            return path.indexOf("auth/updateVersion") != -1
        }
    }
}