package com.alxabr.market_domain.repository

import com.alxabr.market_domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(
        sortType: ProductSortType,
        isOnlyFavorites: Boolean
    ): Flow<Product>

    fun getProduct(productId: String): Flow<Product?>

    suspend fun changeFavoriteState(product: Product)
}