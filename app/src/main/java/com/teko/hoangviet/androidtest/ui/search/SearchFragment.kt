package com.teko.hoangviet.androidtest.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.beetech.productmanagement.di.annotation.LayoutId
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.adapter.ListProductAdapter
import com.teko.hoangviet.androidtest.base.ui.BaseFragment
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.databinding.FragmentSearchBinding
import com.teko.hoangviet.androidtest.extension.*
import com.teko.hoangviet.androidtest.ui.product.detail.DetailProductFragment
import com.teko.hoangviet.androidtest.utils.Define
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list_product.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.layout_search
import kotlinx.android.synthetic.main.layout_search.view.*
import java.util.concurrent.TimeUnit


@LayoutId(R.layout.fragment_search)
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private lateinit var listProductAdapter: ListProductAdapter
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
            initAdapter()
        }, 310))
    }

    private fun initAdapter() {
        listProductAdapter = ListProductAdapter(requireContext(), false)
        brv_search.setAdapter(listProductAdapter)
        brv_search.setEnableRefresh(false)
        brv_search.setListLayoutManager(RecyclerView.VERTICAL)
        brv_search.setOnItemClickListener { recyclerViewAdapter, viewHolder, _, position ->
            requireActivity().hideSoftKeyboard()
            viewController.addFragment(
                DetailProductFragment::class.java, hashMapOf(
                    Define.Fragment.Argument.DETAIL_PRODUCT to recyclerViewAdapter.getItem(
                        position,
                        ProductResponse::class.java
                    )
                )
            )
        }
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
                it?.let { keySearch ->
                    searchViewModel.search(keySearch).observe(this, Observer { dataFilter ->
                        dataFilter?.let {
                            listProductAdapter.highlightTextSearch(keySearch.split(' '))
                            brv_search.refresh(it)
                        }
                    })
                }
            }
    }
}