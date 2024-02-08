package com.alxabr.market_data.repository

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
            images = emptyList() /* TODO */,
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
}