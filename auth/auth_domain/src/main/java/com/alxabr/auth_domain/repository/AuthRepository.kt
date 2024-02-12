package com.alxabr.auth_domain.repository

import com.alxabr.auth_domain.model.User

interface AuthRepository {

    suspend fun login(user: User): Boolean

    suspend fun getUser(): User?
}