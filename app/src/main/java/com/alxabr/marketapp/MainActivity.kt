package com.alxabr.marketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alxabr.auth_presentation.AuthFeature
import com.alxabr.market_feature.MarketFeatureFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val MARKET_TAG = "MainActivity.MARKET_TAG"
    }

    @Inject
    internal lateinit var authFeatureFactory: AuthFeature.Factory
    private val authFeature: AuthFeature by lazy { authFeatureFactory.create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            authFeature.events.collect(::onAuthEvent)
        }
        authFeature.startAuthProcess(fragmentManager = supportFragmentManager, containerId = R.id.fragmentContainer)
    }

    private fun onAuthEvent(event: AuthFeature.Event) {
        when (event) {
            AuthFeature.Event.AuthSuccess -> showMarket()
        }
    }

    private fun showMarket() {
        if (supportFragmentManager.findFragmentByTag(MARKET_TAG) == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, MarketFeatureFragment(), MARKET_TAG)
                .commitAllowingStateLoss()
        }
    }
}