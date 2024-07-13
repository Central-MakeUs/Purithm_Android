package com.cmc.purithm.domain.repository

import com.cmc.purithm.domain.entity.Member

interface AuthRepository {
    suspend fun joinMember(accessToken : String) : Member
}