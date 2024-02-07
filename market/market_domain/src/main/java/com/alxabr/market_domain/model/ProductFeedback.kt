package com.alxabr.market_domain.model

import android.os.Parcelable
import androidx.annotation.FloatRange
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductFeedback(
    val count: Long,
    @FloatRange(from = 0.0, to = 5.0) val rating: Float
): Parcelable