package com.teko.hoangviet.androidtest.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment

inline fun <reified T : ViewModel> DaggerAppCompatActivity.injectViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
}

inline fun <reified T : ViewModel> AppCompatActivity.injectViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
}

inline fun <reified T : ViewModel> DaggerFragment.injectViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.injectViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.injectParentViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(requireParentFragment(), viewModelFactory).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.injectActivityViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(requireActivity(), viewModelFactory).get(T::class.java)
}
