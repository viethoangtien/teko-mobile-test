package com.teko.hoangviet.androidtest.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.teko.hoangviet.androidtest.data.network.response.ListResponse
import com.teko.hoangviet.androidtest.base.ui.BaseViewModel
import com.teko.hoangviet.androidtest.data.network.response.ProductResponse
import javax.inject.Inject

class MainViewModel @Inject constructor(val context: Context) : BaseViewModel() {
    val listProductLiveData = MutableLiveData<ListResponse<ProductResponse>>()

    fun getListProduct() {
        compositeDisposable.add(
            repository.listProduct()
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