package com.teko.hoangviet.androidtest.data.network.api

import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("lang", "vi")
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)
    }
}