package com.teko.hoangviet.androidtest.ui.product.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.teko.hoangviet.androidtest.base.ui.BaseViewModel
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import javax.inject.Inject

class DetailProductViewModel @Inject constructor(val context: Context) : BaseViewModel() {
    val detailProductLiveData = MutableLiveData<ProductResponse>()

    fun setDetailProduct(detailProduct: ProductResponse) {
        detailProductLiveData.value = detailProduct
    }

}