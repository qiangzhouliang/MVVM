package com.qzl.mvvm.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.qzl.mvvm.R
import com.qzl.base_common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * @desc
 * @anthor qiangzhouliang
 * @email 2538096489@qq.com
 * @time 2020/11/14 22:59
 */
class LoginFragment : BaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_login

    override fun initView(rootView: View) {
        super.initView(rootView)
        rootView.toRegistPage.setOnClickListener {
            //传递参数
            val bundle = Bundle()
            bundle.putString("userName", "张三")
            //跳转到注册页面
            findNavController().navigate(R.id.to_regist_fragment, bundle)
        }

        rootView.toForgetPage.setOnClickListener {
            findNavController().navigate(R.id.to_forget_fragment)
        }

        rootView.toAgreementPage.setOnClickListener {
            findNavController().navigate(R.id.to_agreement_activity)
        }
    }
}