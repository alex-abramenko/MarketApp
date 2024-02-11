package com.alxabr.market_data.repository

import com.alxabr.market_data.data_source.database.ProductDao
import com.alxabr.market_data.data_source.remote.RemoteDataSource
import com.alxabr.market_domain.model.Product
import com.alxabr.market_domain.repository.ProductRepository
import com.alxabr.market_domain.repository.ProductSortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val productMapper: ProductMapper,
    private val remoteDataSource: RemoteDataSource
) : ProductRepository {

    override fun getProducts(sortType: ProductSortType, isOnlyFavorites: Boolean): Flow<List<Product>> =
        callbackFlow {
            val localData = productDao.getProducts()
            val remoteData = remoteDataSource.getProducts()
            send(
                productMapper(localData.ifEmpty { remoteData }).applySort(sortType)
            )
            productDao.insertProducts(remoteData)
            var isFirst = true
            val job = launch {
                productDao.getProductsFlowable().collect {
                    if (!isFirst) {
                        send(
                            productMapper(it).applySort(sortType)
                        )
                    }
                    isFirst = false
                }
            }
            awaitClose {
                job.cancel()
                cancel()
            }
        }

    override fun getProduct(productId: String): Flow<Product?> =
        productDao.getProductFlowable(productId = productId).map {
            it?.let { item -> productMapper(item) }
        }

    override suspend fun changeFavoriteState(product: Product) {
        withContext(Dispatchers.IO) {
            productDao.insertProduct(
                product = productMapper(product).copy(isFavorite = !product.isFavorite)
            )
        }
    }

    private fun List<Product>.applySort(sortType: ProductSortType): List<Product> =
        when (sortType) {
            ProductSortType.BY_POPULAR -> sortedBy { it.feedback.rating }
            ProductSortType.BY_PRICE_DOWN -> sortedByDescending { it.price.priceWithDiscount }
            ProductSortType.BY_PRICE_UP -> sortedBy { it.price.priceWithDiscount }
        }
}