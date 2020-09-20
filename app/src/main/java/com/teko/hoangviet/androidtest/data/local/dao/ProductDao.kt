package com.teko.hoangviet.androidtest.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAllProduct(): LiveData<List<ProductResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProduct(data: List<ProductResponse>)

    @RawQuery(observedEntities = [ProductResponse::class])
    fun search(query: SupportSQLiteQuery): LiveData<List<ProductResponse>>
}