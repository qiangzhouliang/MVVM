package com.qzl.mvvm.fragment

import android.view.View
import androidx.navigation.fragment.findNavController
import com.qzl.mvvm.R
import com.qzl.base_common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.view.*

/**
 * @desc
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/14 22:59
 */
class RegisterFragment : BaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_register

    override fun initView(rootView: View) {
        super.initView(rootView)
        rootView.toLoginPage.setOnClickListener {
            findNavController().navigate(R.id.to_login_page)
        }

        println("传递的参数：" + arguments?.get("userName"))
    }
}