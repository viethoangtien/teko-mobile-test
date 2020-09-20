package com.teko.hoangviet.androidtest.ui.product.detail.productdescription

import com.beetech.productmanagement.di.annotation.LayoutId
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.ui.BaseFragment
import com.teko.hoangviet.androidtest.databinding.FragmentProductDescriptionBinding

@LayoutId(R.layout.fragment_product_description)
class ProductDescriptionFragment : BaseFragment<FragmentProductDescriptionBinding>() {


    override fun backFromAddFragment() {

    }

    override fun backPressed(): Boolean {
        return false
    }

    override fun initView() {
    }

    override fun initViewModel() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }
}