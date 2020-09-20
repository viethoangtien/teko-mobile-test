package com.teko.hoangviet.androidtest.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.teko.hoangviet.androidtest.data.network.response.ProductResponse
import com.teko.hoangviet.androidtest.databinding.ItemSameTypeBinding

class SameTypeAdapter(context: Context, enableSelectedMode: Boolean) :
    EndlessLoadingRecyclerViewAdapter<ItemSameTypeBinding>(context, enableSelectedMode) {
    override val itemLayoutRes: Int
        get() = R.layout.item_same_type

    override fun initNormalViewHolder(binding: ItemSameTypeBinding): RecyclerView.ViewHolder? {
        return SameTypeViewHolder(binding)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder<*, Any>, position: Int) {
        holder.bind(getItem(position, ProductResponse::class.java)!!)
    }

    class SameTypeViewHolder(binding: ItemSameTypeBinding) :
        NormalViewHolder<ItemSameTypeBinding, ProductResponse>(binding) {
        override fun bind(data: ProductResponse) {
            binding.productResponse = data
        }

    }
}