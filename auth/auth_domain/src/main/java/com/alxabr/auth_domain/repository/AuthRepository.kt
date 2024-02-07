package com.alxabr.auth_domain.repository

import com.alxabr.auth_domain.model.User

interface AuthRepository {

    suspend fun checkAuthorization(): Boolean

    suspend fun login(user: User): Boolean
}