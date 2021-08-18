package com.qzl.base_common.base

import android.content.pm.ActivityInfo
import android.graphics.BlendMode
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.qzl.base_common.R
import com.qzl.base_common.ui.HeadControlPanel
import com.qzl.base_tools.operate.CompleteQuit
import kotlinx.android.synthetic.main.common_view.*
import kotlinx.android.synthetic.main.head_control_panel.view.*

/**
 * @desc 公共基础activity
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/14 17:51
 */
abstract class BaseActivity : AppCompatActivity() {
    lateinit var headPanel: HeadControlPanel//头部显示区域

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        CompleteQuit.getInstance()?.pushActivity(this)
        //设置沉侵式状态栏
        setTranslucentActionBar()

        setContentView(R.layout.common_view)
        initCommonView()
        registerSDK()
        initView()
        initObserver()

        initHead(getModdleTitle(), isSetLeftImgBack(), headLeftClickListener())
    }

    fun initHead(title: String, isSetLeftImgBack: Boolean = true, onClickListener: View.OnClickListener) {
        headPanel = head_layout as HeadControlPanel
        headPanel.initHeadPanel()
        headPanel.setMiddleTitle(title)

        val headLayout = headPanel.head_layout_back
        if (isSetLeftImgBack) {
            headPanel.getmLeftImage()?.setImageResource(R.drawable.back)
            headLayout?.setOnClickListener(onClickListener)
        }
    }

    private fun initCommonView() {
        //添加子类布局
        LayoutInflater.from(this).inflate(getContentView(), fl_content_view)
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private fun initSwipeBackFinish() {
        var mSwipeBackHelper: BGASwipeBackHelper? = null
        mSwipeBackHelper = BGASwipeBackHelper(this, object : BGASwipeBackHelper.Delegate {
            /**
             * 滑动返回执行完毕，销毁当前 Activity
             */
            override fun onSwipeBackLayoutExecuted() {
                mSwipeBackHelper?.swipeBackward();
            }

            /**
             * 正在滑动返回
             * @param slideOffset 从 0 到 1
             */
            override fun onSwipeBackLayoutSlide(slideOffset: Float) {

            }

            /**
             * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
             */
            override fun onSwipeBackLayoutCancel() {

            }

            override fun isSupportSwipeBack(): Boolean {
                return true
            }

        })
        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true)
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true)
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true)
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true)
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true)
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f)
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false)
    }

    private fun setTranslucentActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.fitsSystemWindows = true
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    //获取页面展示内容
    abstract fun getContentView(): Int

    //初始化页面view
    protected open fun initView() {}

    //初始化观察者
    protected open fun initObserver() {}

    protected open fun registerSDK() {}
    protected open fun unRegisterSDK() {}

    //初始化title
    protected open fun getModdleTitle(): String {
        return "MVVM"
    }

    //是否设置左侧为放返回按钮
    protected open fun isSetLeftImgBack(): Boolean {
        return true
    }

    //设置左侧返回按钮点击事件
    protected open fun headLeftClickListener(): (View) -> Unit {
        return {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterSDK()
    }
}