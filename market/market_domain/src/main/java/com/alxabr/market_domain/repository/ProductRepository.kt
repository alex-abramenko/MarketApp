package com.alxabr.market_domain.repository

import com.alxabr.market_domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(
        sortType: ProductSortType,
        isOnlyFavorites: Boolean
    ): Flow<List<Product>>

    fun getProduct(productId: String): Flow<Product?>

    suspend fun changeFavoriteState(product: Product)
}