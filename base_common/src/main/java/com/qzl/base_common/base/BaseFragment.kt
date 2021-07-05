package com.qzl.base_common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @desc 基础fragment类
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/14 22:52
 */
abstract class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(getLayoutResId(), container, false)
        initView(rootView)
        return rootView
    }

    /**
     * @desc 初始化view方法
     * @author 强周亮
     * @time 2020/11/14 22:55
     */
    open fun initView(rootView: View) {

    }

    //获取布局文件
    abstract fun getLayoutResId(): Int
}