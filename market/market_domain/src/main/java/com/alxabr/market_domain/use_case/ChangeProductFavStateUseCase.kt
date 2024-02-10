package com.alxabr.market_domain.use_case

import com.alxabr.market_domain.model.Product
import com.alxabr.market_domain.repository.ProductRepository
import javax.inject.Inject

class ChangeProductFavStateUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(product: Product) {
        productRepository.changeFavoriteState(product = product)
    }
}