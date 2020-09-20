package com.teko.hoangviet.androidtest.data.network.response

import com.teko.hoangviet.androidtest.utils.Define

open class ListResponse<T>(
    val type: Int,
    val data: ArrayList<T>?,
    val error: Throwable?,
    var isRefresh: Boolean = false,
    var isLoadingMore: Boolean = false
) : BaseResponse() {
    companion object {
        fun <T> loading(): ListResponse<T> =
            ListResponse(Define.ResponseStatus.LOADING, null, null)

        fun <T> success(data: ArrayList<T>): ListResponse<T> =
            ListResponse(Define.ResponseStatus.SUCCESS, data, null)

        fun <T> error(throwable: Throwable): ListResponse<T> =
            ListResponse(Define.ResponseStatus.ERROR, null, throwable)
    }
}

fun <T> ListResponse<T>.setLoadingMore(
    isRefresh: Boolean = false,
    isLoadingMore: Boolean = false
): ListResponse<T> {
    this.isRefresh = isRefresh
    this.isLoadingMore = isLoadingMore
    return this
}