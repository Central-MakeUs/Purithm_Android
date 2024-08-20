package com.cmc.purithm.domain.usecase.member

import com.cmc.purithm.domain.entity.member.Member
import com.cmc.purithm.domain.repository.MemberRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Member {
        return memberRepository.getUser()
    }
}