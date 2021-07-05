package com.qzl.mvvm.taobao

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qzl.bas_model.MapData
import com.qzl.base_common.base.LoadState
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException

/**
 * @desc viewmodel层，将model和view绑定起来
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/13 21:10
 */
class OnSellViewModel : ViewModel() {
    //获取业务处理类
    private val onSellRepository by lazy { OnSellRepository() }

    //监听加载状态
    val loadState = MutableLiveData<LoadState>()

    companion object {
        //默认第一页
        const val DEFAULT_PAGE = 1
    }

    //当前页
    private var mCurrentPage = DEFAULT_PAGE

    //判断当前是加载更多 还是加载数据
    private var isLoadMore = false;

    //存储页面内容
    val contentList = MutableLiveData<MutableList<MapData>>()

    init {

    }

    /**
     * 加载更多
     */
    fun loadMore() {
        println("loadMore...")
        isLoadMore = true
        loadState.postValue(LoadState.LOAD_MORE_LOADING)
        //去加载更多的内容
        mCurrentPage++
        this.listContentByPage(mCurrentPage)
    }

    /**
     * 加载首页内容
     */
    fun loadContent() {
        isLoadMore = false
        loadState.postValue(LoadState.LOADING)
        this.listContentByPage(mCurrentPage)
    }

    //查询页面数据
    private fun listContentByPage(page: Int) {
        //协程挂载的方式调用
        viewModelScope.launch {
            try {
                hashMapOf<String, String>("page" to "1")
                val onSellList = onSellRepository.getOnSellList(hashMapOf<String, String>("page" to "$page"))
                //前面就有的数据,进行数据叠加
                val oldValue = contentList.value ?: mutableListOf()
                oldValue.addAll(onSellList.tbk_dg_optimus_material_response.result_list.map_data)
                contentList.postValue(oldValue)
                if (contentList.value?.size == 0) {
                    if (isLoadMore) {
                        loadState.postValue(LoadState.LOAD_MORE_EMPTY)
                    } else {
                        loadState.postValue(LoadState.EMPTY)
                    }
                } else {
                    loadState.postValue(LoadState.SUCCESS)
                }
            } catch (e: Exception) {
                mCurrentPage--
                e.printStackTrace()
                if (e is NullPointerException) {
                    //没有更多内容的时候，会有一个空指针回来
                    loadState.value = LoadState.LOAD_MORE_EMPTY
                } else {
                    if (isLoadMore) {
                        loadState.postValue(LoadState.LOAD_MORE_ERROR)
                    } else {
                        loadState.postValue(LoadState.ERROR)
                    }
                }
            }
        }
    }


}