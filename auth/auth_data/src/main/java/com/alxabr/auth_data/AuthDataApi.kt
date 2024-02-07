package com.alxabr.auth_data

import com.alxabr.auth_domain.repository.AuthRepository
import com.alxabr.auth_data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthDataApi {

    @Binds
    @Singleton
    fun authRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

}