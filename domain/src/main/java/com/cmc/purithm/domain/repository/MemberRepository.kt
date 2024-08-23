package com.cmc.purithm.domain.repository

import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.entity.filter.FilterHistory
import com.cmc.purithm.domain.entity.member.Account
import com.cmc.purithm.domain.entity.member.Member

interface MemberRepository {
    suspend fun getFirstRun() : Boolean
    suspend fun setFirstRun(flag : Boolean)
    suspend fun agreeToTermsOfService()
    suspend fun getFirstFilter() : Boolean
    suspend fun setFirstFilter(flag : Boolean)
    suspend fun deleteMyReview(reviewId : Long)
    suspend fun getUser() : Member
    suspend fun getAccount() : Account
    suspend fun updateUserProfile(username : String, profile : String)
    suspend fun getStamp() : FilterHistory
    suspend fun getFilterHistory() : FilterHistory
}