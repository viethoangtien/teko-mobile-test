package com.teko.hoangviet.androidtest.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import androidx.recyclerview.widget.RecyclerView
import com.teko.hoangviet.androidtest.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.databinding.ItemListProductBinding
import java.util.*

class ListProductAdapter(context: Context, enableSelectedMode: Boolean) :
    EndlessLoadingRecyclerViewAdapter<ItemListProductBinding>(context, enableSelectedMode) {
    private var textSearch: List<String> = arrayListOf()

    fun highlightTextSearch(textSearch: List<String>) {
        this.textSearch = textSearch
    }

    override val itemLayoutRes: Int
        get() = R.layout.item_list_product

    override fun initNormalViewHolder(binding: ItemListProductBinding): RecyclerView.ViewHolder? {
        return NotificationViewHolder(binding)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder<*, Any>, position: Int) {
        holder.bind(getItem(position, ProductResponse::class.java)!!)
    }

    inner class NotificationViewHolder(binding: ItemListProductBinding) :
        NormalViewHolder<ItemListProductBinding, ProductResponse>(binding) {
        override fun bind(data: ProductResponse) {
            binding.productResponse = data
            if (textSearch.isNotEmpty()) {
                textSearch.forEach { key ->
                    if (key.isNotEmpty()) {
                        val startPos =
                            data.name?.toLowerCase(Locale.US)?.indexOf(key.toLowerCase(Locale.US))!!
                        val endPos = startPos + key.length
                        if (startPos != -1) {
                            val spannable = SpannableString(data.name)
                            val blueColor =
                                ColorStateList(
                                    arrayOf(intArrayOf()),
                                    intArrayOf(Color.BLUE)
                                )
                            val highlightSpan =
                                TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null)
                            spannable.setSpan(
                                highlightSpan,
                                startPos,
                                endPos,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            binding.tvTitle.text = spannable
                        }
                    }
                }
            }
        }
    }
}