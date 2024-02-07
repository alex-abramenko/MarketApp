package com.alxabr.auth_presentation

import com.alxabr.auth_presentation.feature.AuthFeatureFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthFeatureApi {
    @Binds
    @Singleton
    fun authFeatureFactory(authFeatureFactoryImpl: AuthFeatureFactoryImpl): AuthFeature.Factory
}