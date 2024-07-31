package com.cmc.purithm.domain.repository

interface AuthRepository {
    suspend fun joinMember(accessToken : String) : String
    suspend fun setAccessToken(accessToken : String)
    suspend fun checkAccessToken()
}