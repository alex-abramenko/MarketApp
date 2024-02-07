package com.alxabr.auth

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import com.alxabr.auth.feature.AuthFeatureFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface AuthFeature {

    interface Factory {
        fun create(viewModelStoreOwner: ViewModelStoreOwner): AuthFeature
    }

    fun createAuthFragment(): Fragment
}

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthFeatureApi {
    @Binds
    @Singleton
    fun authFeatureFactory(authFeatureFactoryImpl: AuthFeatureFactoryImpl): AuthFeature.Factory
}