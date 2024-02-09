package com.alxabr.market_data.data_source.remote

import android.app.Application
import com.alxabr.market_data.data_source.database.ProductDao
import com.alxabr.market_data.model.ProductEntity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject

internal class RemoteDataSource @Inject constructor(
    private val application: Application,
    private val productDao: ProductDao
) {

    private val gson = Gson()

    suspend fun getProducts(): List<ProductEntity> =
        withContext(Dispatchers.IO) {
            class Result(val items: Array<ProductEntity>)
            gson
                .fromJson(
                    BufferedReader(
                        InputStreamReader(
                            application.assets.open("mock.json"),
                            StandardCharsets.UTF_8
                        )
                    ),
                    Result::class.java
                )
                .items
                .toList()
                .validateFavorite()
        }

    private suspend fun List<ProductEntity>.validateFavorite(): List<ProductEntity> =
        mutableListOf<ProductEntity>().also { newList ->
            forEach {
                newList.add(
                    it.copy(
                        isFavorite = productDao.getProduct(productId = it.id)?.isFavorite ?: false
                    )
                )
            }
        }
}