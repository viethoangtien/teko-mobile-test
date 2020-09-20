package com.teko.hoangviet.androidtest.data.local.repository

import com.teko.hoangviet.androidtest.data.local.dao.ProductDao
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(val productDao: ProductDao) {
    fun insertAll(listData: List<ProductResponse>) {
        productDao.insertAllProduct(listData)
    }

    fun getAllProduct() = productDao.getAllProduct()

    fun search(listKeyWord: List<String>) = productDao.search(listKeyWord)

}