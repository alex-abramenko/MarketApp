package com.alxabr.market_data.di

import android.app.Application
import androidx.room.Room
import com.alxabr.market_data.data_source.database.MarketDatabase
import com.alxabr.market_data.data_source.database.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MarketDataDIModule {

    @Provides
    @Singleton
    fun marketDatabase(application: Application): MarketDatabase =
        Room
            .databaseBuilder(
                context = application,
                klass = MarketDatabase::class.java,
                name = MarketDatabase::class.java.name
            )
            .build()

    @Provides
    @Singleton
    fun productDao(marketDatabase: MarketDatabase): ProductDao =
        marketDatabase.productDao
}