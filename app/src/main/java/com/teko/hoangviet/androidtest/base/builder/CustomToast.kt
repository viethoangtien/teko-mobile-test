package com.teko.hoangviet.androidtest.base.builder

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast

class CustomToast private constructor() {
    companion object {
        private var toastInstance: Toast? = null
        fun getInstance(context: Context): Toast {
            if (toastInstance == null) toastInstance =
                Toast.makeText(context, "", Toast.LENGTH_LONG)
            return toastInstance!!
        }
    }

//    fun show() {
//        toastInstance?.show()
//    }

    class Builder(context: Context) {
        private var toast: Toast = getInstance(context)
        private var toastView: View = getInstance(context).view
        private var toastTextView: TextView

        init {
            toastTextView = toastView.findViewById(android.R.id.message)
        }

        fun setText(message: String) = apply {
            toastTextView.text = message
        }

        fun setTextSize(size: Float) = apply {
            toastTextView.textSize = size
        }

        fun setTextColor(color: Int) = apply {
            toastTextView.setTextColor(color)
        }

        fun setDrawables(left: Int, right: Int, top: Int, bottom: Int) = apply {
            toastTextView.setCompoundDrawablesWithIntrinsicBounds(
                left, right, top, bottom
            )
        }

        fun setGravity(gravity: Int) = apply {
            toastTextView.gravity = gravity
        }

        fun setDrawablePadding(padding: Int) = apply {
            toastTextView.compoundDrawablePadding = padding
        }

        fun setBackgroundColor(color: Int) = apply {
            toastView.setBackgroundColor(color)
        }

        fun setDuration(duration: Int) = apply {
            toast.duration = duration
        }

        fun apply() {
            toast.show()
        }
    }
}