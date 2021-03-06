package com.qzl.base_tools.utils

import android.app.Activity
import android.app.ActivityGroup
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * https://github.com/guoyoujin/MySnackBar
 */

class ScreenUtil private constructor() {
    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {
        private var mStatusHeight = -1
        fun dp2px(ctx: Context, dpValue: Float): Int {
            val density = ctx.resources.displayMetrics.density
            return (dpValue * density + 0.5f).toInt()
        }

        fun sp2px(ctx: Context, spValue: Float): Int {
            val scaledDensity = ctx.resources.displayMetrics.scaledDensity
            return (spValue * scaledDensity + 0.5f).toInt()
        }

        /**
         * 获取屏幕的宽度
         * @param context
         * @return
         */
        fun getScreenWidth(context: Context?): Int {
            val manager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            return display.width
        }

        /**
         * 获取屏幕的高度
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val manager = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            return display.height
        }

        /**
         * 获得状态栏的高度
         * @param context
         * @return mStatusHeight
         */
        fun getStatusHeight(context: Context): Int {
            if (mStatusHeight != -1) {
                return mStatusHeight
            }
            try {
                val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    mStatusHeight = context.resources.getDimensionPixelSize(resourceId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return mStatusHeight
        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         * @param activity
         * @return bp
         */
        fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache ?: return null
            val frame = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(frame)
            val statusBarHeight = frame.top
            val bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, bmp.width, bmp.height - statusBarHeight)
            view.destroyDrawingCache()
            view.isDrawingCacheEnabled = false

            return bp
        }


        /**
         * 设置view margin
         * @param v
         * @param l
         * @param t
         * @param r
         * @param b
         */
        fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
            if (v.layoutParams is ViewGroup.MarginLayoutParams) {
                val p = v.layoutParams as ViewGroup.MarginLayoutParams
                p.setMargins(l, t, r, b)
                v.requestLayout()
            }
        }

        /**
         * @desc 获取底部虚拟导航的高度
         * @author Qzl
         * @time 2018-08-27 15:04
         */
        @JvmStatic
        fun getVirtualBarHeight(context: Context): Int {
            var vh = 0
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val dm = DisplayMetrics()
            try {
                val c = Class.forName("android.view.Display")
                val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
                method.invoke(display, dm)
                vh = dm.heightPixels - display.height
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return vh
        }

        /**
         * @desc 设置屏幕背景颜色
         * @author 强周亮
         * @time 2018-12-11 14:52
         */
        @JvmStatic
        fun backgroundAlpha(activity: Activity, alpha: Float) {
            try {
                val lp = activity.window.attributes
                //0.0-1.0
                lp.alpha = alpha
                activity.window.attributes = lp
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
