package com.cmc.purithm.data.repository

import com.cmc.purithm.data.local.datasource.MemberDataStore
import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.dto.member.ProfileUpdateRequestDto
import com.cmc.purithm.data.remote.mapper.toDomain
import com.cmc.purithm.data.remote.service.MemberService
import com.cmc.purithm.domain.entity.member.Account
import com.cmc.purithm.domain.entity.member.Member
import com.cmc.purithm.domain.repository.MemberRepository
import javax.inject.Inject

internal class MemberRepositoryImpl @Inject constructor(
    private val memberService: MemberService,
    private val memberDataStore: MemberDataStore
) : MemberRepository {
    override suspend fun getFirstRun(): Boolean {
        return memberDataStore.getFirstRun()
    }

    override suspend fun setFirstRun(flag: Boolean) {
        return memberDataStore.setFirstRun(flag)
    }

    override suspend fun agreeToTermsOfService() {
        HandleApi.callApi { memberService.requestAgreeTermsOfService() }
    }

    override suspend fun getFirstFilter(): Boolean {
        return memberDataStore.getFirstFilterRun()
    }

    override suspend fun setFirstFilter(flag: Boolean) {
        memberDataStore.setFirstFilterRun(flag)
    }

    override suspend fun deleteMyReview(reviewId: Long) {
        HandleApi.callApi { memberService.deleteReview(reviewId) }
    }

    override suspend fun getUser(): Member {
        return HandleApi.callApi { memberService.getUser().toDomain() }
    }

    override suspend fun getAccount(): Account {
        return HandleApi.callApi { memberService.getAccount().toDomain() }
    }

    override suspend fun updateUserProfile(username: String, profile: String) {
        val request = ProfileUpdateRequestDto(username, profile)
        HandleApi.callApi { memberService.updateProfile(request) }
    }
}