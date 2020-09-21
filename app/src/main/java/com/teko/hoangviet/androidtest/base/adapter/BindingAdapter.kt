package com.teko.hoangviet.androidtest.adapter

import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.module.GlideApp
import com.teko.hoangviet.androidtest.utils.NumberUtil

class BindingAdapter {
    companion object {
        @BindingAdapter("android:src")
        @JvmStatic
        fun ImageView.bindImageView(
            url: String?
        ) {
            GlideApp.with(this.context)
                .load(url)
                .error(R.drawable.image_empty)
                .placeholder(R.drawable.image_empty)
                .into(this)
        }

        @BindingAdapter("priceDiscount")
        @JvmStatic
        fun TextView.bindPriceDiscount(
            price: Double?
        ) {
            price?.let {
                text = NumberUtil.formatValue(it)
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

        @BindingAdapter("price")
        @JvmStatic
        fun TextView.bindPrice(
            price: Double?
        ) {
            price?.let {
                text = NumberUtil.formatValue(it)
            }
        }
    }
}