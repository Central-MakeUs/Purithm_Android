package com.cmc.purithm.domain.repository

import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.entity.filter.FilterHistory
import com.cmc.purithm.domain.entity.member.Account
import com.cmc.purithm.domain.entity.member.Member
import com.cmc.purithm.domain.entity.review.Review
import com.cmc.purithm.domain.entity.review.ReviewItem

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
    suspend fun getReviewHistory() : List<ReviewItem>
    suspend fun getFilterLike() : List<Filter>
    suspend fun deleteMember()
}