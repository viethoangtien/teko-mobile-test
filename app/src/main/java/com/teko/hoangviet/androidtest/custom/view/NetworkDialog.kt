package com.teko.hoangviet.androidtest.custom.view

import android.content.Context
import com.teko.hoangviet.androidtest.extension.onAvoidDoubleClick
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.view.alert.BaseDialog
import kotlinx.android.synthetic.main.layout_dialog_network.*

class NetworkDialog constructor(context: Context) : BaseDialog(context) {
    override val dialogView: Int
        get() = R.layout.layout_dialog_network
    private var onCloseClickListener: (() -> Unit)? = null

    override fun initListener() {
        super.initListener()
        with(dialog.imv_close) {
            onAvoidDoubleClick {
                dialog.dismiss()
                onCloseClickListener?.invoke()
            }
        }
    }

    fun setOnCloseClickListener(func: () -> Unit) {
        onCloseClickListener = func
    }

}