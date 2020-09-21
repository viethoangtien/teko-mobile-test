package com.teko.hoangviet.androidtest.ui.product.detail

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.teko.hoangviet.androidtest.base.ui.BaseViewModel
import com.teko.hoangviet.androidtest.data.local.model.Cart
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.data.local.repository.CartRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailProductViewModel @Inject constructor(
    val context: Context,
    val cartRepository: CartRepository
) : BaseViewModel() {
    val detailProductLiveData = MutableLiveData<ProductResponse>()
    var quantityBadge = MutableLiveData<Int>()

    fun setDetailProduct(detailProduct: ProductResponse) {
        detailProductLiveData.value = detailProduct
    }

    fun getCart(productId: Int) = cartRepository.getCart(productId)

    @SuppressLint("CheckResult")
    fun saveCart(cart: Cart) {
        compositeDisposable.add(
            Completable.fromCallable {
                cartRepository.saveCart(cart)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    quantityBadge.value = cart.quantity
                }
        )
    }

}