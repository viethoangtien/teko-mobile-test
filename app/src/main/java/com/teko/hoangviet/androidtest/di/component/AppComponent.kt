package com.teko.hoangviet.androidtest.di.component

import android.app.Application
import android.content.Context
import com.teko.hoangviet.androidtest.application.BaseApplication
import com.teko.hoangviet.androidtest.di.builder.ActivityBuilder
import com.teko.hoangviet.androidtest.di.builder.FragmentBuilder
import com.teko.hoangviet.androidtest.di.module.AppModule
import com.teko.hoangviet.androidtest.di.module.LocalModule
import com.teko.hoangviet.androidtest.di.module.NetworkModule
import com.teko.hoangviet.androidtest.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        (AndroidInjectionModule::class),
        (AndroidSupportInjectionModule::class),
        (AppModule::class),
        (NetworkModule::class),
        (LocalModule::class),
        (ActivityBuilder::class),
        (FragmentBuilder::class),
        (ViewModelModule::class)]
)
@Singleton
interface AppComponent {
    fun getContext(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(baseApplication: BaseApplication)
}