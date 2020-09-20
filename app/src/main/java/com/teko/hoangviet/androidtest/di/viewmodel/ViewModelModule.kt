package com.teko.hoangviet.androidtest.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teko.hoangviet.androidtest.ui.product.list.ListProductViewModel
import com.teko.hoangviet.androidtest.ui.main.MainViewModel
import com.teko.hoangviet.androidtest.ui.product.detail.DetailProductViewModel
import com.teko.hoangviet.androidtest.ui.product.detail.pricecomparing.PriceComparingFragment
import com.teko.hoangviet.androidtest.ui.product.detail.pricecomparing.PriceComparingViewModel
import com.teko.hoangviet.androidtest.ui.product.detail.productdescription.ProductDescriptionViewModel
import com.teko.hoangviet.androidtest.ui.product.detail.technicalspec.TechnicalSpecViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListProductViewModel::class)
    internal abstract fun bindLoadMoreViewModel(viewModel: ListProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailProductViewModel::class)
    internal abstract fun bindDetailProductViewModel(viewModel: DetailProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PriceComparingViewModel::class)
    internal abstract fun bindPriceComparingViewModel(viewModel: PriceComparingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDescriptionViewModel::class)
    internal abstract fun bindProductDescriptionViewModel(viewModel: ProductDescriptionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TechnicalSpecViewModel::class)
    internal abstract fun bindTechnicalSpecViewModel(viewModel: TechnicalSpecViewModel): ViewModel
}