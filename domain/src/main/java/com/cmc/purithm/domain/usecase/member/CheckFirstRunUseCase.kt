package com.cmc.purithm.domain.usecase.member

import com.cmc.purithm.domain.repository.MemberRepository
import javax.inject.Inject

class CheckFirstRunUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke() : Boolean{
        return memberRepository.getFirstRun()
    }
}