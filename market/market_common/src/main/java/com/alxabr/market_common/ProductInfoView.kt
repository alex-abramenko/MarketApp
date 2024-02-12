package com.alxabr.market_common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.alxabr.market_common.databinding.ProductInfoItemBinding
import com.alxabr.market_domain.model.ProductInfo

class ProductInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    fun setInfoList(productInfo: List<ProductInfo>) {
        productInfo.forEach {
            addView(it.createView())
        }
    }

    private fun ProductInfo.createView(): View =
        ProductInfoItemBinding
            .inflate(
                LayoutInflater.from(context),
                this@ProductInfoView,
                false
            )
            .also {
                it.title.text = title
                it.value.text = value
            }
            .root
}