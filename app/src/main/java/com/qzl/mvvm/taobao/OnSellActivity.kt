package com.qzl.mvvm.taobao

import android.graphics.Rect
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.qzl.mvvm.R
import com.qzl.mvvm.adapter.OnSellListAdapter
import com.qzl.base_common.base.LoadBaseActivity
import com.qzl.base_common.base.LoadState
import kotlinx.android.synthetic.main.activity_on_sell.*

/**
 * @author 强周亮(qiangzhouliang)
 * @desc 特惠页面
 * @email 2538096489@qq.com
 * @time 2020/11/13 21:11
 */
class OnSellActivity : LoadBaseActivity<OnSellViewModel>() {

    private val mAdapter by lazy { OnSellListAdapter() }

    override fun getContentView() = R.layout.activity_on_sell

    /**
     * 观察数据变化
     */
    override fun initObserver() {
        mViewModel.apply {
            contentList.observe(this@OnSellActivity, Observer {
                //内容列表更新
                //更新适配器
                mAdapter.setData(it)
            })
            loadState.observe(this@OnSellActivity, {
                hideAll()
                //根据加载的状态来更新UI的显示
                when (it) {
                    LoadState.LOADING -> {
                        loadingView.visibility = View.VISIBLE
                    }
                    LoadState.EMPTY -> {
                        emptyView.visibility = View.VISIBLE
                    }
                    LoadState.ERROR -> {
                        errorView.visibility = View.VISIBLE
                    }
                    else -> {
                        contentRefresh.visibility = View.VISIBLE
                        contentRefresh.finishLoadmore()
                    }
                }

                when (it) {
                    LoadState.LOAD_MORE_ERROR -> {
                        Toast.makeText(this@OnSellActivity, "网络不佳，加载出错...", Toast.LENGTH_LONG).show()
                    }
                    LoadState.LOAD_MORE_EMPTY -> {
                        Toast.makeText(this@OnSellActivity, "没有更多数据了。。。", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }.loadContent()
    }

    /**
     * 初始化view
     */
    override fun initView() {
        contentRefresh.run {
            setEnableLoadmore(true)
            setEnableRefresh(false)
            setEnableOverScroll(true)
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                    //去执行加载更多
                    mViewModel.loadMore()
                }
            })
        }
        errorView.setOnClickListener {
            //重新加载
            mViewModel.loadContent()
        }
        contetListRv.run {
            layoutManager = LinearLayoutManager(this@OnSellActivity)
            adapter = mAdapter
            //添加分割线
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                    outRect.apply {
                        top = 8
                        left = 8
                        right = 8
                        bottom = 8
                    }
                }
            })
        }
    }

    private fun hideAll() {
        contentRefresh.visibility = View.GONE
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }

    override fun getViewModelClass(): Class<OnSellViewModel> {
        return OnSellViewModel::class.java
    }
}