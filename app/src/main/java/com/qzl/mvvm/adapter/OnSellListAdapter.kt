package com.qzl.mvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qzl.bas_model.MapData
import com.qzl.mvvm.R
import kotlinx.android.synthetic.main.item_on_sell.view.*

/**
 * @desc
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/13 23:08
 * @class MVVM
 * @package com.qzl.mvvm.adapter
 */
class OnSellListAdapter : RecyclerView.Adapter<OnSellListAdapter.InnerHolder>() {
    private val contentList = arrayListOf<MapData>()

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_on_sell, parent, false)
        return InnerHolder(itemView)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.itemView.apply {
            with(contentList[position]) {
                itemTitleTv.text = title
                offPriseTv.text = String.format("%.2f", zk_final_price.toFloat() - coupon_amount)
                Glide.with(context).load("https:$pict_url").into(picIv)
            }
        }
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    fun setData(it: List<MapData>?) {
        contentList.clear()
        it?.let { it1 -> contentList.addAll(it1) }
        notifyDataSetChanged()
    }
}