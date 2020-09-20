package com.teko.hoangviet.androidtest.data.network

import com.teko.hoangviet.androidtest.data.network.response.ObjectResponse
import com.teko.hoangviet.androidtest.data.network.response.ListResponse
import com.teko.hoangviet.androidtest.utils.Define
import com.teko.hoangviet.androidtest.data.network.response.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET(Define.ApiService.RelativeUrl.LIST_PRODUCT)
    fun listProduct(): Single<ArrayList<ProductResponse>>
}