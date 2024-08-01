package com.cmc.purithm.data.repository

import android.util.Log
import com.cmc.purithm.data.local.datasource.AuthDataStore
import com.cmc.purithm.data.remote.ApiConfig
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
        /*
        * Header에 항상 카카오 토큰이 들어가야함
        * Response에서 토큰이 정상적으로 들어온다면 업데이트 하는 방식으로 변경
        * */
        ApiConfig.ACCESS_TOKEN = accessToken
        return HandleApi.callApi { authService.joinKakao().toDomain() }
    }

    override suspend fun setAccessToken(accessToken: String) {
        authDataStore.setAccessToken(accessToken)
    }

    override suspend fun checkAccessToken() {
        // 서버에 response 없음
        HandleApi.callApi { authService.checkAccessToken() }
    }
    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }
}