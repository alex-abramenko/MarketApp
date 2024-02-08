package com.alxabr.market_data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alxabr.market_data.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProductDao {

    @Query("SELECT * FROM ProductEntity")
    fun getProductsFlowable(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE id=:productId")
    fun getProductFlowable(productId: String): Flow<ProductEntity?>

    @Query("SELECT * FROM ProductEntity WHERE id=:productId")
    suspend fun getProduct(productId: String): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)
}