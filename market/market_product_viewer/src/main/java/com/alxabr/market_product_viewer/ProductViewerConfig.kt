package com.alxabr.market_product_viewer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductViewerConfig(
    val productId: String
) : Parcelable