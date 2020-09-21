package com.teko.hoangviet.androidtest.custom.view

import android.content.Context
import android.util.AttributeSet
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.viewgroup.BaseCustomViewConstrainLayout
import com.teko.hoangviet.androidtest.extension.onAvoidDoubleClick
import kotlinx.android.synthetic.main.layout_quantity.view.*

class QuantityView(context: Context?, attrs: AttributeSet?) :
    BaseCustomViewConstrainLayout(context, attrs) {
    private var quantityListener: ((Int) -> Unit)? = null

    fun setOnQuantityListener(func: (Int) -> Unit) {
        quantityListener = func
    }

    override val layoutRes: Int
        get() = R.layout.layout_quantity

    override fun initView() {

    }

    override fun initData() {
    }

    override fun initListener() {
        imv_remove.setOnClickListener {
            val current = tv_selected.text.toString().toInt()
            if (current > 1) {
                quantityListener?.invoke(current - 1)
                tv_selected.text = (current - 1).toString()
            }
        }
        imv_add.setOnClickListener {
            val current = tv_selected.text.toString().toInt()
            quantityListener?.invoke(current + 1)
            tv_selected.text = (current + 1).toString()
        }
    }

    fun setQuantity(quantity: Int) {
        tv_selected.text = quantity.toString()
    }
}