package com.alxabr.market_product_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alxabr.market_common.buildDiscount
import com.alxabr.market_common.buildPrice
import com.alxabr.market_common.buildPriceWithoutDiscount
import com.alxabr.market_domain.model.Product
import com.alxabr.market_product_list.databinding.ProductListItemBinding

internal class ProductViewHolder(
    rootView: View,
    private val listener: ProductItemListener
) : ViewHolder(rootView) {

    private val binding: ProductListItemBinding = ProductListItemBinding.bind(rootView)

    companion object {
        fun from(
            parent: ViewGroup,
            listener: ProductItemListener
        ) =
            ProductViewHolder(
                rootView = ProductListItemBinding
                    .inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                    .root,
                listener = listener
            )
    }

    fun bind(product: Product) {
        with(binding) {
            productImageViewer.setImages(product.images)
            productFavorite.isFavorite = product.isFavorite
            productPriceWithDiscount.text = product.buildPriceWithoutDiscount()
            productPrice.text = product.buildPrice()
            productDiscount.text = product.buildDiscount()
            productTitle.text = product.title
            productSubtitle.text = product.subtitle
            productRating.text = product.feedback.rating.toString()

            binding.root.setOnClickListener {
                listener.onClick(product = product)
            }
            productFavorite.setOnClickListener {
                listener.onFavoriteClick(product = product)
            }
            productToBasket.setOnClickListener {
                listener.onAddToBasketClick(product = product)
            }
        }
    }
}