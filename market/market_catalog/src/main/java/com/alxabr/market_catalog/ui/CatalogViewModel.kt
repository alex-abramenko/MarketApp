package com.alxabr.market_catalog.ui

import androidx.lifecycle.ViewModel
import com.alxabr.market_domain.repository.ProductSortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class CatalogViewModel @Inject constructor() : ViewModel() {

    private val _sortType: MutableStateFlow<ProductSortType> = MutableStateFlow(ProductSortType.BY_POPULAR)
    val sortType: StateFlow<ProductSortType> by ::_sortType

    fun onUiEvent(event: CatalogUiEvent) {
        when (event) {
            is CatalogUiEvent.OnSortChanged -> onSortTypeChanged(sortType = event.sortType)
        }
    }

    private fun onSortTypeChanged(sortType: ProductSortType) {
        _sortType.value = sortType
    }
}