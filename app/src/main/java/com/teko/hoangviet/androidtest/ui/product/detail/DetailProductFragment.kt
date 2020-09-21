package com.teko.hoangviet.androidtest.ui.product.detail

import android.graphics.Typeface
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.beetech.productmanagement.di.annotation.LayoutId
import com.google.android.material.tabs.TabLayout
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.adapter.DetailProductAdapter
import com.teko.hoangviet.androidtest.adapter.ImageDetailProductAdapter
import com.teko.hoangviet.androidtest.adapter.SameTypeAdapter
import com.teko.hoangviet.androidtest.adapter.TAB_TECHNICAL
import com.teko.hoangviet.androidtest.base.ui.BaseFragment
import com.teko.hoangviet.androidtest.data.local.model.Cart
import com.teko.hoangviet.androidtest.data.local.model.ProductResponse
import com.teko.hoangviet.androidtest.databinding.FragmentDetailProductBinding
import com.teko.hoangviet.androidtest.extension.*
import com.teko.hoangviet.androidtest.ui.main.MainViewModel
import com.teko.hoangviet.androidtest.utils.NumberUtil
import kotlinx.android.synthetic.main.fragment_detail_product.*
import kotlinx.android.synthetic.main.layout_cart.*
import kotlinx.android.synthetic.main.layout_cart.view.*
import kotlinx.android.synthetic.main.layout_quantity.view.*
import kotlinx.android.synthetic.main.layout_toolbar_product.view.*
import kotlinx.android.synthetic.main.layout_toolbar_product.view.tv_price

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
        showLoading()
        compositeDisposable.add(
            completableTimer({
                detailProductViewModel.setDetailProduct(detailProduct)
                initDetailViewPager()
            }, 310)
        )
    }

    override fun initListener() {
        detailProductViewModel.detailProductLiveData.observe(this, Observer {
            it?.let {
                initImageAdapter(it.imageUrl)
                toolbar_product.setData(it)
                binding.productResponse = it
            }
        })
        detailProductViewModel.getCart(detailProduct.id!!).observe(this, Observer {
            it?.let {
                setQuantity(it.quantity, isShowBadge = true)

            } ?: kotlin.run {
                setQuantity(1, isShowBadge = false)
            }
        })
        detailProductViewModel.quantityBadge.observe(this, Observer {
            toolbar_product.tv_badge.setNumber(it)
        })
        mainViewModel.listProductLiveData.observe(this, Observer {
            it?.let {
                compositeDisposable.add(
                    completableTimer({
                        handleListResponse(it)
                    }, 310)
                )
            }
        })
        quantity_view.setOnQuantityListener {
            layout_cart.tv_price.text = NumberUtil.formatValueVnd(detailProduct.price!! * it)
        }
        layout_cart.rl_price.onAvoidDoubleClick {
            detailProductViewModel.saveCart(
                Cart(
                    productId = detailProduct.id!!,
                    quantity = quantity_view.tv_selected.text.toString().toInt()
                )
            )
        }
        toolbar_product.imv_back.onAvoidDoubleClick {
            viewController.backFromAddFragment(null)
        }
    }

    private fun setQuantity(quantity: Int, isShowBadge: Boolean = false) {
        if (isShowBadge) toolbar_product.tv_badge.setNumber(number = quantity)
        layout_cart.tv_price.text = NumberUtil.formatValueVnd(detailProduct.price!! * quantity)
        layout_cart.quantity_view.setQuantity(quantity)
    }

    private fun initDetailViewPager() {
        detailProductAdapter = DetailProductAdapter(requireContext(), childFragmentManager)
        vp_detail_product.apply {
            adapter = detailProductAdapter
            offscreenPageLimit = 2
        }
        tab_detail.setupWithViewPager(vp_detail_product)
        vp_detail_product.currentItem = TAB_TECHNICAL
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