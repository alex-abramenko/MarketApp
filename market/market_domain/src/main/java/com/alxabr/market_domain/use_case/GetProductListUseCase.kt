package com.alxabr.market_domain.use_case

import com.alxabr.market_domain.model.Product
import com.alxabr.market_domain.repository.ProductRepository
import com.alxabr.market_domain.repository.ProductSortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke(
        sortType: ProductSortType = ProductSortType.BY_POPULAR,
        isOnlyFavorites: Boolean = false
    ): Flow<List<Product>> =
        productRepository.getProducts(
            sortType = sortType,
            isOnlyFavorites = isOnlyFavorites
        )
}