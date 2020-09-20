package com.teko.hoangviet.androidtest.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import com.beetech.productmanagement.di.annotation.LayoutId
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.base.ui.BaseFragment
import com.teko.hoangviet.androidtest.databinding.FragmentSearchBinding
import com.teko.hoangviet.androidtest.extension.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_search.view.*
import java.util.concurrent.TimeUnit


@LayoutId(R.layout.fragment_search)
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private lateinit var searchViewModel: SearchViewModel
    override fun backFromAddFragment() {

    }

    override fun backPressed(): Boolean {
        viewController.backFromAddFragment(null)
        return false
    }

    override fun initView() {
        layout_search.edt_search.visible()
        layout_search.edt_search.requestFocus()
        layout_search.tv_input.gone()
        compositeDisposable.add(completableTimer({
            requireActivity().showSoftKeyboard()
        },310))
    }

    override fun initViewModel() {
        searchViewModel = injectViewModel(viewModelFactory)
    }

    override fun initData() {

    }

    @SuppressLint("CheckResult")
    override fun initListener() {
        searchViewModel.listProduct.observe(this, Observer {
            Log.d("myLog", it.toString())
        })
        layout_search.imv_back.onAvoidDoubleClick {
            requireActivity().hideSoftKeyboard()
            viewController.backFromAddFragment(null)
        }
        layout_search.edt_search.observableFromView()
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter {
                return@filter it.isNotEmpty()
            }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it?.let {
                    searchViewModel.search(it).observe(this, Observer { dataFilter ->
                        dataFilter?.let {
                            Log.d("myLog", it.toString())
                        }
                    })
                }
            }
    }
}