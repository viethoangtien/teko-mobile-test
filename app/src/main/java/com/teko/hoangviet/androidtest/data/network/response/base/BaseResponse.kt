package com.teko.hoangviet.androidtest.data.network.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("status")
    var status: Int = 0
    @SerializedName("msg")
    var msg: String = ""
    @SerializedName("total_page")
    var totalPage: Int = 0
    @SerializedName("total_items")
    var totalItems: Int = 0
    @SerializedName("current_page")
    var currentPage: Int = 0

}