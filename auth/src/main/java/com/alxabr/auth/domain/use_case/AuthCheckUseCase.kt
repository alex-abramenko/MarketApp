package com.alxabr.auth.domain.use_case

import com.alxabr.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthCheckUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean =
        authRepository.checkAuthorization()
}