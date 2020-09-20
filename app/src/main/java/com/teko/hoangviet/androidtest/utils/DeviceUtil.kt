package com.teko.hoangviet.androidtest.utils

import android.app.Activity
import android.content.Context
import android.view.View
import com.teko.hoangviet.androidtest.extension.connectivityManager
import com.teko.hoangviet.androidtest.extension.inputManager

object DeviceUtil {
    fun hideSoftKeyBoard(activity: Activity) {
        val inputMethodManager = activity.inputManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isConnectedToNetwork(context: Context): Boolean {
        val mConnectivityManager = context.connectivityManager
        if (mConnectivityManager != null) {
            val activeNetwork = mConnectivityManager.activeNetworkInfo
            return (activeNetwork != null && activeNetwork.isConnected)
        }
        return false
    }
}