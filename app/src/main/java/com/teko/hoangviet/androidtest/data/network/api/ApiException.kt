package com.teko.hoangviet.androidtest.data.network.api

import com.teko.hoangviet.androidtest.data.network.ApiConstant
import java.lang.Exception

class ApiException : Exception {
    var statusCode: Int = ApiConstant.HttpStatusCode.UNKNOWN

    constructor() : super(ApiConstant.HttpMessage.ERROR_TRY_AGAIN)

    constructor(statusCode: Int) : super(ApiConstant.HttpMessage.ERROR_TRY_AGAIN) {
        this.statusCode = statusCode
    }

    constructor(msg: String) : super(msg)

    constructor(statusCode: Int, msg: String) : super(msg) {
        this.statusCode = statusCode
    }
}