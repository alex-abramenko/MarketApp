package com.alxabr.market_data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alxabr.market_data.model.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1
)
@TypeConverters(ProductEntity.Converters::class)
internal abstract class MarketDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}