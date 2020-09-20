package com.teko.hoangviet.androidtest.data.network.response

import com.google.gson.annotations.SerializedName

data class BaseListResponse<T>(
    @SerializedName("last_page")
    var totalPage: Int = 0,

    @SerializedName("total")
    var totalItems: Int = 0,

    @SerializedName("current_page")
    var currentPage: Int = 0,

    @SerializedName("data")
    var data: ArrayList<T>? = null
)