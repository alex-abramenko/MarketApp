package com.alxabr.market_product_list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alxabr.auth_domain.utils.MarketLogger
import com.alxabr.market_domain.model.Product

internal class ProductAdapter(
    private val listener: ProductItemListener
) : RecyclerView.Adapter<ProductViewHolder>() {

    private val products: MutableList<Product> = mutableListOf()

    fun setProducts(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyItemRangeChanged(0, products.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder.from(parent = parent, listener = listener)

    override fun getItemCount(): Int =
        products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        products
            .getOrNull(position)
            ?.let(holder::bind)
            ?: MarketLogger.error("Product not found in pos $position.")
    }
}