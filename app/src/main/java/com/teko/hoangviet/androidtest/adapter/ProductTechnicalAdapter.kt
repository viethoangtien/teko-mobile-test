package com.teko.hoangviet.androidtest.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.teko.hoangviet.androidtest.data.network.response.ProductDescriptionResponse
import com.teko.hoangviet.androidtest.databinding.ItemDescriptionBinding

class ProductTechnicalAdapter(context: Context, enableSelectedMode: Boolean) :
    EndlessLoadingRecyclerViewAdapter<ItemDescriptionBinding>(context, enableSelectedMode) {
    override val itemLayoutRes: Int
        get() = R.layout.item_description

    override fun initNormalViewHolder(binding: ItemDescriptionBinding): RecyclerView.ViewHolder? {
        return ProductDescriptionViewHolder(binding)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder<*, Any>, position: Int) {
        holder.bind(getItem(position, ProductDescriptionResponse::class.java)!!)
    }

    class ProductDescriptionViewHolder(binding: ItemDescriptionBinding) :
        NormalViewHolder<ItemDescriptionBinding, ProductDescriptionResponse>(binding) {

        override fun bind(data: ProductDescriptionResponse) {
            binding.productDescriptionResponse = data
            when {
                adapterPosition == 0 -> {
                    binding.rlDescription.setBackgroundResource(R.drawable.bg_first_description)
                }
                adapterPosition % 2 == 1 -> {
                    binding.rlDescription.setBackgroundResource(R.color.md_white_1000)
                }
                else -> binding.rlDescription.setBackgroundResource(R.drawable.bg_normal_description)
            }
        }
    }
}