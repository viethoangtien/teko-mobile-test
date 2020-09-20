package com.teko.hoangviet.androidtest.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAllProduct(): LiveData<List<ProductResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProduct(data: List<ProductResponse>)

    @Query("SELECT * FROM product WHERE name LIKE :listKeyWord")
    fun search(listKeyWord: String): LiveData<List<ProductResponse>>
}