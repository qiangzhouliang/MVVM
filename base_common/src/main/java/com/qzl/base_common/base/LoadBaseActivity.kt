package com.qzl.base_common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @desc 带有网络加载 model实例化的基础类
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/14 19:09
 */
abstract class LoadBaseActivity<T : ViewModel> : BaseActivity() {
    //如果要使用MViewMode，必须要传递这个泛型
    protected val mViewModel by lazy {
        ViewModelProvider(this).get(getViewModelClass())
    }

    abstract fun getViewModelClass(): Class<T>
}