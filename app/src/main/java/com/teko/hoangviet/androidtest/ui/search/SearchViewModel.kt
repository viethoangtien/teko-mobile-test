package com.teko.hoangviet.androidtest.ui.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.teko.hoangviet.androidtest.base.ui.BaseViewModel
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.data.local.repository.ProductRepository
import com.teko.hoangviet.androidtest.extension.backgroundThreadProcess
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    val context: Context,
    private val productRepository: ProductRepository
) : BaseViewModel() {
    @SuppressLint("CheckResult")
    fun search(keyword: String) = productRepository.search("%${keyword}%")

    val listProduct = productRepository.getAllProduct()
}