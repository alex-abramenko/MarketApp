package com.alxabr.marketapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alxabr.auth_presentation.AuthFeature
import com.alxabr.market_catalog.ui.CatalogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val AUTH_TAG = "MainActivity.AUTH_TAG"
        private const val MARKET_TAG = "MainActivity.MARKET_TAG"
    }

    @Inject
    internal lateinit var authFeatureFactory: AuthFeature.Factory
    private val authFeature: AuthFeature by lazy { authFeatureFactory.create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            if (authFeature.isNeedAuth()) {
                if (supportFragmentManager.findFragmentByTag(AUTH_TAG) == null) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, authFeature.createAuthFragment(), AUTH_TAG)
                        .commitAllowingStateLoss()
                }
                authFeature.events.collect(::onAuthEvent)
            } else {
                showMarket()
            }
        }
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
                .replace(R.id.fragmentContainer, CatalogFragment(), MARKET_TAG)
                .commitAllowingStateLoss()
        }
    }
}