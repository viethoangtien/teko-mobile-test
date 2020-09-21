package com.teko.hoangviet.androidtest.data.local.repository

import com.teko.hoangviet.androidtest.data.local.dao.CartDao
import com.teko.hoangviet.androidtest.data.local.model.Cart
import javax.inject.Inject

class CartRepository @Inject constructor(val cartDao: CartDao) {
    fun getCart(productId: Int) = cartDao.getCart(productId)
    fun saveCart(cart: Cart) {
        cartDao.updateCart(cart)
    }

}