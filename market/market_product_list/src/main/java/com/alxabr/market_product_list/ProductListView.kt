package com.alxabr.market_product_list

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alxabr.market_product_list.adapter.ProductAdapter
import com.alxabr.market_product_list.viewmodel.ProductListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductListView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val itemPadding: Int = resources.getDimensionPixelSize(R.dimen.product_list_item_padding)
    private val itemDecoration: ItemDecoration =
        object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: State
            ) {
                val pos = parent.getChildLayoutPosition(view)
                if (pos % 2 == 0) {
                    outRect.right = itemPadding
                }
                if (pos > 1) {
                    outRect.top = itemPadding
                }
            }
        }

    init {
        layoutManager = GridLayoutManager(context, 2)
        val contentPadding = resources.getDimensionPixelSize(R.dimen.product_list_content_padding)
        setPadding(
            contentPadding,
            contentPadding,
            contentPadding,
            contentPadding
        )
        clipToPadding = false
        addItemDecoration(itemDecoration)
        (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
    }

    fun configure(fragment: Fragment): ProductListController =
        fragment
            .viewModels<ProductListViewModel>()
            .value
            .apply {
                val productAdapter = ProductAdapter(this)
                adapter = productAdapter
                products
                    .onEach(productAdapter::setItems)
                    .launchIn(fragment.viewLifecycleOwner.lifecycleScope)
            }
}