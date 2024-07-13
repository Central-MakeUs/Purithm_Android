package com.cmc.purithm.domain.usecase.auth

import com.cmc.purithm.domain.entity.Member
import com.cmc.purithm.domain.repository.AuthRepository
import javax.inject.Inject

class LoginForKakaoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(accessToken: String): Member {
        return authRepository.joinMember(accessToken)
    }
}