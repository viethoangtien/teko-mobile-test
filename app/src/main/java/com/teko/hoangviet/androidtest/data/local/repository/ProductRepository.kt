package com.teko.hoangviet.androidtest.data.local.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.sqlite.db.SimpleSQLiteQuery
import com.teko.hoangviet.androidtest.data.local.dao.ProductDao
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(val productDao: ProductDao) {
    fun insertAll(listData: List<ProductResponse>) {
        productDao.insertAllProduct(listData)
    }

    fun getAllProduct() = productDao.getAllProduct()

    fun search(listKeyWord: List<String>): LiveData<List<ProductResponse>> {
        val query = StringBuilder("SELECT * FROM product")
        if (listKeyWord.isNotEmpty()) {
            query.append(" WHERE")
        }
        listKeyWord.forEachIndexed { index, key ->
            query.append(" name LIKE '${key}' OR code LIKE '${key}'")
            if (index != listKeyWord.size - 1) {
                query.append(" OR")
            }
        }
        val simpleSQLiteQuery = SimpleSQLiteQuery(query.toString())
        return productDao.search(simpleSQLiteQuery)
    }

}