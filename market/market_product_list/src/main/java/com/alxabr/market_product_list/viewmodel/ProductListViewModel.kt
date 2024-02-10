package com.alxabr.market_product_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxabr.market_domain.model.Product
import com.alxabr.market_domain.use_case.ChangeProductFavStateUseCase
import com.alxabr.market_domain.use_case.GetProductListUseCase
import com.alxabr.market_product_list.ProductListController
import com.alxabr.market_product_list.adapter.ProductItemListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val changeProductFavStateUseCase: ChangeProductFavStateUseCase
) : ViewModel(), ProductListController, ProductItemListener {

    private var loadProductsJob: Job? = null

    private val _products: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val products: StateFlow<List<Product>> by ::_products

    init {
        refreshProducts()
    }

    private fun refreshProducts() {
        loadProductsJob = getProductListUseCase()
            .onEach { _products.value = it }
            .launchIn(viewModelScope)
    }

    override fun onClick(product: Product) {

    }

    override fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            changeProductFavStateUseCase(product)
        }
    }

    override fun onAddToBasketClick(product: Product) {

    }
}