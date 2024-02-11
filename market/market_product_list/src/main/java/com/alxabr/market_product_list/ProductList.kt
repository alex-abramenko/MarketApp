package com.alxabr.market_product_list

import com.alxabr.market_domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductList {

    sealed interface Event {
        data class OnOpenProduct(val product: Product) : Event
    }

    val events: Flow<Event>
}