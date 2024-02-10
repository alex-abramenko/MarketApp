package com.alxabr.market_product_list.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alxabr.market_domain.model.Product
import com.alxabr.market_product_list.databinding.ProductListItemBinding

internal class ProductViewHolder(rootView: View) : ViewHolder(rootView) {

    private val binding: ProductListItemBinding = ProductListItemBinding.bind(rootView)

    companion object {
        fun from(parent: ViewGroup) =
            ProductViewHolder(
                rootView = ProductListItemBinding
                    .inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                    .root
            )
    }

    fun bind(product: Product) {
        with(binding) {
            productPriceWithDiscount.text = product.price.priceWithDiscount.toString()
            productPrice.text = product.price.price.toString()
            productDiscount.text = product.price.discount.toString()
            productTitle.text = product.title
            productSubtitle.text = product.subtitle
            productRating.text = product.feedback.rating.toString()
        }
    }
}