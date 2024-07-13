package com.cmc.purithm.data.repository

import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.mapper.toDomain
import com.cmc.purithm.data.remote.service.AuthService
import com.cmc.purithm.domain.entity.Member
import com.cmc.purithm.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun joinMember(accessToken: String): Member {
        return HandleApi.callApi { authService.joinKakao(accessToken).toDomain() }
    }
}