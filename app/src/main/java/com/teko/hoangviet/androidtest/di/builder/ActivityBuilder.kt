package com.teko.hoangviet.androidtest.di.builder

import com.teko.hoangviet.androidtest.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity
}