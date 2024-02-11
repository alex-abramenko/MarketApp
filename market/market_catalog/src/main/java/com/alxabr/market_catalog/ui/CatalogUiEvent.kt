package com.alxabr.market_catalog.ui

import com.alxabr.market_domain.repository.ProductSortType

sealed interface CatalogUiEvent {

    data class OnSortChanged(val sortType: ProductSortType) : CatalogUiEvent
}