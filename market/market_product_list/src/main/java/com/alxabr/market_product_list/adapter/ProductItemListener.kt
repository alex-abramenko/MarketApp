package com.alxabr.market_product_list.adapter

import com.alxabr.market_domain.model.Product

interface ProductItemListener {

    fun onClick(product: Product)
    fun onFavoriteClick(product: Product)
    fun onAddToBasketClick(product: Product)
}