package com.alxabr.market_catalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alxabr.market_catalog.databinding.CatalogFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private var _binding: CatalogFragmentBinding? = null
    private val binding: CatalogFragmentBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        CatalogFragmentBinding
            .inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productListView.configure(fragment = this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}