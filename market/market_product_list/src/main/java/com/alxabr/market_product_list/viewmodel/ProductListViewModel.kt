package com.alxabr.market_product_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxabr.market_domain.model.Product
import com.alxabr.market_domain.repository.ProductSortType
import com.alxabr.market_domain.use_case.ChangeProductFavStateUseCase
import com.alxabr.market_domain.use_case.GetProductListUseCase
import com.alxabr.market_product_list.ProductList
import com.alxabr.market_product_list.ProductListView
import com.alxabr.market_product_list.adapter.ProductItemListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
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
) : ViewModel(), ProductList, ProductItemListener {

    private var loadProductsJob: Job? = null

    private val _products: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val products: StateFlow<List<Product>> by ::_products

    private val _events: MutableSharedFlow<ProductList.Event> = MutableSharedFlow()
    override val events: Flow<ProductList.Event> by ::_events

    fun onConfigure(config: ProductListView.Config) {
        config
            .sortType
            .onEach {
                refreshProducts(
                    sortType = it,
                    isOnlyFavorites = config.isOnlyFavorites
                )
            }
            .launchIn(viewModelScope)
    }

    private fun refreshProducts(
        sortType: ProductSortType,
        isOnlyFavorites: Boolean
    ) {
        loadProductsJob = getProductListUseCase(sortType = sortType, isOnlyFavorites = isOnlyFavorites)
            .onEach { _products.value = it }
            .launchIn(viewModelScope)
    }

    override fun onClick(product: Product) {
        viewModelScope.launch {
            _events.emit(ProductList.Event.OnOpenProduct(product = product))
        }
    }

    override fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            changeProductFavStateUseCase(product)
        }
    }

    override fun onAddToBasketClick(product: Product) {
        // TODO Обращаемся к UseCase для добавления в корзину
    }
}