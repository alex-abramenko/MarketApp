package com.alxabr.market_domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductInfo(
    val title: String,
    val value: String
): Parcelable