package com.alxabr.auth.domain.use_case

import com.alxabr.auth.domain.model.User
import com.alxabr.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(user: User): Boolean =
        authRepository.login(user = user)
}