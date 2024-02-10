package com.alxabr.market_data.data_source.remote

import android.app.Application
import com.alxabr.market_data.R
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
                .validateImages()
        }

    private suspend fun List<ProductEntity>.validateFavorite(): List<ProductEntity> =
        map {
            it.copy(
                isFavorite = productDao.getProduct(productId = it.id)?.isFavorite ?: false
            )
        }

    private fun List<ProductEntity>.validateImages(): List<ProductEntity> =
        map {
            it.copy(
                images = mutableListOf<Int>().apply {
                    when (it.id) {
                        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> {
                            add(R.drawable.image6)
                            add(R.drawable.image5)
                        }
                        "54a876a5-2205-48ba-9498-cfecff4baa6e" -> {
                            add(R.drawable.image1)
                            add(R.drawable.image2)
                        }
                        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> {
                            add(R.drawable.image5)
                            add(R.drawable.image6)
                        }
                        "16f88865-ae74-4b7c-9d85-b68334bb97db" -> {
                            add(R.drawable.image3)
                            add(R.drawable.image4)
                        }
                        "26f88856-ae74-4b7c-9d85-b68334bb97db" -> {
                            add(R.drawable.image2)
                            add(R.drawable.image3)
                        }
                        "15f88865-ae74-4b7c-9d81-b78334bb97db" -> {
                            add(R.drawable.image6)
                            add(R.drawable.image1)
                        }
                        "88f88865-ae74-4b7c-9d81-b78334bb97db" -> {
                            add(R.drawable.image4)
                            add(R.drawable.image3)
                        }
                        "55f58865-ae74-4b7c-9d81-b78334bb97db" -> {
                            add(R.drawable.image1)
                            add(R.drawable.image5)
                        }
                    }
                }
            )
        }
}