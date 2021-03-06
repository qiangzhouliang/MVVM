package com.qzl.base_common.ui

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.qzl.base_common.R
import kotlinx.android.synthetic.main.head_control_panel.view.*

/**
 * @author 强周亮(Qzl)
 * @desc 头部导航
 * @email 2538096489@qq.com
 * @time 2019-05-28 19:22
 * @class HeadControlPanel
 */
class HeadControlPanel(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    override fun onFinishInflate() {
        super.onFinishInflate()
        if (!isInEditMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            head_view.visibility = View.VISIBLE
        }
    }

    fun initHeadPanel() {
        setMiddleTitle("首页")
    }

    fun setMiddleTitle(s: String) {
        var s = s
        midle_title.textSize = title_size
        if (s.length == 2) {
            s = s[0] + " " + s[1]
        }
        midle_title.text = s
    }

    fun getmLeftImage(): ImageView? {
        return menu_btn_img
    }

    fun getmRightText(): TextView? {
        return right_btn_text
    }

    fun getmRightImg(): ImageView? {
        right_btn_img.visibility = View.VISIBLE
        return right_btn_img
    }

    companion object {
        private val middle_title_size = 20f
        private val title_size = 18f
        private val default_background_color = Color.rgb(99, 220, 215)
    }
}
