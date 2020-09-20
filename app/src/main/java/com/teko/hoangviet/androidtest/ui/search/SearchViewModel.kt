package com.teko.hoangviet.androidtest.ui.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import com.teko.hoangviet.androidtest.base.ui.BaseViewModel
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.data.local.repository.ProductRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    val context: Context,
    private val productRepository: ProductRepository
) : BaseViewModel() {
    @SuppressLint("CheckResult")
    fun search(keyword: String): LiveData<List<ProductResponse>> {
        val listKey = keyword.split(' ')
        return productRepository.search(listKey.map {
            "%${it}%"
        })
    }


    val listProduct = productRepository.getAllProduct()
}