package com.alxabr.market_common

import com.alxabr.market_domain.model.ProductPrice

fun ProductPrice.buildPriceWithoutDiscount(): String =
    "$priceWithDiscount $unit"

fun ProductPrice.buildPrice(): String =
    "$price $unit"

fun ProductPrice.buildDiscount(): String =
    "-${discount}%"