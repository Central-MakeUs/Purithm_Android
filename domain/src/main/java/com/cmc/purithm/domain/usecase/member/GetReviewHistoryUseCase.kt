package com.cmc.purithm.domain.usecase.member

import com.cmc.purithm.domain.entity.review.ReviewItem
import com.cmc.purithm.domain.repository.MemberRepository
import javax.inject.Inject

class GetReviewHistoryUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke() : List<ReviewItem> {
        return memberRepository.getReviewHistory()
    }
}