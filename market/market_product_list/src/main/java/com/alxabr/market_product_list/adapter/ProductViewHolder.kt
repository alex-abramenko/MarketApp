package com.alxabr.market_product_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alxabr.market_common.base_adapter.BaseViewHolder
import com.alxabr.market_common.buildDiscount
import com.alxabr.market_common.buildPrice
import com.alxabr.market_common.buildPriceWithoutDiscount
import com.alxabr.market_domain.model.Product
import com.alxabr.market_product_list.databinding.ProductListItemBinding

internal class ProductViewHolder(
    rootView: View,
    private val listener: ProductItemListener
) : BaseViewHolder<Product>(rootView) {

    private val binding: ProductListItemBinding = ProductListItemBinding.bind(rootView)

    companion object {
        fun from(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            listener: ProductItemListener
        ) =
            ProductViewHolder(
                rootView = ProductListItemBinding
                    .inflate(layoutInflater, parent, false)
                    .root,
                listener = listener
            )
    }

    init {
        with(binding) {
            root.setOnClickListener {
                listener.onClick(product = requireItem())
            }
            productFavorite.setOnClickListener {
                listener.onFavoriteClick(product = requireItem())
            }
            productToBasket.setOnClickListener {
                listener.onAddToBasketClick(product = requireItem())
            }
        }
    }

/*    override val renderer: ViewRenderer<Product> = diff {
        diff(
            get = Product::images,
            set = binding.productImageViewer::setImages
        )
        diff(
            get = Product::isFavorite,
            set = { binding.productFavorite.isFavorite = it }
        )
        diff(get = Product::price) {
            binding.productPriceWithDiscount.text = it.buildPriceWithoutDiscount()
            binding.productPrice.text = it.buildPrice()
            binding.productDiscount.text = it.buildDiscount()
        }
        diff(
            get = Product::title,
            set = binding.productTitle::setText
        )
        diff(
            get = Product::subtitle,
            set = binding.productSubtitle::setText
        )
        diff(get = Product::feedback) {
            binding.productRating.text = it.rating.toString()
        }
    }*/

    override fun render(item: Product) {
        binding.productImageViewer.setImages(item.images)
        binding.productFavorite.isFavorite = item.isFavorite
        binding.productPriceWithDiscount.text = item.price.buildPriceWithoutDiscount()
        binding.productPrice.text = item.price.buildPrice()
        binding.productDiscount.text = item.price.buildDiscount()
        binding.productTitle.text = item.title
        binding.productSubtitle.text = item.subtitle
        binding.productRating.text = item.feedback.rating.toString()
    }
}