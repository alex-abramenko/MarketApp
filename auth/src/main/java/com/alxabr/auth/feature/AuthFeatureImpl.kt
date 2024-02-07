package com.alxabr.auth.feature

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.alxabr.auth.AuthFeature
import com.alxabr.auth.presentation.AuthFragment
import com.alxabr.auth.utils.findOrCreateViewModel
import javax.inject.Inject

internal class AuthFeatureFactoryImpl @Inject constructor() : AuthFeature.Factory {
    override fun create(viewModelStoreOwner: ViewModelStoreOwner): AuthFeature =
        viewModelStoreOwner.findOrCreateViewModel {
            AuthFeatureImpl()
        }
}

internal class AuthFeatureImpl : AuthFeature, ViewModel() {

    override fun createAuthFragment(): Fragment =
        AuthFragment()
}