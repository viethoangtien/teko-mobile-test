package com.teko.hoangviet.androidtest.di.module

import android.content.Context
import androidx.room.Room
import com.teko.hoangviet.androidtest.data.local.db.AppDatabase
import com.teko.hoangviet.androidtest.utils.Define
import dagger.Module
import dagger.Provides

@Module
class LocalModule {
    @Provides
    internal fun provideAppDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, Define.Database.NAME).build()

    @Provides
    internal fun provideProductDao(appDatabase: AppDatabase) = appDatabase.productDao()

    @Provides
    internal fun provideCartDao(appDatabase: AppDatabase) = appDatabase.cartDao()
}