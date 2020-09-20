package com.teko.hoangviet.androidtest.base.view.alert

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface

class BaseAlertDialog(val context: Context) {
    private lateinit var dialogBuilder: AlertDialog.Builder
    var isCancelable: Boolean = true
    lateinit var title: String
    lateinit var message: String
    lateinit var positiveTitle: String
    lateinit var negativeTitle: String
    private var onPositiveListener: (() -> Unit)? = null
    private var onNegativeListener: (() -> Unit)? = null

    fun create(): Dialog {
        dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.apply {
            setCancelable(isCancelable)
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveTitle) { dialogInterface: DialogInterface, position: Int ->
                onPositiveListener?.invoke()
            }
            setNegativeButton(negativeTitle) { dialogInterface: DialogInterface, position: Int ->
                onNegativeListener?.invoke()
            }
        }
        val dialog = dialogBuilder.create()
        dialog.show()
        return dialog
    }

    fun setOnPositiveListener(func: () -> Unit) {
        this.onPositiveListener = func
    }

    fun setOnNegativeListener(func: () -> Unit) {
        this.onNegativeListener = func
    }
}