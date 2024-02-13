package com.alxabr.market_data.repository

import com.alxabr.market_data.R
import com.alxabr.market_data.model.ProductEntity
import com.alxabr.market_domain.model.Product
import com.alxabr.market_domain.model.ProductFeedback
import com.alxabr.market_domain.model.ProductInfo
import com.alxabr.market_domain.model.ProductPrice
import javax.inject.Inject

internal class ProductMapper @Inject constructor() {

    operator fun invoke(productEntityList: List<ProductEntity>): List<Product> =
        productEntityList.map { invoke(productEntity = it) }

    operator fun invoke(productEntity: ProductEntity): Product =
        Product(
            id = productEntity.id,
            images = getImages(productEntity.id),
            title = productEntity.title,
            subtitle = productEntity.subtitle,
            price = ProductPrice(
                price = productEntity.price.price,
                discount = productEntity.price.discount,
                priceWithDiscount = productEntity.price.priceWithDiscount,
                unit = productEntity.price.unit
            ),
            feedback = ProductFeedback(
                count = productEntity.feedback.count,
                rating = productEntity.feedback.rating
            ),
            tags = productEntity.tags,
            available = productEntity.available,
            description = productEntity.description,
            infoList = productEntity.info.map {
                ProductInfo(
                    title = it.title,
                    value = it.value
                )
            },
            ingredients = productEntity.ingredients,
            isFavorite = productEntity.isFavorite
        )

    operator fun invoke(product: Product): ProductEntity =
        ProductEntity(
            id = product.id,
            title = product.title,
            subtitle = product.subtitle,
            price = ProductEntity.Price(
                price = product.price.price,
                discount = product.price.discount,
                priceWithDiscount = product.price.priceWithDiscount,
                unit = product.price.unit
            ),
            feedback = ProductEntity.Feedback(
                count = product.feedback.count,
                rating = product.feedback.rating
            ),
            tags = product.tags,
            available = product.available,
            description = product.description,
            info = product.infoList.map {
                ProductEntity.Info(
                    title = it.title,
                    value = it.value
                )
            },
            ingredients = product.ingredients,
            isFavorite = product.isFavorite
        )

    private fun getImages(id: String): List<Int> =
        mutableListOf<Int>()
            .apply {
                when (id) {
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
}