package com.alxabr.market_data

import com.alxabr.market_data.repository.ProductRepositoryImpl
import com.alxabr.market_domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface MarketDataApi {

    @Binds
    @Singleton
    fun productRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository
}