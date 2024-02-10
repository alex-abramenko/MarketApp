package com.alxabr.market_common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ObliqueStrikeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var paint: Paint = Paint()
        .apply {
            color = textColors.defaultColor
            strokeWidth = resources.getDimension(R.dimen.product_oblique_strike_width)
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = height.toFloat() * 0.3f
        canvas.drawLine(0f, height.toFloat() - padding, width.toFloat(), padding, paint)
    }
}