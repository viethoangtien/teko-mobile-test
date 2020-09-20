package com.teko.hoangviet.androidtest.ui.product.list

import android.content.IntentFilter
import android.text.InputType
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.beetech.productmanagement.di.annotation.LayoutId
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.adapter.ListProductAdapter
import com.teko.hoangviet.androidtest.base.ui.BaseFragment
import com.teko.hoangviet.androidtest.broadcast.Broadcast
import com.teko.hoangviet.androidtest.data.local.model.LoadMoreData
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.databinding.FragmentListProductBinding
import com.teko.hoangviet.androidtest.extension.*
import com.teko.hoangviet.androidtest.rxbus.RxBus
import com.teko.hoangviet.androidtest.rxbus.RxEvent
import com.teko.hoangviet.androidtest.ui.main.MainViewModel
import com.teko.hoangviet.androidtest.ui.product.detail.DetailProductFragment
import com.teko.hoangviet.androidtest.ui.search.SearchFragment
import com.teko.hoangviet.androidtest.utils.Define
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_list_product.*
import kotlinx.android.synthetic.main.fragment_list_product.layout_search
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_search.view.*

@LayoutId(R.layout.fragment_list_product)
class ListProductFragment : BaseFragment<FragmentListProductBinding>() {
    private var broadcast: Broadcast? = null
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
        initBroadcast()
        layout_search.edt_search.gone()
        layout_search.tv_input.visible()
    }

    private fun initBroadcast() {
        broadcast = Broadcast()
        requireActivity().registerReceiver(
            broadcast,
            IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        )
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
        brv_product.setEnableRefresh(false)
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
        layout_search.imv_back.onAvoidDoubleClick {
            requireActivity().onBackPressed()
        }
        compositeDisposable.add(
            RxBus.listen(RxEvent.NetworkConnectedEvent::class.java)
                .subscribe {
                    if (layout_empty.isShown) {
                        mainViewModel.getListProduct()
                    }
                }
        )

    }

    override fun getError(error: String?, code: Int) {
        layout_empty.visible()
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
        if (data?.isEmpty() == true) {
            layout_empty.visible()
        } else {
            layout_empty.gone()
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

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(broadcast)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}