package com.qzl.base_tools.utils

import android.os.Handler
import android.os.Looper

/**
 * @desc 是线程运行在主线程中
 * @author Qzl
 * @email 2538096489@qq.com
 * @time 2019-02-20 19:02
 * @class QGMusicKotlin
 * @package qzl.com.qgmusickotlin.util
 */
object ThreadUtil {
    val handler = Handler(Looper.getMainLooper())

    /**
     * 运行在主线程中
     */
    fun runOnMainThread(runnable: Runnable) {
        handler.post(runnable)
    }
}