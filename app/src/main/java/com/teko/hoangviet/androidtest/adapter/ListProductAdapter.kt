package com.teko.hoangviet.androidtest.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.teko.hoangviet.androidtest.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.databinding.ItemListProductBinding

class ListProductAdapter (context: Context, enableSelectedMode: Boolean) :
    EndlessLoadingRecyclerViewAdapter<ItemListProductBinding>(context, enableSelectedMode) {
    override val itemLayoutRes: Int
        get() = R.layout.item_list_product

    override fun initNormalViewHolder(binding: ItemListProductBinding): RecyclerView.ViewHolder? {
        return NotificationViewHolder(binding)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder<*, Any>, position: Int) {
        holder.bind(getItem(position, ProductResponse::class.java)!!)
    }

    class NotificationViewHolder(binding: ItemListProductBinding) :
        NormalViewHolder<ItemListProductBinding, ProductResponse>(binding) {
        override fun bind(data: ProductResponse) {
            binding.productResponse = data
        }
    }
}