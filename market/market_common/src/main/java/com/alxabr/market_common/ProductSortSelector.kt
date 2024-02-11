package com.alxabr.market_common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.alxabr.market_common.databinding.ProductSortButtonBinding
import com.alxabr.market_domain.repository.ProductSortType
import com.google.android.material.textfield.MaterialAutoCompleteTextView

typealias Listener = (selectedSort: ProductSortType) -> Unit

class ProductSortSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var listener: Listener? = null
    private var text: MaterialAutoCompleteTextView? = null

    init {
        val sortView = ProductSortButtonBinding
            .inflate(LayoutInflater.from(context), this, false)
        text = (sortView.autoCompleteTextView as? MaterialAutoCompleteTextView)?.apply {
            setSimpleItems(createSimpleItems())
            setOnItemClickListener { _, _, position, _ ->
                listener?.invoke(ProductSortType.values()[position])
            }
        }
        addView(
            sortView.root,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        )
    }

    fun setSelectedSort(sortType: ProductSortType) {
        text?.setText(sortType.toUserFriendlyString())
        text?.setSimpleItems(createSimpleItems())
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    private fun createSimpleItems(): Array<String> =
        ProductSortType
            .values()
            .map { it.toUserFriendlyString() }
            .toTypedArray()

    private fun ProductSortType.toUserFriendlyString(): String =
        context.getString(
            when (this) {
                ProductSortType.BY_POPULAR -> R.string.product_sort_by_popular
                ProductSortType.BY_PRICE_DOWN -> R.string.product_sort_by_price_down
                ProductSortType.BY_PRICE_UP -> R.string.product_sort_by_price_up
            }
        )
}