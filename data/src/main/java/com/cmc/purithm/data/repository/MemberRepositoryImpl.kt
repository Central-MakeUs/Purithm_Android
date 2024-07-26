package com.cmc.purithm.data.repository

import com.cmc.purithm.data.local.datasource.MemberDataStore
import com.cmc.purithm.domain.repository.MemberRepository
import javax.inject.Inject

internal class MemberRepositoryImpl @Inject constructor(
    private val memberDataStore: MemberDataStore
) : MemberRepository {
    override suspend fun getFirstRun(): Boolean {
        return memberDataStore.getFirstRun()
    }

    override suspend fun setFirstRun(flag: Boolean) {
        return memberDataStore.setFirstRun(flag)
    }

}