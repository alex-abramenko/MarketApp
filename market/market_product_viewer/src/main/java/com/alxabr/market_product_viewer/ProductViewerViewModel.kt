package com.alxabr.market_product_viewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxabr.auth_domain.utils.MarketLogger
import com.alxabr.market_domain.model.Product
import com.alxabr.market_domain.use_case.ChangeProductFavStateUseCase
import com.alxabr.market_domain.use_case.GetProductUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class ProductViewerViewModel @AssistedInject constructor(
    getProductUseCase: GetProductUseCase,
    private val changeProductFavStateUseCase: ChangeProductFavStateUseCase,
    @Assisted private val config: ProductViewerConfig
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(config: ProductViewerConfig) : ProductViewerViewModel
    }

    private val _product: MutableStateFlow<Product?> = MutableStateFlow(null)
    val product: StateFlow<Product?> by ::_product

    private val _descHiderState: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val descHiderState: StateFlow<Boolean> by ::_descHiderState
    val descBlockVisibility: StateFlow<Boolean> by ::_descHiderState

    init {
        getProductUseCase(productId = config.productId)
            .onEach { _product.value = it }
            .launchIn(viewModelScope)
    }

    fun onUiEvent(event: ProductViewerUiEvent) {
        when (event) {
            ProductViewerUiEvent.OnFavoriteChanged -> onFavoriteChanged()
            ProductViewerUiEvent.OnToBasketClick -> onToBasketClick()
            ProductViewerUiEvent.OnDescHiderClick -> onDescHiderClick()
        }
    }

    private fun onFavoriteChanged() {
        _product
            .value
            ?.let {
                viewModelScope.launch { changeProductFavStateUseCase(product = it) }
            }
            ?: MarketLogger.error("Product is null.")
    }

    private fun onToBasketClick() {
        // TODO Добавлем в корзину через UseCase
    }

    private fun onDescHiderClick() {
        _descHiderState.value = !_descHiderState.value
    }
}