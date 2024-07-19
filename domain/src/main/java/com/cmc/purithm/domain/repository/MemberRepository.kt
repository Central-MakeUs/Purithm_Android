package com.cmc.purithm.domain.repository

interface MemberRepository {
    suspend fun initializeFirstRun() : Boolean
}