package com.qzl.base_common.base

/**
 * @desc listview列表加载状态
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/14 00:14
 */
enum class LoadState {
    LOADING,
    SUCCESS,
    EMPTY,
    ERROR,
    LOAD_MORE_LOADING,
    LOAD_MORE_EMPTY,
    LOAD_MORE_ERROR,
}