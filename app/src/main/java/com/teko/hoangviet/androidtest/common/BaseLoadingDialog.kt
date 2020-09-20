package com.teko.hoangviet.androidtest.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.teko.hoangviet.androidtest.R

class BaseLoadingDialog private constructor(private var context: Context) {

    private var mDialog: Dialog? = null
    private var shown: Boolean? = null

    //Before initialize instance of class
    //Before class constructor
    companion object {
        @JvmStatic fun getInstance(context: Context) = BaseLoadingDialog(context)
    }

    //After primary constructor
    init {
        mDialog = Dialog(context)
        mDialog?.apply {
            setContentView(R.layout.layout_loading)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        shown = false
    }

    fun showLoadingDialog() {
        if (!shown!! && !(context as Activity).isFinishing) {
            force()
            mDialog?.show()
        }
    }

    fun hideLoadingDialog() {
        if (shown!! && (mDialog?.isShowing!!)) {
            initialization()
            mDialog?.dismiss()
        }
    }

    private fun force() {
        shown = true
    }

    private fun initialization() {
        shown = false
    }
}