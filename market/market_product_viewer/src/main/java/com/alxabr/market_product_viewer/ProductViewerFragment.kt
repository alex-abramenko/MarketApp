package com.alxabr.market_product_viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alxabr.market_common.buildAvailable
import com.alxabr.market_common.buildDiscount
import com.alxabr.market_common.buildFeedbackCountExt
import com.alxabr.market_common.buildPrice
import com.alxabr.market_common.buildPriceWithoutDiscount
import com.alxabr.market_domain.model.Product
import com.alxabr.market_product_viewer.databinding.ProductViewerFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ProductViewerFragment : Fragment() {

    companion object {
        private const val CONFIG_ARG = "ProductViewerFragment.CONFIG_ARG"

        fun newInstance(config: ProductViewerConfig): Fragment =
            ProductViewerFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(CONFIG_ARG, config)
                    }
                }
    }

    private val config: ProductViewerConfig by lazy {
        @Suppress("DEPRECATION")
        requireArguments().getParcelable(CONFIG_ARG)!!
    }
    @Inject
    internal lateinit var viewModelFactory: ProductViewerViewModel.Factory
    private val viewModel: ProductViewerViewModel by lazy {
        viewModelFactory.create(config = config)
    }
    private var _binding: ProductViewerFragmentBinding? = null
    private val binding: ProductViewerFragmentBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ProductViewerFragmentBinding
            .inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel
            .product
            .onEach(::render)
            .launchIn(viewLifecycleOwner.lifecycleScope)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.btnToBasket.setOnClickListener {
            viewModel.onUiEvent(ProductViewerUiEvent.OnToBasketClick)
        }
        binding.productFavorite.setOnClickListener {
            viewModel.onUiEvent(ProductViewerUiEvent.OnFavoriteChanged)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(product: Product?) {
        if (product == null) {
            return
        }
        with(binding) {
            productImageViewer.setImages(product.images)
            productFavorite.isFavorite = product.isFavorite
            productTitle.text = product.title
            productSubtitle.text = product.subtitle
            productAvailable.text = product.buildAvailable(resources)
            productRating.rating = product.feedback.rating
            productRatingValue.text = product.feedback.rating.toString()
            productFeedbackCount.text = product.feedback.buildFeedbackCountExt(resources)
            productPrice.text = product.price.buildPrice()
            productPriceWithDiscount.text = product.price.buildPriceWithoutDiscount()
            productDiscount.text = product.price.buildDiscount()
            textDesc.text = product.description
            productBrand.text = product.title
            productDescription.text = product.description
            productIngredients.text = product.ingredients
        }
    }
}