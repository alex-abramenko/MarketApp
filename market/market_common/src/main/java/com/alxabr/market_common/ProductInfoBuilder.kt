package com.alxabr.market_common

import android.content.res.Resources
import com.alxabr.market_domain.model.ProductFeedback
import com.alxabr.market_domain.model.ProductPrice

fun ProductPrice.buildPriceWithoutDiscount(): String =
    "$priceWithDiscount $unit"

fun ProductPrice.buildPrice(): String =
    "$price $unit"

fun ProductPrice.buildDiscount(): String =
    "-${discount}%"

fun ProductFeedback.buildFeedbackCount(): String =
    "($count)"

fun ProductFeedback.buildFeedbackCountExt(resource: Resources): String =
    resource.getQuantityString(R.plurals.product_feedback_count, count, count)