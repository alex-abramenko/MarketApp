package com.alxabr.auth_domain.use_case

import com.alxabr.auth_domain.model.User
import com.alxabr.auth_domain.repository.AuthRepository
import javax.inject.Inject

class AuthLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(user: User): Boolean =
        authRepository.login(user = user)
}