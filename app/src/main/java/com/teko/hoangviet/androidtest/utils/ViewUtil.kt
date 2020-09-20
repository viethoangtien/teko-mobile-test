package com.teko.hoangviet.androidtest.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.View

object ViewUtil {
    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    fun locateView(v: View?): Rect? {
        val locInt = IntArray(2)
        if (v == null) return null
        try {
            v.getLocationOnScreen(locInt)
        } catch (npe: NullPointerException) {
            return null
        }
        val location = Rect()
        location.left = locInt[0]
        location.top = locInt[1]
        location.right = locInt[0] + v.width
        location.bottom = locInt[1] + v.height
        return location
    }
}