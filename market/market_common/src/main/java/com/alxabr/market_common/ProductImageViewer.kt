package com.alxabr.market_common

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.SliderViewAdapter

class ProductImageViewer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SliderView(context, attrs, defStyleAttr) {

    private val adapter: SliderAdapter = SliderAdapter()

    init {
        setSliderAdapter(adapter)
    }

    fun setImages(images: List<Int>) {
        adapter.setImages(images = images)
    }

    class SliderAdapter : SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder>() {
        @DrawableRes
        private val images: MutableList<Int> = mutableListOf()

        fun setImages(images: List<Int>) {
            this.images.clear()
            this.images.addAll(images)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterViewHolder =
            SliderAdapterViewHolder(ImageView(parent.context))

        override fun onBindViewHolder(viewHolder: SliderAdapterViewHolder, position: Int) {
            images
                .getOrNull(position)
                ?.let(viewHolder::bind)
        }

        override fun getCount(): Int = images.size

        class SliderAdapterViewHolder(private val imageView: ImageView) : ViewHolder(imageView) {

            fun bind(@DrawableRes drawableRes: Int) {
                imageView.setImageResource(drawableRes)
            }
        }
    }
}