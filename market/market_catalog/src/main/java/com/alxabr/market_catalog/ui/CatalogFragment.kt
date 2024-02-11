package com.alxabr.market_catalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alxabr.market_catalog.R
import com.alxabr.market_catalog.databinding.CatalogFragmentBinding
import com.alxabr.market_domain.model.Product
import com.alxabr.market_product_list.ProductList
import com.alxabr.market_product_list.ProductListView
import com.alxabr.market_product_viewer.viewer.ProductViewerConfig
import com.alxabr.market_product_viewer.viewer.ProductViewerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    companion object {
        private const val PRODUCT_VIEWER_TAG = "CatalogFragment.PRODUCT_VIEWER_TAG"
    }

    private var _binding: CatalogFragmentBinding? = null
    private val binding: CatalogFragmentBinding
        get() = _binding!!
    private val viewModel: CatalogViewModel by viewModels()
    private val backPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            childFragmentManager.popBackStack()
            isEnabled = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        CatalogFragmentBinding
            .inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)

        with(binding) {
            productListView
                .configure(
                    fragment = this@CatalogFragment,
                    config = ProductListView.Config(sortType = viewModel.sortType)
                )
                .events
                .onEach(::onProductListEvent)
                .launchIn(viewLifecycleOwner.lifecycleScope)
            productSortSelector.setSelectedSort(sortType = viewModel.sortType.value)
            productSortSelector.setListener {
                viewModel.onUiEvent(event = CatalogUiEvent.OnSortChanged(sortType = it))
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onProductListEvent(event: ProductList.Event) {
        when (event) {
            is ProductList.Event.OnOpenProduct -> showProductViewer(product = event.product)
        }
    }

    private fun showProductViewer(product: Product) {
        backPressedCallback.isEnabled = true
        if (childFragmentManager.findFragmentByTag(PRODUCT_VIEWER_TAG) == null) {
            childFragmentManager
                .beginTransaction()
                .replace(
                    R.id.productViewerContainer,
                    ProductViewerFragment.newInstance(
                        config = ProductViewerConfig(productId = product.id)
                    ),
                    PRODUCT_VIEWER_TAG
                )
                .addToBackStack(PRODUCT_VIEWER_TAG)
                .commitAllowingStateLoss()
        }
    }
}