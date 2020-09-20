package com.teko.hoangviet.androidtest.custom.view

import android.content.Context
import android.util.AttributeSet
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.viewgroup.BaseCustomViewConstrainLayout

class QuantityView(context: Context?, attrs: AttributeSet?) :
    BaseCustomViewConstrainLayout(context, attrs) {
    override val layoutRes: Int
        get() = R.layout.layout_quantity

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {

    }
}