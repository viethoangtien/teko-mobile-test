package com.teko.hoangviet.androidtest.di.module

import com.teko.hoangviet.androidtest.data.network.ApiConstant
import com.teko.hoangviet.androidtest.data.network.ApiService
import com.teko.hoangviet.androidtest.data.network.api.NetworkConnectionInterceptor
import com.teko.hoangviet.androidtest.BuildConfig
import com.teko.hoangviet.androidtest.application.BaseApplication
import com.teko.hoangviet.androidtest.data.network.api.LanguageInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        var logging = HttpLoggingInterceptor().setLevel(ApiConstant.LoggingLevel.BODY)
        var mOkHttpClientBuilder = OkHttpClient.Builder()
        mOkHttpClientBuilder.connectTimeout(ApiConstant.Timeout.CONNECT, TimeUnit.SECONDS)
        mOkHttpClientBuilder.readTimeout(ApiConstant.Timeout.READ, TimeUnit.SECONDS)
        mOkHttpClientBuilder.writeTimeout(ApiConstant.Timeout.WRITE, TimeUnit.SECONDS)
        mOkHttpClientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .method(original.method(), original.body())
                .build()
            chain.proceed(request)
        }
            .addInterceptor(NetworkConnectionInterceptor(BaseApplication.instance))
            .addInterceptor(LanguageInterceptor())
        if (BuildConfig.DEBUG) {
            mOkHttpClientBuilder?.let {
                if (!it.interceptors().contains(logging)) {
                    it.addInterceptor(logging)
                }
            }
        }
        return mOkHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}