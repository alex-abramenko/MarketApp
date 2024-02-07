package com.alxabr.market_domain.use_case

import com.alxabr.market_domain.model.Product
import com.alxabr.market_domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke(productId: String): Flow<Product?> =
        productRepository.getProduct(productId = productId)
}