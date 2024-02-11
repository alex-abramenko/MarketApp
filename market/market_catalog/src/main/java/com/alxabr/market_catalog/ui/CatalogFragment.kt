package com.alxabr.market_catalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alxabr.market_catalog.databinding.CatalogFragmentBinding
import com.alxabr.market_product_list.ProductListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private var _binding: CatalogFragmentBinding? = null
    private val binding: CatalogFragmentBinding
        get() = _binding!!
    private val viewModel: CatalogViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        CatalogFragmentBinding
            .inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productListView.configure(
            fragment = this,
            config = ProductListView.Config(sortType = viewModel.sortType)
        )
        binding.productSortSelector.setSelectedSort(sortType = viewModel.sortType.value)
        binding.productSortSelector.setListener {
            viewModel.onUiEvent(event = CatalogUiEvent.OnSortChanged(sortType = it))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}