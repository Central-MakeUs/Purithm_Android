package com.cmc.purithm.data.repository

import com.cmc.purithm.data.local.datasource.MemberDataStore
import com.cmc.purithm.domain.repository.MemberRepository
import javax.inject.Inject

internal class MemberRepositoryImpl @Inject constructor(
    private val memberDataStore: MemberDataStore
) : MemberRepository {
    override suspend fun initializeFirstRun(): Boolean {
        val firstRunFlag = memberDataStore.getFirstRun()
        if (firstRunFlag) {
            memberDataStore.setFirstRun()
        }
        return firstRunFlag
    }
}