package com.cmc.purithm.domain.usecase.auth

import com.cmc.purithm.domain.repository.AuthRepository
import javax.inject.Inject

class SetAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(accessToken : String){
        return authRepository.setAccessToken(accessToken)
    }
}