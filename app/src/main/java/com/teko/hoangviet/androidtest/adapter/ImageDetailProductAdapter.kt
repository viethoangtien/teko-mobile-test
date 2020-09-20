package com.teko.hoangviet.androidtest.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.teko.hoangviet.androidtest.databinding.ItemImageDetailProductBinding

class ImageDetailProductAdapter(context: Context, enableSelectedMode: Boolean) :
    EndlessLoadingRecyclerViewAdapter<ItemImageDetailProductBinding>(context, enableSelectedMode) {
    override val itemLayoutRes: Int
        get() = R.layout.item_image_detail_product

    override fun initNormalViewHolder(binding: ItemImageDetailProductBinding): RecyclerView.ViewHolder? {
        return ImageViewHolder(binding)
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder<*, Any>, position: Int) {
        holder.bind(getItem(position, String::class.java)!!)
    }

    class ImageViewHolder(binding: ItemImageDetailProductBinding) :
        NormalViewHolder<ItemImageDetailProductBinding, String>(binding) {
        override fun bind(data: String) {
            binding.imageUrl = data
        }
    }
}