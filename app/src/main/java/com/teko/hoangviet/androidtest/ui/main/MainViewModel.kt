package com.teko.hoangviet.androidtest.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.teko.hoangviet.androidtest.data.network.response.ListResponse
import com.teko.hoangviet.androidtest.base.ui.BaseViewModel
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.data.local.repository.ProductRepository
import com.teko.hoangviet.androidtest.extension.backgroundThreadProcess
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val context: Context,
    private val productRepository: ProductRepository
) : BaseViewModel() {
    val listProductLiveData = MutableLiveData<ListResponse<ProductResponse>>()

    fun getListProduct() {
        compositeDisposable.add(
            repository.listProduct()
                .subscribeOn(Schedulers.io())
                .map {
                    productRepository.insertAll(it)
                    it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    listProductLiveData.value = ListResponse.loading()
                }
                .subscribe({ listProduct ->
                    listProduct?.let {
                        listProductLiveData.value = ListResponse.success(it)
                    }
                }, {
                    listProductLiveData.value = ListResponse.error(it)
                })
        )
    }
}