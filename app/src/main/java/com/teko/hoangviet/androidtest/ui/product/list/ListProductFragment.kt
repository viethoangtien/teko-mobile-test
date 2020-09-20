package com.teko.hoangviet.androidtest.ui.product.list

import android.text.InputType
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.beetech.productmanagement.di.annotation.LayoutId
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.adapter.ListProductAdapter
import com.teko.hoangviet.androidtest.base.ui.BaseFragment
import com.teko.hoangviet.androidtest.data.local.model.LoadMoreData
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.databinding.FragmentListProductBinding
import com.teko.hoangviet.androidtest.extension.injectActivityViewModel
import com.teko.hoangviet.androidtest.extension.injectViewModel
import com.teko.hoangviet.androidtest.extension.onAvoidDoubleClick
import com.teko.hoangviet.androidtest.ui.main.MainViewModel
import com.teko.hoangviet.androidtest.ui.product.detail.DetailProductFragment
import com.teko.hoangviet.androidtest.ui.search.SearchFragment
import com.teko.hoangviet.androidtest.utils.Define
import kotlinx.android.synthetic.main.fragment_list_product.*
import kotlinx.android.synthetic.main.fragment_list_product.layout_search
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_search.view.*

@LayoutId(R.layout.fragment_list_product)
class ListProductFragment : BaseFragment<FragmentListProductBinding>() {
    private lateinit var listProductAdapter: ListProductAdapter
    private lateinit var listProductViewModel: ListProductViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun initViewModel() {
        mainViewModel = injectActivityViewModel(viewModelFactory)
        listProductViewModel = injectViewModel(viewModelFactory)
    }

    override fun backFromAddFragment() {

    }

    override fun backPressed(): Boolean {
        return true
    }

    override fun initView() {
        initAdapter()
        layout_search.edt_search.apply {
            inputType = InputType.TYPE_NULL
            isEnabled = false
        }
    }

    private fun initAdapter() {
        listProductAdapter = ListProductAdapter(requireContext(), false)
        brv_product.setAdapter(listProductAdapter)
        brv_product.setListLayoutManager(RecyclerView.VERTICAL)
        brv_product.setOnRefreshListener {
            mainViewModel.getListProduct()
        }
        brv_product.setOnItemClickListener { recyclerViewAdapter, viewHolder, _, position ->
            viewController.addFragment(
                DetailProductFragment::class.java, hashMapOf(
                    Define.Fragment.Argument.DETAIL_PRODUCT to recyclerViewAdapter.getItem(
                        position,
                        ProductResponse::class.java
                    )
                )
            )
        }
        brv_product.setOnLoadingMoreListener {
//            brv_product.showLoadingItem()
//            listProductViewModel.getListProduct()
        }
        brv_product.setOnRetryLoadingMoreListener {
//            listProductViewModel.getListProduct()
        }
    }

    override fun initData() {
        mainViewModel.getListProduct()
    }

    override fun initListener() {
        mainViewModel.listProductLiveData.observe(this, Observer {
            it?.let {
                handleListResponse(it)
            }
        })
        layout_search.rl_search.onAvoidDoubleClick {
            viewController.addFragment(SearchFragment::class.java, null)
        }
        layout_search.edt_search.onAvoidDoubleClick {
            viewController.addFragment(SearchFragment::class.java, null)
        }
        layout_search.imv_back.onAvoidDoubleClick {
            requireActivity().onBackPressed()
        }
    }

    override fun getError(error: String?, code: Int) {
        if (listProductAdapter.getIsLoading()) {
            listProductAdapter.getItem(
                listProductAdapter.itemCount - 1,
                LoadMoreData::class.java
            )?.isRetry =
                true
            listProductAdapter.notifyItemChanged(listProductAdapter.itemCount - 1)
        }
    }

    override fun getListResponse(data: List<*>?, isRefresh: Boolean, isLoadingMore: Boolean) {
        brv_product.enableLoadMore(isLoadingMore)
        when {
            isRefresh -> {
                brv_product.refresh(data as List<Any>?)
            }
            else -> {
                brv_product.addItem(data as ArrayList<Any>)
            }
        }
    }
}