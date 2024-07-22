package com.cmc.purithm.domain.repository

interface MemberRepository {
    suspend fun getFirstRun() : Boolean
    suspend fun setFirstRun()
}