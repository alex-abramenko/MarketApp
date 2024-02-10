package com.alxabr.market_common

import com.alxabr.market_domain.model.Product

fun Product.buildPriceWithoutDiscount(): String =
    "${price.priceWithDiscount} ${price.unit}"

fun Product.buildPrice(): String =
    "${price.price} ${price.unit}"

fun Product.buildDiscount(): String =
    "-${price.discount}%"