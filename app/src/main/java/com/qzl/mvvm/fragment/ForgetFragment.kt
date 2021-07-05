package com.qzl.mvvm.fragment

import android.view.View
import androidx.navigation.fragment.findNavController
import com.qzl.mvvm.R
import com.qzl.base_common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_forget.view.*

/**
 * @desc
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/14 22:59
 * @class MVVM
 * @package com.qzl.mvvm.fragment
 */
class ForgetFragment : BaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_forget

    override fun initView(rootView: View) {
        super.initView(rootView)
        //返回
        rootView.forgetBack.setOnClickListener {
            // 返回的一个箭头 适用于这个
            findNavController().navigateUp()
            //当用户按返回按钮时，适用这个
//            findNavController().popBackStack()
        }

        rootView.toRegistPage.setOnClickListener {
            //跳转到注册页
            findNavController().navigate(R.id.forget_to_regist_fragment)
        }
    }
}