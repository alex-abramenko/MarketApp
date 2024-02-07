package com.alxabr.auth.di

import android.app.Application
import androidx.room.Room
import com.alxabr.auth.data.data_source.AuthDatabase
import com.alxabr.auth.data.data_source.UserDao
import com.alxabr.auth.data.repository.AuthRepositoryImpl
import com.alxabr.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [AuthDIModule.Binds::class])
@InstallIn(SingletonComponent::class)
internal class AuthDIModule {

    @Provides
    @Singleton
    fun authDatabase(application: Application): AuthDatabase =
        Room
            .databaseBuilder(
                context = application,
                klass = AuthDatabase::class.java,
                name = AuthDatabase::class.java.name
            )
            .build()

    @Provides
    @Singleton
    fun userDao(authDatabase: AuthDatabase): UserDao =
        authDatabase.userDao

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binds {

        @dagger.Binds
        @Singleton
        fun authRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    }
}