package com.alxabr.market_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alxabr.market_catalog.ui.CatalogFragment
import com.alxabr.market_feature.databinding.MarketFeatureFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class MarketFeatureFragment : Fragment() {

    private val tabContents: List<TabContent> = listOf(
        TabContent(
            text = R.string.market_feature_main_tab,
            icon = R.drawable.main_tab,
            fragmentFactory = { StubFragment.newInstance(R.string.market_feature_main_tab) }
        ),
        TabContent(
            text = R.string.market_feature_catalog_tab,
            icon = R.drawable.catalog_tab,
            fragmentFactory = { CatalogFragment() }
        ),
        TabContent(
            text = R.string.market_feature_basket_tab,
            icon = R.drawable.basket_tab,
            fragmentFactory = { StubFragment.newInstance(R.string.market_feature_basket_tab) }
        ),
        TabContent(
            text = R.string.market_feature_stock_tab,
            icon = R.drawable.stock_tab,
            fragmentFactory = { StubFragment.newInstance(R.string.market_feature_stock_tab) }
        ),
        TabContent(
            text = R.string.market_feature_account_tab,
            icon = R.drawable.account_tab,
            fragmentFactory = { StubFragment.newInstance(R.string.market_feature_account_tab) }
        )
    )

    private var _binding: MarketFeatureFragmentBinding? = null
    private val binding: MarketFeatureFragmentBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        MarketFeatureFragmentBinding
            .inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = SampleFragmentPagerAdapter(fragment = this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabContent = tabContents.getOrNull(position) ?: return@TabLayoutMediator
            tab.text = getString(tabContent.text)
            tab.icon = ContextCompat.getDrawable(requireContext(), tabContent.icon)
        }.attach()
        binding.tabLayout.addOnTabSelectedListener(
            object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    invalidateToolbar(tab ?: return)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    invalidateToolbar(tab ?: return)
                }
            }
        )
        binding.tabLayout.getTabAt(0)?.select()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun invalidateToolbar(tab: TabLayout.Tab) {
        binding.toolbar.title = tab.text
    }

    private inner class SampleFragmentPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int =
            tabContents.size

        override fun createFragment(position: Int): Fragment =
            tabContents[position].fragmentFactory()
    }

    private data class TabContent(
        @StringRes val text: Int,
        @DrawableRes val icon: Int,
        val fragmentFactory: () -> Fragment
    )
}