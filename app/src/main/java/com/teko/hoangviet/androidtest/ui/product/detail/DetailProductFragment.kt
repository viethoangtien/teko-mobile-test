package com.teko.hoangviet.androidtest.ui.product.detail

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.beetech.productmanagement.di.annotation.LayoutId
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.adapter.DetailProductAdapter
import com.teko.hoangviet.androidtest.adapter.ImageDetailProductAdapter
import com.teko.hoangviet.androidtest.adapter.SameTypeAdapter
import com.teko.hoangviet.androidtest.base.ui.BaseFragment
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.databinding.FragmentDetailProductBinding
import com.teko.hoangviet.androidtest.extension.argument
import com.teko.hoangviet.androidtest.extension.injectActivityViewModel
import com.teko.hoangviet.androidtest.extension.injectViewModel
import com.teko.hoangviet.androidtest.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_detail_product.*

@LayoutId(R.layout.fragment_detail_product)
class DetailProductFragment : BaseFragment<FragmentDetailProductBinding>() {
    private lateinit var detailProductAdapter: DetailProductAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var imageDetailProductAdapter: ImageDetailProductAdapter
    private lateinit var sameTypeAdapter: SameTypeAdapter
    private lateinit var detailProductViewModel: DetailProductViewModel
    private val detailProduct: ProductResponse by argument()

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        viewController.backFromAddFragment(null)
        return false
    }

    override fun initView() {

    }

    override fun initViewModel() {
        mainViewModel = injectActivityViewModel(viewModelFactory)
        detailProductViewModel = injectViewModel(viewModelFactory)
    }

    override fun initData() {
        detailProductViewModel.setDetailProduct(detailProduct)
        initDetailViewPager()
    }

    override fun initListener() {
        detailProductViewModel.detailProductLiveData.observe(this, Observer {
            it?.let {
                initImageAdapter(it.imageUrl)
                toolbar_product.setData(it)
                binding.productResponse = it
            }
        })
        mainViewModel.listProductLiveData.observe(this, Observer {
            it?.let {
                handleListResponse(it)
            }
        })
    }

    private fun initDetailViewPager() {
        detailProductAdapter = DetailProductAdapter(requireContext(), childFragmentManager)
        vp_detail_product.apply {
            adapter = detailProductAdapter
            offscreenPageLimit = 2
        }
        tab_detail.setupWithViewPager(vp_detail_product)
    }

    private fun initImageAdapter(imageUrl: String?) {
        imageDetailProductAdapter = ImageDetailProductAdapter(requireContext(), false)
        imageDetailProductAdapter.addModels(listOf(imageUrl!!, imageUrl, imageUrl, imageUrl), false)
        vp_product.apply {
            adapter = imageDetailProductAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        ci_product.setViewPager(vp_product)
    }

    override fun getListResponse(data: List<*>?, isRefresh: Boolean, isLoadingMore: Boolean) {
        sameTypeAdapter = SameTypeAdapter(requireContext(), false)
        sameTypeAdapter.addModels(data as List<Any>, false)
        rcv_same_type.apply {
            adapter = sameTypeAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }
}