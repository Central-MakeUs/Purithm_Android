package com.cmc.purithm.domain.usecase.member

import com.cmc.purithm.domain.repository.MemberRepository
import javax.inject.Inject

class DeleteMyReviewUseCase @Inject constructor(
    private val memberRepository: MemberRepository
){
    suspend operator fun invoke(reviewId : Long){
        memberRepository.deleteMyReview(reviewId)
    }
}