package com.alxabr.market_domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    @DrawableRes val images: List<Int>,
    val title: String,
    val subtitle: String,
    val price: ProductPrice,
    val feedback: ProductFeedback,
    val tags: List<String>,
    val available: Long,
    val description: String,
    val infoList: List<ProductInfo>,
    val ingredients: String,
    val isFavorite: Boolean
): Parcelable