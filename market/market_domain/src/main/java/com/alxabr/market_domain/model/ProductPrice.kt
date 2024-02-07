package com.alxabr.market_domain.model

import android.os.Parcelable
import androidx.annotation.IntRange
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductPrice(
    val price: Long,
    @IntRange(from = 0L, to = 100L) val discount: Int,
    val priceWithDiscount: Long,
    val unit: String
): Parcelable