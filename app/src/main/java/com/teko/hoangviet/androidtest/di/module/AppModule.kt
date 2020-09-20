package com.teko.hoangviet.androidtest.di.module

import android.app.Application
import android.content.Context
import com.teko.hoangviet.androidtest.data.local.preference.AppSharePreference
import com.teko.hoangviet.androidtest.data.local.preference.SharePreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun provideSharePreferences(context: Context): SharePreference =
        AppSharePreference(
            context
        )

}