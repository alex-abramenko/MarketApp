package com.alxabr.market_common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView

class ProductFavoriteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var isFavorite: Boolean = false
        set(value) {
            field = value
            applyState()
        }

    private var imageView: ImageView = ImageView(context)

    init {
        addView(
            imageView,
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
        )
        applyState()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = resources.getDimensionPixelSize(R.dimen.product_favorite_button_size)
        super.onMeasure(size, size)
    }

    private fun applyState() {
        imageView.setImageResource(if (isFavorite) R.drawable.heart_fill else R.drawable.heart)
    }
}