package com.teko.hoangviet.androidtest.data.network

import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.utils.Define
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @GET(Define.ApiService.RelativeUrl.LIST_PRODUCT)
    fun listProduct(): Single<ArrayList<ProductResponse>>
}