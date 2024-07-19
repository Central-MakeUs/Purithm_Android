package com.cmc.purithm.data.repository

import com.cmc.purithm.data.local.datasource.AuthDataStore
import com.cmc.purithm.data.remote.ApiSetting
import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.mapper.toDomain
import com.cmc.purithm.data.remote.service.AuthService
import com.cmc.purithm.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val authDataStore: AuthDataStore
) : AuthRepository {
    override suspend fun joinMember(accessToken: String) : String {
        // 초기 회원가입을 위한 토큰 저장
        if(ApiSetting.ACCESS_TOKEN.isEmpty()){
            authDataStore.setAccessToken(accessToken)
        }
        return HandleApi.callApi { authService.joinKakao().toDomain() }
    }

    override suspend fun checkAccessToken(): Boolean {
        // Header에 등록한 토큰 검증
        return HandleApi.callApi { authService.checkAccessToken().toDomain()}
    }
}