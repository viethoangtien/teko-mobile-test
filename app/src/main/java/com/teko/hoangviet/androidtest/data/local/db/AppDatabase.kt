package com.teko.hoangviet.androidtest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teko.hoangviet.androidtest.data.local.dao.CartDao
import com.teko.hoangviet.androidtest.data.local.dao.ProductDao
import com.teko.hoangviet.androidtest.data.local.model.Cart
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse

@Database(entities = [ProductResponse::class, Cart::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}