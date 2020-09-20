package com.teko.hoangviet.androidtest.di.builder

import com.teko.hoangviet.androidtest.ui.product.detail.DetailProductFragment
import com.teko.hoangviet.androidtest.ui.product.detail.pricecomparing.PriceComparingFragment
import com.teko.hoangviet.androidtest.ui.product.detail.productdescription.ProductDescriptionFragment
import com.teko.hoangviet.androidtest.ui.product.detail.technicalspec.TechnicalSpecFragment
import com.teko.hoangviet.androidtest.ui.product.list.ListProductFragment
import com.teko.hoangviet.androidtest.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector()
    abstract fun bindLoadMoreFragment(): ListProductFragment

    @ContributesAndroidInjector()
    abstract fun bindDetailProductFragment(): DetailProductFragment

    @ContributesAndroidInjector()
    abstract fun bindPriceComparingFragment(): PriceComparingFragment

    @ContributesAndroidInjector()
    abstract fun bindTechnicalSpecFragment(): TechnicalSpecFragment

    @ContributesAndroidInjector()
    abstract fun bindProductDescriptionFragment(): ProductDescriptionFragment

    @ContributesAndroidInjector()
    abstract fun bindSearchFragment(): SearchFragment
}