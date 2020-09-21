package com.teko.hoangviet.androidtest.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.teko.hoangviet.androidtest.data.local.model.Cart

@Dao
interface CartDao {

    @Query("SELECT * FROM cart WHERE cart.product_id = :productId")
    fun getCart(productId: Int): LiveData<Cart>

    @Query("SELECT * FROM cart WHERE cart.product_id = :productId")
    fun getCartByProductId(productId: Int): Cart?

    @Update
    fun update(cart: Cart)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(cart: Cart)

    fun updateCart(cartUpdate: Cart) {
        val cart = getCartByProductId(cartUpdate.productId)
        cart?.let {
            cart.quantity = cartUpdate.quantity
            update(cart)
        } ?: kotlin.run {
            insertCart(cartUpdate)
        }
    }
}