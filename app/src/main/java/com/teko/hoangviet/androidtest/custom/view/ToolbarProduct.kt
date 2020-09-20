package com.teko.hoangviet.androidtest.custom.view

import android.content.Context
import android.util.AttributeSet
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.viewgroup.BaseCustomViewConstrainLayout
import com.teko.hoangviet.androidtest.data.network.response.ProductResponse
import com.teko.hoangviet.androidtest.utils.NumberUtil
import kotlinx.android.synthetic.main.layout_toolbar_product.view.*

class ToolbarProduct(context: Context?, attrs: AttributeSet?) :
    BaseCustomViewConstrainLayout(context, attrs) {
    override val layoutRes: Int
        get() = R.layout.layout_toolbar_product

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    fun setData(productResponse: ProductResponse) {
        tv_name_product.text = productResponse.name
        productResponse.price?.let {
            tv_price.text = "${NumberUtil.formatValue((it * 100000).toInt().toString())} đ"
        }
    }

}