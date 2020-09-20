package com.teko.hoangviet.androidtest.ui.product.detail.technicalspec

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beetech.productmanagement.di.annotation.LayoutId
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.adapter.ProductTechnicalAdapter
import com.teko.hoangviet.androidtest.base.ui.BaseFragment
import com.teko.hoangviet.androidtest.data.network.response.ProductDescriptionResponse
import com.teko.hoangviet.androidtest.databinding.FragmentTechnicalSpecBinding
import kotlinx.android.synthetic.main.fragment_technical_spec.*

@LayoutId(R.layout.fragment_technical_spec)
class TechnicalSpecFragment : BaseFragment<FragmentTechnicalSpecBinding>() {
    private lateinit var productTechnicalAdapter: ProductTechnicalAdapter
    override fun backFromAddFragment() {

    }

    override fun backPressed(): Boolean {
        return false
    }

    override fun initView() {
        productTechnicalAdapter = ProductTechnicalAdapter(requireContext(), false)
        productTechnicalAdapter.addModels(
            listOf(
                ProductDescriptionResponse(key = "Thương hiệu", value = "Cooler Master"),
                ProductDescriptionResponse(key = "Bảo hành", value = "36 tháng"),
                ProductDescriptionResponse(key = "Công suất", value = "140W"),
                ProductDescriptionResponse(key = "Bộ nhớ đệm", value = "8.25MB")
            ),
            false
        )
        rcv_technical.apply {
            adapter = productTechnicalAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    override fun initViewModel() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }
}