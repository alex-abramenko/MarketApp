package com.alxabr.market_product_list.ui

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductListView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val productAdapter: ProductAdapter = ProductAdapter()

    init {
        layoutManager = GridLayoutManager(context, 2)
        adapter = productAdapter
    }

    fun configure(fragment: Fragment): ProductListController =
        fragment
            .viewModels<ProductListViewModel>()
            .value
            .apply {
                products
                    .onEach(productAdapter::setProducts)
                    .launchIn(fragment.viewLifecycleOwner.lifecycleScope)
            }
}