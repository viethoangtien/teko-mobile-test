package com.teko.hoangviet.androidtest.application

import android.app.Activity
import android.app.Application
import androidx.databinding.library.BuildConfig
import androidx.fragment.app.Fragment
import com.teko.hoangviet.androidtest.di.component.DaggerAppComponent
import com.teko.hoangviet.androidtest.utils.LogUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {
    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    @Inject
    internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LogUtil.init(BuildConfig.DEBUG)
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}