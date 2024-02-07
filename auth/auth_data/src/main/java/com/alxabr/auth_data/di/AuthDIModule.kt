package com.alxabr.auth_data.di

import android.app.Application
import androidx.room.Room
import com.alxabr.auth_data.data_source.AuthDatabase
import com.alxabr.auth_data.data_source.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
internal class AuthDataDIModule {

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
}