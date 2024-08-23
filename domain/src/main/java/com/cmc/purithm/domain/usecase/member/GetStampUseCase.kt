package com.cmc.purithm.domain.usecase.member

import com.cmc.purithm.domain.entity.filter.FilterHistory
import com.cmc.purithm.domain.repository.MemberRepository
import javax.inject.Inject

class GetStampUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke() : FilterHistory {
        return memberRepository.getStamp()
    }
}