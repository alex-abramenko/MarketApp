package com.alxabr.market_product_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alxabr.market_common.base_adapter.BaseAdapter
import com.alxabr.market_common.base_adapter.BaseViewHolder
import com.alxabr.market_domain.model.Product


internal class ProductAdapter(private val listener: ProductItemListener) : BaseAdapter<Product>() {

    override fun instanceViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): BaseViewHolder<Product> =
        ProductViewHolder.from(layoutInflater = layoutInflater, parent = parent, listener = listener)

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}