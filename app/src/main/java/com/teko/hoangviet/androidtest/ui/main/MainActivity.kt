package com.teko.hoangviet.androidtest.ui.main

import android.annotation.TargetApi
import android.os.Build
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.beetech.productmanagement.di.annotation.LayoutId
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.ui.BaseActivity
import com.teko.hoangviet.androidtest.databinding.ActivityMainBinding
import com.teko.hoangviet.androidtest.extension.injectViewModel
import com.teko.hoangviet.androidtest.ui.product.list.ListProductFragment

@LayoutId(R.layout.activity_main)
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var mMainViewModel: MainViewModel

    override fun getFragmentContainerId(): Int {
        return R.id.fl_container
    }

    override fun initViewModel() {
        mMainViewModel = injectViewModel(viewModelFactory)
    }

    override fun initView() {
        initStatusBar()
        viewController.addFragment(ListProductFragment::class.java, null)
    }

    private fun initStatusBar() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            window.setBackgroundDrawableResource(R.drawable.bg_main_color)
        }
    }

    override fun initData() {

    }

    override fun initListener() {

    }
}
