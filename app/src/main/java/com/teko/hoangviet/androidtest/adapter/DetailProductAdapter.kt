package com.teko.hoangviet.androidtest.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.teko.hoangviet.androidtest.R
import com.teko.hoangviet.androidtest.ui.product.detail.pricecomparing.PriceComparingFragment
import com.teko.hoangviet.androidtest.ui.product.detail.productdescription.ProductDescriptionFragment
import com.teko.hoangviet.androidtest.ui.product.detail.technicalspec.TechnicalSpecFragment

const val TAB_DESCRIPTION = 0
const val TAB_TECHNICAL = 1
const val TAB_COMPARING = 2

class DetailProductAdapter(val context: Context, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentCreator: Map<Int, () -> Fragment> = mapOf(
        TAB_DESCRIPTION to { ProductDescriptionFragment() },
        TAB_TECHNICAL to { TechnicalSpecFragment() },
        TAB_COMPARING to { PriceComparingFragment() }
    )

    override fun getItem(position: Int): Fragment {
        return fragmentCreator[position]?.invoke()!!
    }

    override fun getCount(): Int {
        return fragmentCreator.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            TAB_DESCRIPTION -> context.getString(R.string.tab_description)
            TAB_TECHNICAL -> context.getString(R.string.tab_technical)
            TAB_COMPARING -> context.getString(R.string.tab_comparing)
            else -> ""
        }
    }
}