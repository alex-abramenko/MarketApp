package com.alxabr.auth.domain.repository

import com.alxabr.auth.domain.model.User

interface AuthRepository {

    suspend fun checkAuthorization(): Boolean

    suspend fun login(user: User): Boolean
}