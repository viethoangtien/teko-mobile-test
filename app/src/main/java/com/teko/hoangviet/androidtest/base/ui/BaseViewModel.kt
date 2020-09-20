package com.teko.hoangviet.androidtest.base.ui

import androidx.lifecycle.ViewModel
import com.teko.hoangviet.androidtest.data.network.api.Repository
import com.teko.hoangviet.androidtest.data.preference.SharePreference
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    @Inject
    protected lateinit var sharePreference: SharePreference
    @Inject
    protected lateinit var repository: Repository
    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.let {
            it.clear()
        }
    }
}