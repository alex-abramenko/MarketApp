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
/*            val stringBuilder = StringBuilder()
            val inputStream: InputStream = application.assets.open("mock.json")
            val bufferBuilder = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            var str: String?
            while (bufferBuilder.readLine().also { str = it } != null) {
                stringBuilder.append(str)
            }
            bufferBuilder.close()
            gson.fromJson(stringBuilder.toString(), Array<ProductEntity>::class.java).toList()*/
            gson
                .fromJson(
                    BufferedReader(
                        InputStreamReader(
                            application.assets.open("mock.json"),
                            StandardCharsets.UTF_8
                        )
                    ),
                    Array<ProductEntity>::class.java
                )
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