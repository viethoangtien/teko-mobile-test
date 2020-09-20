package com.teko.hoangviet.androidtest.data.network.api

import com.teko.hoangviet.androidtest.data.network.ApiConstant
import com.teko.hoangviet.androidtest.data.network.ApiService
import com.teko.hoangviet.androidtest.data.network.response.ListResponse
import com.teko.hoangviet.androidtest.extension.backgroundThreadProcess
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(val apiService: ApiService) {
    fun listProduct() = apiService.listProduct()
}